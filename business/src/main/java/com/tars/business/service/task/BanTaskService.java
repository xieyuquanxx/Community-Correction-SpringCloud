package com.tars.business.service.task;

import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BanTaskService extends MyTaskService {
    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    public BanTaskService(TaskService taskService,
                          RuntimeService runtimeService
    ) {
        super(taskService, runtimeService);
        String processKey = "BanProcess";
        setProcessKey(processKey);
    }

}
