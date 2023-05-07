package com.tars.termination.controller;

import com.tars.termination.api.ResponseResult;
import com.tars.termination.entity.TermAnnounce;
import com.tars.termination.remote.RemoteCrpService;
import com.tars.termination.remote.RemoteOssService;
import com.tars.termination.remote.RemoteWordService;
import com.tars.termination.service.TermAnnounceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/term/announce")
@CrossOrigin(origins = "*")
public class TermAnnounceController {

    @Autowired
    private TermAnnounceService service;
    @Autowired
    private RemoteCrpService crpService;
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

    private static final SimpleDateFormat simpleFormatter =
            new SimpleDateFormat("yyyy-MM-dd");

    @PostMapping("/export")
    public ResponseResult<String> export(@RequestBody TermAnnounce announce) {
        try {
            Map<String, Object> dataMap = new HashMap<>();
            dataMap.put("title", "终止矫正宣告书");
            dataMap.put("dxbh", announce.getDxbh());
            dataMap.put("xgrq", announce.getXgrq());
            dataMap.put("xm", announce.getXm());
            dataMap.put("finish", announce.getFinish());
            dataMap.put("date", simpleFormatter.format(new Date()));
//            String filename = announce.getDxbh() + "的终止矫正宣告书.doc";
//            File file = wordUtil.exportAnnounceWord(filename, dataMap);
            // 根据宣告信息生成word，上传到oss上后将url返回
            return wordService.export(dataMap);
        } catch (Exception e) {
            return ResponseResult.fail("", e.getMessage());
        }
    }

    @GetMapping("/all")
    public ResponseResult<List<TermAnnounce>> getAll() {
        try {
            List<TermAnnounce> list =
                    service.list().stream()
                            .peek(e -> e.setXm(crpService.getName(e.getDxbh()))).toList();
            return ResponseResult.success(list);
        } catch (Exception e) {
            return ResponseResult.fail(null, e.getMessage());
        }
    }

    @PostMapping("/save")
    public ResponseResult<Boolean> save(@RequestBody TermAnnounce crp) {
        try {
            crp.setFinish("0");
            service.save(crp);
            return ResponseResult.success(true);
        } catch (Exception e) {
            return ResponseResult.fail(false, e.getMessage());
        }
    }

    @PostMapping("/update")
    public ResponseResult<Boolean> update(@RequestBody TermAnnounce crp) {
        try {
            service.update().eq("dxbh", crp.getDxbh()).update(crp);
            return ResponseResult.success(true);
        } catch (Exception e) {
            return ResponseResult.fail(false, "更新失败！");
        }
    }
}
