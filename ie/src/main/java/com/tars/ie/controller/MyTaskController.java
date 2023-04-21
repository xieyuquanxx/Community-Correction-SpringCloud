package com.tars.ie.controller;

import com.tars.ie.service.MyTaskService;
import lombok.Data;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.engine.*;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.runtime.Execution;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.image.ProcessDiagramGenerator;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/task2")
public class MyTaskController {

    @Autowired
    private MyTaskService myService;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private ProcessEngine processEngine;
    @Autowired
    private HistoryService historyService;

    /**
     * 1. 当决定开始调查评估流程后，进入节点1 委托方
     * 2. 委托方填写好委托文书后，委托文书会发送给决定方
     *
     * @param wtbh
     * @return
     */
    @PostMapping("/process")
    public String startProcessInstance(@RequestParam("wtbh") String wtbh) {
        //启动流程
        HashMap<String, Object> map = new HashMap<>();
        map.put("wtbh", wtbh);

        ProcessInstance processInstance = myService.startProcess(map);
        StringBuilder sb = new StringBuilder();
        sb.append("创建调查评估流程 processId：").append(processInstance.getId())
                .append("\n");
        return sb.toString();
    }

    // 代办列表查询
    @GetMapping(value = "/tasks", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TaskRepresentation> getTasks(@RequestParam String assignee) {
        List<Task> tasks = myService.getTasks(assignee);
        List<TaskRepresentation> dtos = new ArrayList<>();
        for (Task task : tasks) {
            dtos.add(new TaskRepresentation(task.getId(), task.getName()));
        }
        return dtos;
    }

    @GetMapping(value = "/apply")
    public String apply(@RequestParam("taskId") String taskId, String result) {
        Task task = myService.getTaskById(taskId);
        if (task == null) {
            throw new RuntimeException("调查流程不存在");
        }
        //通过审核
        HashMap<String, Object> map = new HashMap<>();
        map.put("checkResult", result);
        myService.complete(taskId, map);
        return result;
    }

    @GetMapping(value = "/pimg", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getProcessImage(@RequestParam("processId") String processId) throws IOException {
        // 查找历史流程
        HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery()
                .processInstanceId(processId)
                .singleResult();


        List<Execution> executions = runtimeService
                .createExecutionQuery()
                .processInstanceId(processId)
                .list();

        List<String> activityIds = new ArrayList<>();
        List<String> flows = new ArrayList<>();
        for (Execution exe : executions) {
            List<String> ids = runtimeService.getActiveActivityIds(exe.getId());
            activityIds.addAll(ids);
        }
        BpmnModel bpmnModel = repositoryService.getBpmnModel(historicProcessInstance.getProcessDefinitionId());
        ProcessEngineConfiguration engconf =
                processEngine.getProcessEngineConfiguration();
        ProcessDiagramGenerator diagramGenerator = engconf
                .getProcessDiagramGenerator();
        InputStream in = diagramGenerator.generateDiagram(
                bpmnModel, "png",
                activityIds, flows, engconf.getActivityFontName(),
                engconf.getLabelFontName(), engconf.getAnnotationFontName(),
                engconf.getClassLoader(), 1.0, true);
        byte[] bytes = new byte[in.available()];
        in.read(bytes, 0, in.available());
        return bytes;
    }

    @Data
    static class TaskRepresentation {

        private String id;
        private String name;

        public TaskRepresentation(String id, String name) {
            this.id = id;
            this.name = name;
        }


    }


}
