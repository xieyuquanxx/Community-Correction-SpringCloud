package com.tars.uncorrected.controller;

import com.tars.uncorrected.api.ResponseResult;
import com.tars.uncorrected.entity.UnCorrAnnouncement;
import com.tars.uncorrected.entity.UnCorrInfo;
import com.tars.uncorrected.service.UnCorrAnnouncementInfoService;
import com.tars.uncorrected.service.UnCorrInfoService;
import com.tars.uncorrected.utils.CrpHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/uncorrected/announcement")
@CrossOrigin(origins = "*")
public class UnCorrAnnouncementController {
    @Autowired
    private UnCorrAnnouncementInfoService service;

    /**
     * 根据对象编号获取终止矫正细腻系
     *
     * @param dxbh 对象编号
     * @return 终止矫正信息
     */
    @GetMapping("/{dxbh}")
    public ResponseResult<UnCorrAnnouncement> getInfoByDxbh(@PathVariable("dxbh") String dxbh) {
        UnCorrAnnouncement info = service.query().eq("dxbh", dxbh).one();
        if (info == null)
            return ResponseResult.fail(null, "未找到解除矫正信息");
        info.setXm(CrpHelper.getXm(dxbh));
        return ResponseResult.success(info);
    }

    @GetMapping("/all")
    public ResponseResult<List<UnCorrAnnouncement>> getAll() {
        List<UnCorrAnnouncement> list = service.list().stream()
                .peek(e -> e.setXm(
                        CrpHelper.getXm(
                                e.getDxbh())))
                .toList();
        return ResponseResult.success(list);
    }

    /**
     * 保存解除矫正信息
     *
     * @param info 前端传来的终止矫正信息
     * @return 返回保存状态
     */
    @PostMapping("/save")
    public ResponseResult<Boolean> saveInfo(@RequestBody UnCorrAnnouncement info) {
        try {
            return ResponseResult.success(service.save(info));
        } catch (Exception e) {
            return ResponseResult.fail(false,
                    "保存失败!" + e.getMessage());
        }
    }

    @PostMapping("/update")
    public ResponseResult<Boolean> updateInfo(@RequestBody UnCorrAnnouncement info) {
        try {
            service.update().eq("dxbh", info.getDxbh())
                    .update(info);

            return ResponseResult.success(true);
        } catch (Exception e) {
            return ResponseResult.fail(false, "更新失败!");
        }
    }

    @PostMapping("/finish")
    public ResponseResult<Boolean> finishInfo(@RequestBody UnCorrAnnouncement info) {
        try {
            service.update().eq("dxbh", info.getDxbh())
                    .set("finish", "01")
                    .update();
            return ResponseResult.success(true);
        } catch (Exception e) {
            return ResponseResult.fail(false, "更新失败!");
        }
    }


}
