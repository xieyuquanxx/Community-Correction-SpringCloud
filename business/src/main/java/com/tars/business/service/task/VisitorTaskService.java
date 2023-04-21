package com.tars.business.service.task;

import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VisitorTaskService extends MyTaskService {
    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    public VisitorTaskService(TaskService taskService,
                              RuntimeService runtimeService
    ) {
        super(taskService, runtimeService);
        String processKey = "VisitorProcess";
        setProcessKey(processKey);
    }

}
