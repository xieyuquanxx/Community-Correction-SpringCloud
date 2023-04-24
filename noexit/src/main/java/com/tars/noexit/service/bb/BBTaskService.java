package com.tars.noexit.service.bb;

import com.tars.noexit.service.MyTaskService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

@Service
public class BBTaskService extends MyTaskService {
    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    public BBTaskService(TaskService taskService,
                         RuntimeService runtimeService) {
        super(taskService, runtimeService);
        String processKey = "BBProcess";
        setProcessKey(processKey);
    }

}
