package com.tars.business.controller;

import com.tars.business.api.ResponseResult;
import com.tars.business.entity.Ban.BanInfo;
import com.tars.business.service.ban.BanInfoService;
import com.tars.business.utils.CrpHelper;
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

    public BanInfo getBanInfoByDXBH(String dxbh) {
        return service.query()
                      .eq("dxbh", dxbh)
                      .one();
    }

    @GetMapping("/all")
    public ResponseResult<List<BanInfo>> getAllBans() {
        List<BanInfo> list = service.list().stream()
                                    .peek(e -> e.setXm(
                                            CrpHelper.getXm(
                                                    e.getDxbh())))
                                    .toList();
        return ResponseResult.success(list);
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
    public void sendToSFS(@RequestBody BanInfo ban) {
        BanInfo info = taskController.sendSFS(ban);
        saveBanInfo(info);
    }

    // 模拟司法所向社区矫正机构发送信息
    @PostMapping("/sfs/jzjg")
    public void sfsSendToJzjg(@RequestBody BanInfo ban) {
        System.out.println(ban);
        BanInfo info = taskController.sendJGSH(ban);
        System.out.println(info);
        saveBanInfo(info);
    }

    // 发送社区矫正机构的信息
    @PostMapping("/jzjg")
    public void sendToJZJG(@RequestBody BanInfo ban) {
        System.out.println(ban);
        BanInfo info = taskController.recvJZJG(ban);
        saveBanInfo(info);
    }

}
