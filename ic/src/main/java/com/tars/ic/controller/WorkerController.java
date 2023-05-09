package com.tars.ic.controller;

import com.tars.ic.api.ResponseResult;
import com.tars.ic.entity.Worker;
import com.tars.ic.service.WorkerService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ic/worker")
@CrossOrigin(origins = "*")
public class WorkerController {

    @Autowired
    private WorkerService service;

    @GetMapping("/all")
    public ResponseResult<List<Worker>> getAll() {
        return ResponseResult.success(service.list());
    }

    private static final SimpleDateFormat formatter =
            new SimpleDateFormat(
                    "yyyy-MM-dd");

    public void modifyTeam(List<String> workers, Long teamId) {
        List<String> oldWorkers = service.query()
                .eq("team", teamId)
                .list()
                .stream()
                .map(Worker::getId)
                .toList();
        System.out.println(workers);
        System.out.println(oldWorkers);
        if (oldWorkers.size() > 0) {
            service.update()
                    .in("id", oldWorkers)
                    .set("team", 0)
                    .set("gmt_modified", formatter.format(new Date()))
                    .update();
        }
        service.update()
                .in("id", workers)
                .set("team", teamId)
                .set("gmt_modified", formatter.format(new Date()))
                .update();
    }

    @GetMapping("/t1")
    public List<Worker> getWorkerByTeam(@RequestParam Long team) {
        return service.query().eq("team", team).select("id")
                .list();
    }

    public void updateWorker(String dxbh, String teamId) {
        service.update().eq("team", teamId)
                .set("dxbh", dxbh)
                .update();
    }

    public String getWorkersByDxbh(String dxbh) {
        StringBuilder builder = new StringBuilder();
        service.query().eq("dxbh", dxbh).list()
                .forEach(s -> builder.append(s.getXm()).append("，"));
        return builder.toString();
    }
}
