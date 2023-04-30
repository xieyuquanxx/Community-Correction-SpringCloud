package com.tars.ie.controller;

import com.tars.ie.api.ResponseResult;
import com.tars.ie.entity.IEInfo;
import com.tars.ie.entity.ProcessWTBH;
import com.tars.ie.service.IEInfoService;
import com.tars.ie.service.ProcessWTBHService;
import com.tars.ie.utils.GenerateIEInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/ie")
@CrossOrigin(origins = "*")
public class IEInfoController {

    @Autowired
    private IEInfoService service;
    @Autowired
    private SuggestInfoController suggestInfoController;
    @Autowired
    private IETaskController taskController;
    @Autowired
    private ProcessWTBHService processWTBHService;

    @GetMapping("/test")
    public String hello() {
        System.out.println("Hello World!");
        return "Hello World!";
    }

    public void saveGenerateInfo(IEInfo info) {
        save(info);
    }

    @GetMapping("/count")
    public ResponseResult<Long> getCount() {
        return ResponseResult.success(service.count());
    }

    /**
     * 只选择已经进入社区矫正机构环节的调查评估
     *
     * @return
     */
    @GetMapping("/all")
    public ResponseResult<List<IEInfo>> getAll() {
        return ResponseResult.success(
                service.query().notIn("finish", -1).list());
    }

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


    @PostMapping("/save")
    public ResponseResult<Boolean> save(@RequestBody IEInfo info) {
        try {
            service.save(info);
            // 同时生成调查评估意见
            suggestInfoController.initSuggestInfo(info.getWtbh());
            return ResponseResult.success(true);
        } catch (Exception e) {
            return ResponseResult.fail(false, "调查评估 编号 重复!");
        }
    }

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
            return ResponseResult.success(true);
        } catch (Exception e) {
            return ResponseResult.fail(false, "调查评估 完成失败!");
        }
    }


    @PostMapping("/update")
    public ResponseResult<Boolean> update(@RequestBody IEInfo info) {
        try {
            service.update().eq("wtbh", info.getWtbh()).update(info);
            return ResponseResult.success(true);
        } catch (Exception e) {
            return ResponseResult.fail(false, "更新失败！");
        }
    }

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

    @GetMapping("/task/all")
    public ResponseResult<List<IEInfo>> getAllDcpg() {
        return ResponseResult.success(service.query().in("finish", -1)
                                             .eq("step", 1)
                                             .list());
    }

    @PostMapping("/task/recv")
    public ResponseResult<Boolean> recvWTF() {
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
        return ResponseResult.success();
    }

    @PostMapping("/task/accept")
    public ResponseResult<Boolean> accept(@RequestBody IEInfo info) {
//        String processId = info.getProcessId();
//        HashMap<String, Object> map = new HashMap<>();
//        String res = "1";
//        map.put("checkResult", res);
//        taskController.complete(processId, map);
        info.setFinish(10);
        info.setStep(info.getStep() + 1);
        update(info);
        return ResponseResult.success();
    }

    @PostMapping("/task/unaccepted")
    public ResponseResult<Boolean> unaccepted(@RequestBody IEInfo info) {
        String processId = info.getProcessId();
        HashMap<String, Object> map = new HashMap<>();
        String res = "0";
        map.put("checkResult", res);
        taskController.complete(processId, map);
        delete(info);
        return ResponseResult.success();

    }
}
