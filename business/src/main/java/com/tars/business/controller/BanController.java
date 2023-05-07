package com.tars.business.controller;

import com.tars.business.api.ResponseResult;
import com.tars.business.entity.Ban.BanInfo;
import com.tars.business.service.ban.BanInfoService;
import com.tars.business.remote.RemoteCrpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/business/ban")
@CrossOrigin(origins = "*")
public class BanController {

    @Autowired
    private BanInfoService service;

    @Autowired
    private BanTaskController taskController;
    @Autowired
    private RemoteCrpService crpService;


    @GetMapping("/all")
    public ResponseResult<List<BanInfo>> getAllBans() {
        try {
            List<BanInfo> list = service.list().stream()
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
    public ResponseResult<Boolean> saveBanInfo(@RequestBody BanInfo ban) {
        try {
            BanInfo info = service.query().eq("dxbh", ban.getDxbh())
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
    public ResponseResult<Boolean> sendToSFS(@RequestBody BanInfo ban) {
        try {
            BanInfo info = taskController.sendSFS(ban);
            saveBanInfo(info);
            return ResponseResult.success(true);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseResult.fail(false, e.getMessage());
        }

    }

    // 模拟司法所向社区矫正机构发送信息
    @PostMapping("/sfs/jzjg")
    public ResponseResult<Boolean> sfsSendToJzjg(@RequestBody BanInfo ban) {
        try {
            BanInfo info = taskController.sendJGSH(ban);
            System.out.println(info);
            saveBanInfo(info);
            return ResponseResult.success(true);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseResult.fail(false, e.getMessage());
        }

    }

    // 发送社区矫正机构的信息，结束审批流程
    @PostMapping("/jzjg")
    public ResponseResult<Boolean> finishBBInfo(@RequestBody BanInfo ban) {
        try {
            System.out.println(ban);
            BanInfo info = taskController.recvJZJG(ban);
            saveBanInfo(info);
            return ResponseResult.success(true);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseResult.fail(false, e.getMessage());
        }
    }

}
