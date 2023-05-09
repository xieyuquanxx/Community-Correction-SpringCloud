package com.tars.ic.controller;

import com.tars.ic.api.ResponseResult;
import com.tars.ic.entity.CorrectionTeam;
import com.tars.ic.entity.CorrectionTeamTemp;
import com.tars.ic.entity.Worker;
import com.tars.ic.service.CrtService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ic/crt")
@CrossOrigin(origins = "*")
public class CrtController {

    @Autowired
    private CrtService service;
    @Autowired
    private WorkerController workerController;
    private static final SimpleDateFormat formatter =
            new SimpleDateFormat("yyyy-MM-dd");

    @PostMapping("/save")
    public ResponseResult<Boolean> save(@RequestBody CorrectionTeam info) {
        try {
            CorrectionTeamTemp temp =
                    CorrectionTeamTemp.builder()
                            .id(info.getId())
                            .name(info.getName())
                            .monitor(info.getMonitor())
                            .number(info.getNumber())
                            .gmt_create(formatter.format(new Date()))
                            .build();

            service.save(temp);
            info.setId(temp.getId());

            workerController.modifyTeam(info.getWorkers(), info.getId());
            return ResponseResult.success(true);
        } catch (Exception e) {
            return ResponseResult.fail(false, e.getMessage());
        }
    }

    @PostMapping("/update")
    public ResponseResult<Boolean> update(@RequestBody CorrectionTeam crp) {
        try {
            CorrectionTeamTemp temp =
                    CorrectionTeamTemp.builder()
                            .id(crp.getId())
                            .name(crp.getName())
                            .monitor(crp.getMonitor())
                            .number(crp.getNumber())
                            .gmt_modified(formatter.format(new Date()))
                            .build();

            service.update().eq("id", crp.getId()).update(temp);
            System.out.println(crp.getWorkers());
            workerController.modifyTeam(crp.getWorkers(), crp.getId());

            return ResponseResult.success(true);
        } catch (Exception e) {
            return ResponseResult.fail(false, "更新失败！");
        }
    }

    @GetMapping("/all")
    public ResponseResult<List<CorrectionTeam>> getAll() {
        List<CorrectionTeamTemp> tempList = service.list();
        List<CorrectionTeam> team = new ArrayList<>();
        for (CorrectionTeamTemp t : tempList) {
            List<String> workers =
                    workerController.getWorkerByTeam(t.getId())
                            .stream().map(Worker::getId)
                            .toList();
            CorrectionTeam c = new CorrectionTeam(t);
            c.getWorkers().addAll(workers);
            team.add(c);
        }
        return ResponseResult.success(team);
    }

}
