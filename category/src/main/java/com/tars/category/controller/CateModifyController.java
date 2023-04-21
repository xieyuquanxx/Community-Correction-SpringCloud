package com.tars.category.controller;

import com.tars.category.api.ResponseResult;
import com.tars.category.entity.CateModifyInfo;
import com.tars.category.entity.CategoryInfo;
import com.tars.category.service.CateModiftyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/cate/modify")
@CrossOrigin(origins = "*")
public class CateModifyController {
    @Autowired
    private CateModiftyService service;
    @Autowired
    private CateTaskController taskController;
    @Autowired
    private CateController cateController;

    @GetMapping("/{dxbh}")
    public ResponseResult<CateModifyInfo> getModifyInfo(@PathVariable("dxbh") String dxbh) {
        CateModifyInfo cateModifyInfo = service.query()
                                               .eq("dxbh", dxbh)
                                               .one();
        if (cateModifyInfo == null) {
            CateModifyInfo info = new CateModifyInfo();
            info.setDxbh(dxbh);
            info.setStep(0);
            return ResponseResult.success(info);
        }
        return ResponseResult.success(cateModifyInfo);
    }

    @PostMapping("/sfs")
    public ResponseResult<String> implSendJZJG(@RequestBody CateModifyInfo info) {
        System.out.println(info);
        // 开启一个审批流程
        info.setProcessId(taskController.startProcessInstance());
        // 设置司法所的相关意见
        info.setSfsshr("测试人员");
        info.setSfsshsj("2023-4-19");
        info.setSfsshyj("通过");
        // 设置管理类别的相关信息
        String prevGLLB = cateController.getGLLB(info.getDxbh());
        int gllb = Integer.parseInt(prevGLLB) + 1 % 4;
        if (gllb == 0) gllb = 1;
        info.setGllb("0" + gllb);
        info.setTzyy("测试");
        info.setBdrq("2023-4-19");
        info.setXjsqjzjgspsj("2023-4-19");
        // step+1
        info.setStep(1);
        service.save(info);
        // task complete
        taskController.complete(info.getProcessId());

        return ResponseResult.success("任务已发送给矫正机构!");
    }

    @PostMapping("/update")
    public ResponseResult<Boolean> update(@RequestBody CateModifyInfo info) {
        // step+1
        info.setStep(2);
        service.update().eq("dxbh", info.getDxbh())
               .update(info);
        // task complete
        HashMap<String, Object> map = new HashMap<>();
        int res = 1;
        map.put("result", res);
        taskController.complete(info.getProcessId(), map);

        // 更新原来的矫正类别
        CategoryInfo cate = new CategoryInfo();
        cate.setDxbh(info.getDxbh());
        cate.setGllb(info.getGllb());
        cateController.updateCateInfo(cate);

        return ResponseResult.success(true);
    }
}
