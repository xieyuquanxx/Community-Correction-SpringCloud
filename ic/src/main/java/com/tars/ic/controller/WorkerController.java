package com.tars.ic.controller;

import com.tars.ic.api.ResponseResult;
import com.tars.ic.entity.CorrectionPeople;
import com.tars.ic.entity.Worker;
import com.tars.ic.service.WorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    public void modifyTeam(List<String> workers, String teamId) {
        System.out.println(workers);
        List<String> oldWorkers = service.query()
                                   .eq("team", teamId)
                                   .select("rybm")
                                   .list()
                                   .stream()
                                   .map(Worker::getRybm).filter(s -> !workers.contains(s))
                                   .toList();
        System.out.println(oldWorkers);
        if(oldWorkers.size() > 0) {
            service.update()
                   .in("rybm", oldWorkers)
                   .set("team", 0)
                   .update();
        }
        service.update()
                .in("rybm", workers)
                .set("team", teamId)
                .update();
    }

    @GetMapping("/t1")
    public List<Worker> getWorkerByTeam(@RequestParam String team) {
        return service.query().eq("team", team).select("rybm")
                .list();
    }
}
