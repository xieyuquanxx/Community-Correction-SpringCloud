package com.tars.ie.controller;

import com.tars.ie.api.ResponseResult;
import com.tars.ie.entity.ProcessWTBH;
import com.tars.ie.service.IETaskService;
import com.tars.ie.service.ProcessWTBHService;
import lombok.Data;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
public class IETaskController {
    @Autowired
    private ProcessWTBHService processWTBHService;
    @Autowired
    private IETaskService myService;


    // 开始一个调查评估流程，进入 委托方节点
    public String startProcessInstance() {
        ProcessInstance processInstance = myService.startProcess();
        return processInstance.getProcessInstanceId();
    }

    public void complete(String processId,
                         HashMap<String, Object> map) {
        Task task = myService.getTasksByProcessId(
                processId).get(0);
        myService.complete(task.getId(), map);
    }

    ProcessWTBH getProcessWTBHByProcessId(String id) {
        ProcessWTBH res = processWTBHService.query()
                                            .eq("processId", id)
                                            .one();
        if (res == null) {
            res = new ProcessWTBH(id, "");
        }
        return res;
    }

    // 根据 任务执行人获取所有的代办任务
    public ResponseResult<List<TaskRepresentation>> getTasks(
            @RequestParam String assignee) {
        List<Task> tasks = myService.getTasks(assignee);
        List<TaskRepresentation> dtos =
                tasks.stream().map(
                             (task) -> new TaskRepresentation(task.getId(),
                                     task.getProcessInstanceId(),
                                     task.getName(),
                                     task.getAssignee(),
                                     getProcessWTBHByProcessId(
                                             task.getProcessInstanceId()).getWtbh()))
                     .toList();
        return ResponseResult.success(dtos);
    }

    public List<TaskRepresentation> getTaskList(@RequestParam String processId) {
        List<Task> taskList = myService.getTasksByProcessId(
                processId);
        List<TaskRepresentation> dtos = new ArrayList<>();
        for (Task task : taskList) {
            ProcessWTBH processWTBH = getProcessWTBHByProcessId(
                    processId);
            dtos.add(new TaskRepresentation(task.getId(), processId,
                    task.getName(),
                    task.getAssignee(), processWTBH.getWtbh())
            );
        }
        return dtos;
    }

    // 委托方向矫正机构发送调查评估委托函
    public String send2JZJG(String processId) {
        Task task = myService.getTasksByProcessId(processId).get(0);
        myService.complete(task.getId());
        return processId + "进入社区矫正机构调查阶段";
    }

    @Data
    static class TaskRepresentation {
        private String taskId;
        private String processId;
        private String name;
        private String assignee;

        private String wtbh;

        public TaskRepresentation(String taskId, String processId,
                                  String name, String assignee,
                                  String wtbh) {
            this.taskId = taskId;
            this.processId = processId;
            this.name = name;
            this.assignee = assignee;
            this.wtbh = wtbh;
        }
    }

}
