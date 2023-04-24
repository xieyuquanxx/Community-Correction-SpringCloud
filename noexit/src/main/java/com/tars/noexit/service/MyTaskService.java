package com.tars.noexit.service;

import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

public class MyTaskService {
    private final TaskService taskService;
    private final RuntimeService runtimeService;
    private String processKey;

    public MyTaskService(TaskService taskService,
                         RuntimeService runtimeService
    ) {
        this.taskService = taskService;
        this.runtimeService = runtimeService;
    }

    public void setProcessKey(String key) {
        processKey = key;
    }

    @Transactional
    public ProcessInstance startProcess() {
        return runtimeService
                .startProcessInstanceByKey(processKey);
    }

    // 获得办理人为assignee的所有代办信息
    @Transactional
    public List<Task> getTasks(String assignee) {
        return taskService.createTaskQuery().taskAssignee(assignee)
                          .list();
    }

    @Transactional
    public List<Task> getTasksByProcessId(String processId) {
        return taskService.createTaskQuery()
                          .processInstanceId(processId)
                          .list();
    }

    @Transactional
    public Task getTaskByProcessIdAndAssignee(String processId,
                                              String assignee) {
        return taskService.createTaskQuery()
                          .processInstanceId(processId)
                          .taskAssignee(assignee)
                          .singleResult();
    }

    // 根据taskId来获取task
    @Transactional
    public Task getTaskById(String taskId) {
        return taskService.createTaskQuery()
                          .taskId(taskId).singleResult();
    }

    @Transactional
    public void complete(String taskId, HashMap<String, Object> map) {
        taskService.complete(taskId, map);
    }

    @Transactional
    public void complete(String taskId) {
        taskService.complete(taskId);
    }
}
