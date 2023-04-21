package com.tars.ie.controller;

import com.tars.ie.api.ResponseResult;
import com.tars.ie.entity.IEInfo;
import com.tars.ie.entity.SuggestInfo;
import com.tars.ie.service.IEInfoService;
import com.tars.ie.service.SuggestInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ie/suggest")
@CrossOrigin(origins = "*")
public class SuggestInfoController {

    @Autowired
    private SuggestInfoService service;


    public void initSuggestInfo(String wtbh) {
        SuggestInfo info = new SuggestInfo();
        info.setWtbh(wtbh);
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
    public ResponseResult<List<SuggestInfo>> getAll() {
        return ResponseResult.success(
                service.query().notIn("finish", -1).list());
    }

    @GetMapping("/{wtbh}")
    public ResponseResult<SuggestInfo> getIEInfo(@PathVariable(
            "wtbh") String wtbh) {
        SuggestInfo temp = service.query().eq("wtbh", wtbh).one();
        if (temp == null) {
            return ResponseResult.fail(null,
                    "没有找到委托编号为" + wtbh + "的调查评估意见书");
        } else {
            return ResponseResult.success(temp);
        }
    }


    @PostMapping("/save")
    public ResponseResult<Boolean> save(@RequestBody SuggestInfo info) {
        try {
            service.save(info);
            return ResponseResult.success(true);
        } catch (Exception e) {
            return ResponseResult.fail(false,
                    "调查评估意见书已存在!");
        }
    }


    @PostMapping("/update")
    public ResponseResult<Boolean> update(@RequestBody SuggestInfo info) {
        try {
            service.update().eq("wtbh", info.getWtbh()).update(info);
            return ResponseResult.success(true);
        } catch (Exception e) {
            return ResponseResult.fail(false, "更新失败！");
        }
    }

    @DeleteMapping("/delete")
    public ResponseResult<Boolean> delete(@RequestBody SuggestInfo info) {
        try {
            return ResponseResult.success(service.removeById(info)
            );
        } catch (Exception e) {
            return ResponseResult.fail(true, "删除失败！");
        }
    }
}
