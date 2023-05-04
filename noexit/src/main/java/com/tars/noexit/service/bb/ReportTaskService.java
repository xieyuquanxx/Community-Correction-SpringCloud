package com.tars.noexit.service.bb;

import com.tars.noexit.service.MyTaskService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportTaskService extends MyTaskService {

  @Autowired
  private RuntimeService runtimeService;

  @Autowired
  private TaskService taskService;

  public ReportTaskService(TaskService taskService,
      RuntimeService runtimeService) {
    super(taskService, runtimeService);
    String processKey = "BBProcess";
    setProcessKey(processKey);
  }

}
