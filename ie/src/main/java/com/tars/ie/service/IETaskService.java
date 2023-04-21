package com.tars.ie.service;

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
public class IETaskService {
    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    // 启动调查评估流程
    @Transactional
    public ProcessInstance startProcess() {
        return runtimeService
                .startProcessInstanceByKey("IEProcess");
    }


    // 获得办理人为assignee的所有代办信息
    @Transactional
    public List<Task> getTasks(String assignee) {
        return taskService.createTaskQuery().taskAssignee(assignee).list();
    }

    @Transactional
    public List<Task> getTasksByProcessId(String processId) {
        return taskService.createTaskQuery()
                .processInstanceId(processId)
                .list();
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
