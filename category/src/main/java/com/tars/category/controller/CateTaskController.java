package com.tars.category.controller;

import com.tars.category.service.CateTaskService;
import org.flowable.engine.HistoryService;
import org.flowable.engine.ProcessEngine;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/cate/task")
@CrossOrigin(origins = "*")
public class CateTaskController {
    @Autowired
    private CateTaskService myService;
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private ProcessEngine processEngine;
    @Autowired
    private HistoryService historyService;

    // 开始一个流程，进入 司法所填写审批表
    @PostMapping("/process")
    public String startProcessInstance() {
        ProcessInstance processInstance = myService.startProcess();
        return processInstance.getId();
    }

    public void complete(String processId) {
        Task task = myService.getTasksByProcessId(processId).get(0);
        System.out.println(task + "完成");
        myService.complete(task.getId());
    }

    public void complete(String processId,
                         HashMap<String, Object> map) {
        Task task = myService.getTasksByProcessId(processId)
                             .get(0);
        myService.complete(task.getId(), map);
    }
}
