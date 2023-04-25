package com.tars.noexit.controller;

import com.tars.noexit.api.ResponseResult;
import com.tars.noexit.entity.BBInfo;
import com.tars.noexit.entity.Exit;
import com.tars.noexit.entity.ZJInfo;
import com.tars.noexit.service.ExitService;
import com.tars.noexit.service.remote.RemoteCrpService;
import com.tars.noexit.utils.CrpHelper;
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

    @GetMapping("/all")
    public ResponseResult<List<Exit>> getAllInfos() {
        List<Exit> list = service.list()
                .stream()
                .peek(e -> e.setXm(crpService.getName(
                        e.getDxbh())))
                .toList();
        return ResponseResult.success(list);
    }


    @PostMapping("/save")
    public ResponseResult<Boolean> saveExitInfo(@RequestBody Exit info) {
        try {
            return ResponseResult.success(service.save(info)
            );
        } catch (Exception e) {
            return ResponseResult.fail(false, "保存失败!");
        }
    }

    public void setBB(BBInfo info) {
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
        Exit exit = service.query().eq("dxbh", id).one();
        return ResponseResult.success(exit);
    }
}
