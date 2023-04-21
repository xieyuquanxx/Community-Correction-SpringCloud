package com.tars.business.controller;

import com.tars.business.entity.Ban.BanInfo;
import com.tars.business.service.task.BanTaskService;
import com.tars.business.utils.DateHelper;
import lombok.Data;
import org.flowable.engine.HistoryService;
import org.flowable.engine.ProcessEngine;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/business/ban/task")
@CrossOrigin(origins = "*")
public class BanTaskController {
    @Autowired
    private BanTaskService myService;
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
            "process") String processId) {
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

    public BanInfo sendSFS(BanInfo info) {
        // 模拟发送
        System.out.println("向司法所发送禁止令信息表");
        String processId = info.getProcessId();
        // 矫正人员步骤完成，进入司法所审批阶段
        info.setStep(info.getStep() + 1);
        complete(processId);
        return info;
    }

    // 模拟向社区矫正机构发送
    public BanInfo sendJGSH(BanInfo ban) {
        return recvSFS(ban);
    }

    // 接收委托方司法所的审核意见
    public BanInfo recvSFS(BanInfo ban) {
        ban.setSfsshr("司法所测试人员" + new Random().nextInt(100));
        ban.setSfsshsj(DateHelper.getDate());
        ban.setSfsshyj("审核意见" + new Random().nextInt(100));
        ban.setStep(ban.getStep() + 1);
        String processId = ban.getProcessId();
        // 司法所步骤完成，进入社区矫正机构审批阶段
        complete(processId);
        System.out.println("进入社区矫正机构审批阶段");
        return ban;
    }

    public BanInfo recvJZJG(BanInfo ban) {
        ban.setStep(ban.getStep() + 1);
        String processId = ban.getProcessId();
        // 社区矫正机构审批结束，得出结果
        complete(processId);
        System.out.println("进入社区矫正机构结果阶段");
        return ban;
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
