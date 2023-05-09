package com.tars.ic.controller;

import com.tars.ic.api.ResponseResult;
import com.tars.ic.entity.CorrectionAnnounce;
import com.tars.ic.entity.CorrectionPeople;
import com.tars.ic.remote.RemoteOssService;
import com.tars.ic.remote.RemoteWordService;
import com.tars.ic.service.AnnounceService;
import com.tars.ic.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    @Autowired
    private WorkerController workerController;

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
    public ResponseResult<List<CorrectionAnnounce>> getAll() {
        try {
            List<CorrectionAnnounce> list =
                    service.list().stream()
                            .peek(e -> {
                                CorrectionPeople crp =
                                        crpController.getCrp(e.getDxbh()).getData();
                                e.setXm(crp.getXm());
                            }).toList();
            System.out.println(list.size());
            return ResponseResult.success(list);
        } catch (Exception e) {
            return ResponseResult.fail(null, e.getMessage());
        }
    }

    @PostMapping("/save")
    public ResponseResult<Boolean> save(@RequestBody CorrectionAnnounce crp) {
        try {
            crp.setFinish("0")
                    .setGmt_create(DateUtil.now());
            service.save(crp);
            return ResponseResult.success(true);
        } catch (Exception e) {
            return ResponseResult.fail(false, e.getMessage());
        }
    }

    @PostMapping("/update")
    public ResponseResult<Boolean> update(@RequestBody CorrectionAnnounce crp) {
        try {
            service.update().eq("dxbh", crp.getDxbh())
                    .set("gmt_modified", DateUtil.now())
                    .update(crp);
            return ResponseResult.success(true);
        } catch (Exception e) {
            return ResponseResult.fail(false, "更新失败！");
        }
    }


    @PostMapping("/export")
    public ResponseResult<String> export(
            @RequestBody CorrectionAnnounce announce) {
        try {
            Map<String, Object> dataMap = new HashMap<>();
            dataMap.put("xmlName", "announce.xml");
            dataMap.put("title", "社区矫正宣告书");
            dataMap.put("xm", announce.getXm());
            dataMap.put("date", DateUtil.now());
            dataMap.put("workers", workerController.getWorkersByDxbh(announce.getDxbh()));
//            String filename = announce.getDxbh() + "的终止矫正宣告书.doc";

            // 根据宣告信息生成word，上传到oss上后将url返回
            return wordService.exportInfo(dataMap);
        } catch (Exception e) {
            return ResponseResult.fail("", e.getMessage());
        }
    }
}
