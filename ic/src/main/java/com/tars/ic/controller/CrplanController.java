package com.tars.ic.controller;

import com.tars.ic.api.ResponseResult;
import com.tars.ic.entity.CorrectionInfo;
import com.tars.ic.entity.CorrectionPlan;
import com.tars.ic.remote.RemoteWordService;
import com.tars.ic.service.CrplanService;
import com.tars.ic.utils.DateUtil;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/ic/plan")
@CrossOrigin(origins = "*")
public class CrplanController {

  @Autowired
  private CrplanService service;
  @Autowired
  private CrpController crpController;
  @Autowired
  private RemoteWordService wordService;

  @GetMapping("/count")
  public ResponseResult<Long> getCount() {
    try {
      return ResponseResult.success(service.count());
    } catch (Exception e) {
      return ResponseResult.fail(-1L, e.getMessage());
    }

  }

  @PostMapping("/export")
  public ResponseResult<String> exportPlan(@RequestBody CorrectionPlan plan) {
    try {
      Map<String, Object> dataMap = new HashMap<>();
      dataMap.put("xmlName", "plan.xml");
      dataMap.put("xm", plan.getXm());
      dataMap.put("dxbh", plan.getDxbh());
      dataMap.put("date", DateUtil.now());
      dataMap.put("famc", plan.getFamc());
      dataMap.put("jzlb", plan.getJzlb());
//            String filename = announce.getDxbh() + "的终止矫正宣告书.doc";

      // 根据宣告信息生成word，上传到oss上后将url返回
      return wordService.exportInfo(dataMap);
    } catch (Exception e) {
      return ResponseResult.fail("", e.getMessage());
    }
  }

  @PostMapping("/upload")
  public ResponseResult<String> uploadPlan(
      @RequestParam("file") MultipartFile file) {
    return crpController.uploadFile(file);
  }

  @GetMapping("/all")
  public ResponseResult<List<CorrectionPlan>> getAll() {
    try {
      List<CorrectionPlan> list =
          service.list().stream()
              .peek(e -> {
                CorrectionInfo crp =
                    crpController.getCrp(e.getDxbh()).getData();
                e.setXm(crp.getXm());
                e.setJzlb(crp.getJzlb());
              }).toList();
      return ResponseResult.success(list);
    } catch (Exception e) {
      return ResponseResult.fail(null, e.getMessage());
    }
  }

  @GetMapping("/{id}")
  public ResponseResult<CorrectionPlan> getPlanById(@PathVariable("id") String id) {
    CorrectionPlan temp = service.query().eq("id", id).one();
    if (temp == null) {
      return ResponseResult.fail(null,
          "没有找到方案编号为" + id + "的矫正方案");
    } else {
      temp.setXm(crpController.getName(temp.getDxbh()));
      return ResponseResult.success(temp);
    }
  }

  @GetMapping("/plan")
  public ResponseResult<CorrectionPlan> getPlanByDxbh(
      @RequestParam("dxbh") String dxbh
  ) {
    CorrectionPlan temp = service.query().eq("dxbh", dxbh).one();
    if (temp == null) {
      return ResponseResult.fail(null,
          "没有找到对象编号为" + dxbh + "的矫正方案");
    } else {
      temp.setXm(crpController.getName(temp.getDxbh()));
      return ResponseResult.success(temp);
    }
  }

  @PostMapping("/save")
  public ResponseResult<Boolean> save(@RequestBody CorrectionPlan plan) {
    try {
      return ResponseResult.success(service.save(plan));
    } catch (Exception e) {
      return ResponseResult.fail(false, e.getMessage());
    }
  }

  @PostMapping("/update")
  public ResponseResult<Boolean> update(@RequestBody CorrectionPlan plan) {
    try {
      service.update().eq("dxbh", plan.getDxbh()).update(plan);
      return ResponseResult.success(true);
    } catch (Exception e) {
      return ResponseResult.fail(false, "更新失败！");
    }
  }
}
