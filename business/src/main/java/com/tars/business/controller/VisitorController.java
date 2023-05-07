package com.tars.business.controller;

import com.tars.business.api.ResponseResult;
import com.tars.business.entity.Visitor.VisitorInfo;
import com.tars.business.remote.RemoteCrpService;
import com.tars.business.service.visitor.VisitorInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/business/visitor")
@CrossOrigin(origins = "*")
public class VisitorController {

    @Autowired
    private VisitorInfoService service;

    @Autowired
    private VisitorTaskController taskController;
    @Autowired
    private RemoteCrpService crpService;


    @GetMapping("/all")
    public ResponseResult<List<VisitorInfo>> getAllVisitor() {
        try {
            List<VisitorInfo> list = service.list().stream()
                    .peek(e -> e.setXm(
                            crpService.getName(
                                    e.getDxbh())))
                    .toList();
            return ResponseResult.success(list);
        } catch (Exception e) {
            return ResponseResult.fail(null, e.getMessage());
        }

    }

    @PostMapping("/save")
    public ResponseResult<Boolean> saveVisitorInfo(@RequestBody VisitorInfo ban) {
        try {
            VisitorInfo info = service.query()
                    .eq("dxbh", ban.getDxbh())
                    .one();
            if (info == null) {
                // 新建一个审批流程
                ban.setProcessId(
                        taskController.startProcessInstance());
                service.save(ban);
            } else {
                System.out.println(ban);
                service.update()
                        .eq("dxbh", ban.getDxbh())
                        .update(ban);
            }
            return ResponseResult.success(true);
        } catch (Exception e) {
            return ResponseResult.fail(false, e.getMessage());
        }
    }

    // 向司法所发送禁止令表，让司法所审核
    @PostMapping("/sfs")
    public ResponseResult<Boolean> sendToSFS(@RequestBody VisitorInfo ban) {
        try {
            VisitorInfo info = taskController.sendSFS(ban);
            saveVisitorInfo(info);
            return ResponseResult.success(true);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseResult.fail(false, e.getMessage());
        }

    }

    // 模拟司法所向社区矫正机构发送信息
    @PostMapping("/sfs/jzjg")
    public ResponseResult<Boolean> sfsSendToJzjg(@RequestBody VisitorInfo ban) {
        try {
            VisitorInfo info = taskController.sendJGSH(ban);
            System.out.println(info);
            saveVisitorInfo(info);
            return ResponseResult.success(true);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseResult.fail(false, e.getMessage());
        }

    }

    // 发送社区矫正机构的信息
    @PostMapping("/jzjg")
    public ResponseResult<Boolean> finishVisitor(@RequestBody VisitorInfo ban) {
        try {
            System.out.println(ban);
            VisitorInfo info = taskController.recvJZJG(ban);
            saveVisitorInfo(info);
            return ResponseResult.success(true);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseResult.fail(false, e.getMessage());
        }
    }


}
