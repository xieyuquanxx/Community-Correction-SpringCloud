package com.tars.uncorrected.controller;

import com.tars.uncorrected.api.ResponseResult;
import com.tars.uncorrected.entity.UnCorrInfo;
import com.tars.uncorrected.service.UnCorrInfoService;
import com.tars.uncorrected.utils.CrpHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/uncorrected")
@CrossOrigin(origins = "*")
public class UnCorrInfoController {
    @Autowired
    private UnCorrInfoService service;
    @Autowired
    private UnCorrInfoTaskController taskController;

    /**
     * 根据对象编号获取终止矫正细腻系
     *
     * @param dxbh 对象编号
     * @return 终止矫正信息
     */
    @GetMapping("/{dxbh}")
    public ResponseResult<UnCorrInfo> getInfoByDxbh(@PathVariable("dxbh") String dxbh) {
        UnCorrInfo info = service.query().eq("dxbh", dxbh).one();
        if (info == null)
            return ResponseResult.fail(null, "未找到解除矫正信息");
        info.setXm(CrpHelper.getXm(dxbh));
        return ResponseResult.success(info);
    }

    @GetMapping("/all")
    public ResponseResult<List<UnCorrInfo>> getAll() {
        List<UnCorrInfo> list = service.list().stream()
                .peek(e -> e.setXm(
                        CrpHelper.getXm(
                                e.getDxbh())))
                .toList();
        return ResponseResult.success(list);
    }

    /**
     * 模拟司法所发送解除矫正请求
     *
     * @return
     */
    @GetMapping("/sfs")
    public ResponseResult<Boolean> implSFS() {
        try {
            // 随机从矫正对象库里拿一个矫正对象
            String dxbh = CrpHelper.getRandomDxbh();
            UnCorrInfo info;
            do {
                info = service.query().eq("dxbh", dxbh)
                        .one();
            } while (info != null);
            info = new UnCorrInfo();
            info.setProcessId(taskController.startProcessInstance());
            info.setDxbh(dxbh);
            info.setStep(1);
            // 司法所环节结束，进入社区矫正机构环节
            taskController.complete(info.getProcessId());
            service.save(info);
            return ResponseResult.success(true);
        } catch (Exception e) {
            return ResponseResult.fail(false, e.getMessage());
        }

    }

    /**
     * 保存解除矫正信息
     *
     * @param info 前端传来的终止矫正信息
     * @return 返回保存状态
     */
    @PostMapping("/save")
    public ResponseResult<Boolean> saveInfo(@RequestBody UnCorrInfo info) {
        try {
            return ResponseResult.success(service.save(info));
        } catch (Exception e) {
            return ResponseResult.fail(false,
                    "保存失败!" + e.getMessage());
        }
    }

    @PostMapping("/update")
    public ResponseResult<Boolean> updateInfo(@RequestBody UnCorrInfo info) {
        try {
            // 社区矫正机构部分完成
            info.setStep(info.getStep() + 1);
            taskController.complete(info.getProcessId());
            service.update().eq("dxbh", info.getDxbh())
                    .update(info);

            return ResponseResult.success(true);
        } catch (Exception e) {
            return ResponseResult.fail(false, "更新失败!");
        }
    }

    @PostMapping("/store")
    public ResponseResult<Boolean> storeInfo(@RequestBody UnCorrInfo info) {
        try {
            service.update().eq("dxbh", info.getDxbh())
                    .update(info);

            return ResponseResult.success(true);
        } catch (Exception e) {
            return ResponseResult.fail(false, "更新失败!");
        }
    }

}
