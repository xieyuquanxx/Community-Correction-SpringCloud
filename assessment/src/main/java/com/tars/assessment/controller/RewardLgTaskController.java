package com.tars.assessment.controller;

import com.tars.assessment.entity.RewardLgInfo;
import com.tars.assessment.service.task.RewardLgTaskService;
import com.tars.assessment.utils.DateHelper;
import lombok.Data;
import org.flowable.engine.HistoryService;
import org.flowable.engine.ProcessEngine;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/assess/reward/lg/task")
@CrossOrigin(origins = "*")
public class RewardLgTaskController {
    @Autowired
    private RewardLgTaskService myService;
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private ProcessEngine processEngine;
    @Autowired
    private HistoryService historyService;


    // 开始一个流程，进入 委托方节点
    @PostMapping("/process")
    public String startProcessInstance() {
        ProcessInstance processInstance = myService.startProcess();
        return processInstance.getId();
    }

    @GetMapping("/process_id")
    public List<TaskRepresentation> getProcessTask(@RequestParam(
            "processes") String processId) {
        return myService.getTasksByProcessId(
                                processId)
                        .stream()
                        .map(e -> new TaskRepresentation(e.getId(),
                                processId, e.getName(),
                                e.getAssignee()))
                        .toList();
    }


    public void complete(String processId) {
        Task task = myService.getTasksByProcessId(processId).get(0);
        System.out.println(task);
        myService.complete(task.getId());
    }

    // 县级矫正机构完成
    public RewardLgInfo xjComplete(RewardLgInfo info) {
        info.setStep(info.getStep() + 1);
        complete(info.getProcessId());
        return info;
    }

    // 市级矫正机构完成
    public RewardLgInfo sjComplete(RewardLgInfo info) {
        info.setStep(info.getStep() + 1);
        info.setSjsqjzjgspr("谢xx");
        info.setSjsqjzjgspsj(DateHelper.getNow());
        info.setSjsqjzjgspyj("同意");
        info.setSpjg("01");
        complete(info.getProcessId());
        return info;
    }


    @Data
    static class TaskRepresentation {
        private String taskId;
        private String processId;
        private String name;
        private String assignee;

        public TaskRepresentation(String taskId, String processId,
                                  String name, String assignee
        ) {
            this.taskId = taskId;
            this.processId = processId;
            this.name = name;
            this.assignee = assignee;
        }
    }

}
