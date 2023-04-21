package com.tars.ie.controller;

import com.tars.ie.api.ResponseResult;
import com.tars.ie.entity.IEInfo;
import com.tars.ie.service.IEInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ie")
@CrossOrigin(origins = "*")
public class IEInfoController {

    @Autowired
    private IEInfoService service;
    @Autowired
    private SuggestInfoController suggestInfoController;

    public void saveGenerateInfo(IEInfo info) {
        save(info);
    }

    @GetMapping("/test")
    public String helloIE() {
        return "Hello IE";
    }


    @GetMapping("/count")
    public ResponseResult<Long> getCount() {
        return ResponseResult.success(service.count());
    }

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
            suggestInfoController.initSuggestInfo(info.getWtbh());
            return ResponseResult.success(true);
        } catch (Exception e) {
            return ResponseResult.fail(false, "调查评估 编号 重复!");
        }
    }

    @PostMapping("/finish")
    public ResponseResult<Boolean> finish(@RequestBody IEInfo info) {
        try {
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
            return ResponseResult.success(service.removeById(info)
            );
        } catch (Exception e) {
            return ResponseResult.fail(true, "删除失败！");
        }
    }
}
