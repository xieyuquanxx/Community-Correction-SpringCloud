package com.tars.assessment.service.task;

import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RewardLgTaskService extends MyTaskService {
    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    public RewardLgTaskService(TaskService taskService,
                               RuntimeService runtimeService
    ) {
        super(taskService, runtimeService);
        String processKey = "lgProcess";
        setProcessKey(processKey);
    }

}
