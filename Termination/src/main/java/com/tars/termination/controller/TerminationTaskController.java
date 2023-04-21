package com.tars.termination.controller;

import com.tars.termination.service.TerminationTaskService;
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
@CrossOrigin(origins = "*")
public class TerminationTaskController {
    @Autowired
    private TerminationTaskService myService;
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private ProcessEngine processEngine;
    @Autowired
    private HistoryService historyService;

    // 开始一个流程，进入 司法所填报环节
    public String startProcessInstance() {
        ProcessInstance processInstance = myService.startProcess();
        return processInstance.getId();
    }

    public void complete(String processId) {
        Task task = myService.getTasksByProcessId(processId).get(0);
        System.out.println(task + "完成");
        myService.complete(task.getId());
    }

}
