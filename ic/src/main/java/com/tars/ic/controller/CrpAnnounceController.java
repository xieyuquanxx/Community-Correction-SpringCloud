package com.tars.ic.controller;

import com.tars.ic.api.ResponseResult;
import com.tars.ic.entity.CorrectionAnnouncement;
import com.tars.ic.entity.CorrectionPeople;
import com.tars.ic.remote.RemoteOssService;
import com.tars.ic.remote.RemoteWordService;
import com.tars.ic.service.AnnounceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/ic/announce")
@CrossOrigin(origins = "*")
public class CrpAnnounceController {

    @Autowired
    private AnnounceService service;
    @Autowired
    private CrpController crpController;
    @Autowired
    private RemoteOssService ossService;
    @Autowired
    private RemoteWordService wordService;

    @GetMapping("/count")
    public ResponseResult<Long> getCount() {
        return ResponseResult.success(service.count());
    }


    @PostMapping("/upload")
    public ResponseResult<String> uploadAudio(
            @RequestParam("file") MultipartFile file) {
        return ossService.upload(file);
    }

    @GetMapping("/all")
    public ResponseResult<List<CorrectionAnnouncement>> getAll() {
        try {
            List<CorrectionAnnouncement> list =
                    service.list().stream()
                            .peek(e -> {
                                CorrectionPeople crp =
                                        crpController.getCrp(
                                                        e.getDxbh())
                                                .getData();
                                e.setXm(crp.getXm());
                            }).toList();
            System.out.println(list.size());
            return ResponseResult.success(list);
        } catch (Exception e) {
            return ResponseResult.fail(null, e.getMessage());
        }
    }

    @PostMapping("/save")
    public ResponseResult<Boolean> save(@RequestBody CorrectionAnnouncement crp) {
        try {
            crp.setFinish("1");
            service.save(crp);
            return ResponseResult.success(true);
        } catch (Exception e) {
            return ResponseResult.fail(false, e.getMessage());
        }
    }

    @PostMapping("/update")
    public ResponseResult<Boolean> update(@RequestBody CorrectionAnnouncement crp) {
        try {
            service.update().eq("dxbh", crp.getDxbh()).update(crp);
            return ResponseResult.success(true);
        } catch (Exception e) {
            return ResponseResult.fail(false, "更新失败！");
        }
    }

    private static final SimpleDateFormat simpleFormatter =
            new SimpleDateFormat("yyyy-MM-dd");

    @PostMapping("/export")
    public ResponseResult<String> export(@RequestBody CorrectionAnnouncement announce) {
        try {
            Map<String, Object> dataMap = new HashMap<>();
            dataMap.put("title", "接受入矫宣告书");
            dataMap.put("dxbh", announce.getDxbh());
            dataMap.put("xgrq", announce.getXgrq());
            dataMap.put("xm", announce.getXm());
            dataMap.put("finish", announce.getFinish());
            dataMap.put("date", simpleFormatter.format(new Date()));
//            String filename = announce.getDxbh() + "的终止矫正宣告书.doc";

            // 根据宣告信息生成word，上传到oss上后将url返回
            return wordService.export(dataMap);
        } catch (Exception e) {
            return ResponseResult.fail("", e.getMessage());
        }
    }
}
