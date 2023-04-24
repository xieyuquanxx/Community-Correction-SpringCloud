package com.tars.noexit.controller.bb;

import com.tars.noexit.api.ResponseResult;
import com.tars.noexit.controller.ExitController;
import com.tars.noexit.entity.BBInfo;
import com.tars.noexit.service.bb.BBInfoService;
import com.tars.noexit.utils.CrpHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/noexit/bb")
@CrossOrigin(origins = "*")
public class BBInfoController {
    @Autowired
    private BBInfoService service;
    @Autowired
    private BBTaskController taskController;
    @Autowired
    ExitController exitController;

    @GetMapping("/{id}")
    public ResponseResult<BBInfo> getBBInfoById(@PathVariable String id) {
        BBInfo info = service.query().eq("dxbh", id).one();
        info.setXm(CrpHelper.getXm(id));
        return ResponseResult.success(info);
    }

    // 接收入矫时自动保存
    @PostMapping("/save")
    public ResponseResult<Boolean> saveBBInfo(@RequestBody BBInfo info) {
        try {
            return ResponseResult.success(service.save(info));
        } catch (Exception e) {
            return ResponseResult.fail(false, "保存失败!");
        }
    }

    @PostMapping("/update")
    public ResponseResult<Boolean> updateBBInfo(@RequestBody BBInfo info) {
        try {
            String processId = taskController.startProcessInstance();
            info.setProcessId(processId);
            info.setStep(info.getStep() + 1);
            service.update().eq("dxbh", info.getDxbh())
                   .update(info);
            // 向公安系统发送内容
            taskController.sendGongan(info);

            return ResponseResult.success(true);
        } catch (Exception e) {
            return ResponseResult.fail(false, "更新失败!");
        }
    }

    @PostMapping("/task/accept")
    public ResponseResult<Boolean> accept(@RequestBody BBInfo info) {
        try {

            info = service.query().eq("dxbh", info.getDxbh()).one();
            System.out.println(info);
            // 向公安系统发送内容
            info = taskController.gonganAccept(info);
            service.update().eq("dxbh", info.getDxbh())
                   .update(info);
            exitController.setBB(info);

            return ResponseResult.success(true);
        } catch (Exception e) {
            return ResponseResult.fail(false, "更新失败!");
        }
    }
}
