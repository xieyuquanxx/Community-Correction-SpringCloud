package com.tars.noexit.controller;

import com.tars.noexit.api.ResponseResult;
import com.tars.noexit.entity.NumberData;
import com.tars.noexit.entity.ReportInfo;
import com.tars.noexit.entity.Exit;
import com.tars.noexit.entity.ZJInfo;
import com.tars.noexit.service.ExitService;
import com.tars.noexit.remote.RemoteCrpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/noexit/info")
@CrossOrigin(origins = "*")
public class ExitController {

    @Autowired
    private ExitService service;

    @Autowired
    private RemoteCrpService crpService;

    @GetMapping("/counts")
    public ResponseResult<NumberData> getCount() {
        try {
            NumberData numberData = NumberData.builder()
                    .reportNumber(service.query().eq("bb", "1").count())
                    .zjNumber(service.query().ne("zj", "06").count())
                    .bkNumber(service.query().ne("bk", "0").count())
                    .build();
            return ResponseResult.success(numberData);
        } catch (Exception e) {
            return ResponseResult.fail(null, e.getMessage());
        }
    }

    @GetMapping("/all")
    public ResponseResult<List<Exit>> getAllInfo() {
        try {
            List<Exit> list = service.list()
                    .stream()
                    .peek(e -> e.setXm(crpService.getName(
                            e.getDxbh())))
                    .toList();
            return ResponseResult.success(list);
        } catch (Exception e) {
            return ResponseResult.fail(null, e.getMessage());
        }

    }


    @PostMapping("/save")
    public ResponseResult<Boolean> saveExitInfo(@RequestBody Exit info) {
        try {
            return ResponseResult.success(
                    service.save(info)
            );
        } catch (Exception e) {
            return ResponseResult.fail(false, "保存失败!");
        }
    }

    public void setBB(ReportInfo info) {
        Exit exit = service.query().eq("dxbh", info.getDxbh()).one();
        exit.setBb("1");
        service.update()
                .eq("dxbh", info.getDxbh())
                .update(exit);
    }

    public void setZj(ZJInfo info) {
        Exit exit = service.query().eq("dxbh", info.getDxbh()).one();
        exit.setZj(info.getZj());
        service.update()
                .eq("dxbh", info.getDxbh())
                .update(exit);
    }

    @GetMapping("/{id}")
    public ResponseResult<Exit> getExitInfo(@PathVariable String id) {
        try {
            Exit exit = service.query().eq("dxbh", id).one();
            return ResponseResult.success(exit);
        } catch (Exception e) {
            return ResponseResult.fail(null, e.getMessage());
        }
    }
}
