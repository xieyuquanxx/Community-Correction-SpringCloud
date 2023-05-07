package com.tars.noexit.controller.bb;

import com.tars.noexit.api.ResponseResult;
import com.tars.noexit.controller.ExitController;
import com.tars.noexit.entity.ReportInfo;
import com.tars.noexit.service.bb.ReportInfoService;
import com.tars.noexit.remote.RemoteCrpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/noexit/bb")
@CrossOrigin(origins = "*")
public class ReportController {

    @Autowired
    private ReportInfoService service;
    @Autowired
    private ReportTaskController taskController;
    @Autowired
    private ExitController exitController;
    @Autowired
    private RemoteCrpService crpService;

    @GetMapping("/{id}")
    public ResponseResult<ReportInfo> getBBInfoById(@PathVariable String id) {
        try {
            ReportInfo info = service.query().eq("dxbh", id).one();
            info.setXm(crpService.getName(id));
            return ResponseResult.success(info);
        } catch (Exception e) {
            return ResponseResult.fail(null, e.getMessage());
        }
    }

    // 接收入矫时自动保存
    @PostMapping("/save")
    public ResponseResult<Boolean> saveBBInfo(@RequestBody ReportInfo info) {
        try {
            return ResponseResult.success(service.save(info));
        } catch (Exception e) {
            return ResponseResult.fail(false, "保存失败!");
        }
    }

    @PostMapping("/modify")
    public ResponseResult<Boolean> modifyBBInfo(@RequestBody ReportInfo info) {
        try {
            return ResponseResult.success(
                    service.update()
                            .eq("dxbh", info.getDxbh())
                            .update(info)
            );
        } catch (Exception e) {
            return ResponseResult.fail(false, "保存失败!");
        }
    }


    @PostMapping("/update")
    public ResponseResult<Boolean> updateBBInfo(@RequestBody ReportInfo info) {
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
    public ResponseResult<Boolean> accept(@RequestBody ReportInfo info) {
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

    @PostMapping("/task/refuse")
    public ResponseResult<Boolean> refuse(@RequestBody ReportInfo info) {
        try {

            info = service.query().eq("dxbh", info.getDxbh()).one();
            System.out.println(info);
            // 向公安系统发送内容
            info = taskController.refuse(info);
            service.update().eq("dxbh", info.getDxbh())
                    .update(info);

            return ResponseResult.success(true);
        } catch (Exception e) {
            return ResponseResult.fail(false, "更新失败!");
        }
    }
}
