package com.tars.noexit.service.zj;

import com.tars.noexit.service.MyTaskService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ZJTaskService extends MyTaskService {
    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    public ZJTaskService(TaskService taskService,
                         RuntimeService runtimeService) {
        super(taskService, runtimeService);
        String processKey = "ZJProcess";
        setProcessKey(processKey);
    }

}
