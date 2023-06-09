package com.tars.ic.controller;

import com.aliyun.oss.model.ObjectMetadata;
import com.tars.ic.api.ResponseResult;
import com.tars.ic.entity.CorrectionAnnouncement;
import com.tars.ic.entity.CorrectionPeople;
import com.tars.ic.remote.RemoteOssService;
import com.tars.ic.service.AnnounceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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
      crp.setFinish(false);
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
}
