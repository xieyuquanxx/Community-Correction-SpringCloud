package com.tars.ie.controller;

import com.tars.ie.api.ResponseResult;
import com.tars.ie.entity.IEInfo;
import com.tars.ie.entity.ProcessWTBH;
import com.tars.ie.entity.other.CorrectionPeople;
import com.tars.ie.remote.RemoteCrpService;
import com.tars.ie.service.IEInfoService;
import com.tars.ie.service.ProcessWTBHService;
import com.tars.ie.utils.GenerateIEInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/ie")
@CrossOrigin(origins = "*")
@Tag(name = "IEInfoController", description = "调查评估信息接口类")
public class IEInfoController {

  @Autowired
  private IEInfoService service;
  @Autowired
  private SuggestInfoController suggestInfoController;
  @Autowired
  private IETaskController taskController;
  @Autowired
  private ProcessWTBHService processWTBHService;
  @Autowired
  private RemoteCrpService crpService;


  /**
   * 保存调查评估信息类
   *
   * @param info 调查评估信息类
   */
  public void saveGenerateInfo(IEInfo info) {
    try {
      save(info);
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  @Operation(summary = "获取调查评估信息的个数")
  @GetMapping("/count")
  public ResponseResult<Long> getCount() {
    try {
      long count = service.count();
      return ResponseResult.success(count);
    } catch (Exception e) {
      return ResponseResult.fail(-1L, e.getMessage());
    }
  }


  @Operation(summary = "获取所有的调查评估信息")
  @GetMapping("/all")
  public ResponseResult<List<IEInfo>> getAll() {
    try {
      return ResponseResult.success(
          service.query().notIn("finish", -1).list());
    } catch (Exception e) {
      return ResponseResult.fail(null, e.getMessage());
    }
  }

  @Operation(summary = "根据委托编号获取调查评估信息", parameters =
      {@Parameter(name = "wtbh", description = "委托编号")})
  @GetMapping("/{wtbh}")
  public ResponseResult<IEInfo> getIEInfo(@PathVariable("wtbh") String wtbh) {
    IEInfo temp = service.query().eq("wtbh", wtbh).one();
    if (temp == null) {
      return ResponseResult.fail(null,
          "没有找到委托编号为" + wtbh + "的调查评估");
    } else {
      return ResponseResult.success(temp);
    }
  }

  @Operation(summary = "保存调查评估信息")
  @PostMapping("/save")
  public ResponseResult<Boolean> save(@RequestBody IEInfo info) {
    try {
      service.save(info);
      // 同时生成调查评估意见
      suggestInfoController.initSuggestInfo(info.getWtbh());
      return ResponseResult.success(true);
    } catch (Exception e) {
      return ResponseResult.fail(false,
          "调查评估 编号 重复!" + e.getMessage());
    }
  }

  @Operation(summary = "结束一个调查评估流程")
  @PostMapping("/finish")
  public ResponseResult<Boolean> finish(@RequestBody IEInfo info) {
    try {
      HashMap<String, Object> map = new HashMap<>();
      String res = "1";
      map.put("checkResult", res);
      taskController.complete(info.getProcessId(), map);
      service.update().
          eq("wtbh", info.getWtbh())
          .set("finish", 0).update();

      crpService.finishDcpg(info.getWtbh());
      return ResponseResult.success(true);
    } catch (Exception e) {
      return ResponseResult.fail(false, "调查评估 完成失败!");
    }
  }

  @Operation(summary = "更新调查评估信息")
  @PostMapping("/update")
  public ResponseResult<Boolean> update(@RequestBody IEInfo info) {
    try {
      service.update().eq("wtbh", info.getWtbh()).update(info);
      return ResponseResult.success(true);
    } catch (Exception e) {
      return ResponseResult.fail(false, "更新失败！");
    }
  }

  @Operation(summary = "更新调查评估所需要的时间")
  @PostMapping("/update/time")
  public ResponseResult<Boolean> updateTime(@RequestBody IEInfo info) {
    try {
      service.update().eq("wtbh", info.getWtbh())
          .set("finish", info.getFinish()).update();
      return ResponseResult.success(true);
    } catch (Exception e) {
      return ResponseResult.fail(false, "更新失败！");
    }
  }

  // 更新委托函
  public void updateWTH(String wtbh,
      String wth) {
    try {
      service.update().eq("wtbh", wtbh)
          .set("wtdch", wth).update();
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  @Operation(summary = "删除一个调查评估信息")
  @DeleteMapping("/delete")
  public ResponseResult<Boolean> delete(@RequestBody IEInfo info) {
    try {
      return ResponseResult.success(
          service.removeById(info)
      );
    } catch (Exception e) {
      return ResponseResult.fail(true, "删除失败！");
    }
  }

  /**
   * 流程审批api
   */
  @Operation(summary = "模拟：开启一个调查评估流程")
  @GetMapping("/task/start")
  public ResponseResult<Boolean> startProcess() {
    // 随机生成委托函信息
    IEInfo info = GenerateIEInfo.generateIEInfo(
        GenerateIEInfo.randomWTBH());
    info.setProcessId(taskController.startProcessInstance());
    System.out.println(info);
    // 保存
    saveGenerateInfo(info);

    processWTBHService.save(
        new ProcessWTBH(info.getProcessId(),
            info.getWtbh()));
    return ResponseResult.success();
  }

  @Operation(summary = "获取所有未被接收的调查评估信息")
  @GetMapping("/task/all")
  public ResponseResult<List<IEInfo>> getAllDcpg() {
    return ResponseResult.success(service.query().in("finish", -1)
        .eq("step", 1)
        .list());
  }

  @Operation(summary = "接收一个调查评估流程")
  @PostMapping("/task/recv")
  public ResponseResult<Boolean> recvWTF() {
    try {

      List<ProcessWTBH> list = processWTBHService.query().list();
      ProcessWTBH processWTBH = list.get(list.size() - 1);
      String processId = processWTBH.getProcessId();
      String wtbh = processWTBH.getWtbh();
      updateWTH(wtbh, "委托调查函");
      // 更新步骤
      IEInfo data = getIEInfo(wtbh).getData();
      data.setStep(data.getStep() + 1);
      update(data);

      taskController.send2JZJG(processId);

      return ResponseResult.success(true);
    } catch (Exception e) {
      return ResponseResult.fail(false, e.getMessage());
    }
  }

  @Operation(summary = "系统接收一个调查评估信息")
  @PostMapping("/task/accept")
  public ResponseResult<Boolean> accept(@RequestBody IEInfo info) {
    info.setFinish(10);
    info.setStep(info.getStep() + 1);
    update(info);
    // 创建待入矫人员信息
    CorrectionPeople crp = CorrectionPeople.builder()
        .xm(info.getBgrxm())
        .dxbh(info.getWtbh())
        .sfzhm(info.getBgrsfzh())
        .xb(info.getBgrxb())
        .status("待入矫")
        .csrq("1999-1-1")
        .build();
    crpService.register(crp);
    return ResponseResult.success(true);
  }

  @Operation(summary = "系统退回一个调查评估信息")
  @PostMapping("/task/unaccepted")
  public ResponseResult<Boolean> unaccepted(@RequestBody IEInfo info) {
    String processId = info.getProcessId();
    HashMap<String, Object> map = new HashMap<>();
    String res = "0";
    map.put("checkResult", res);
    taskController.complete(processId, map);
    delete(info);
    return ResponseResult.success(true);

  }
}
