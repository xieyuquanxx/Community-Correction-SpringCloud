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
public class MyTaskService {

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Transactional
    public ProcessInstance startProcess(HashMap<String, Object> map) {
        // processes里的<process id="oneTaskProcess" name="The One Task Process"> id
        return runtimeService.startProcessInstanceByKey("testExample", map);
    }

    // 获得值为assignee的所有代办信息
    @Transactional
    public List<Task> getTasks(String assignee) {
        return taskService.createTaskQuery().taskAssignee(assignee).list();
    }

    @Transactional
    public Task getTaskById(String taskId) {
        return taskService.createTaskQuery()
                .taskId(taskId).singleResult();
    }
    

    @Transactional
    public void complete(String taskId, HashMap<String, Object> map) {
        taskService.complete(taskId, map);
    }
}
