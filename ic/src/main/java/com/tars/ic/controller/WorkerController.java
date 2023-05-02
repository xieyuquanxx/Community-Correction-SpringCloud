package com.tars.ic.controller;

import com.tars.ic.api.ResponseResult;
import com.tars.ic.entity.Worker;
import com.tars.ic.service.WorkerService;
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

  public void modifyTeam(List<String> workers, Integer teamId) {
    System.out.println(workers);
    List<String> oldWorkers = service.query()
        .eq("team", teamId)
        .select("rybm")
        .list()
        .stream()
        .map(Worker::getRybm).filter(s -> !workers.contains(s))
        .toList();
    System.out.println(oldWorkers);
    if (oldWorkers.size() > 0) {
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
  public List<Worker> getWorkerByTeam(@RequestParam Integer team) {
    return service.query().eq("team", team).select("rybm")
        .list();
  }
}
