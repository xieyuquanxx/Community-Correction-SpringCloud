package com.tars.ie.controller;

import com.tars.ie.api.ResponseResult;
import com.tars.ie.entity.IEInfo;
import com.tars.ie.entity.ProcessWTBH;
import com.tars.ie.service.IETaskService;
import com.tars.ie.service.ProcessWTBHService;
import com.tars.ie.utils.GenerateIEInfo;
import lombok.Data;
import org.flowable.engine.HistoryService;
import org.flowable.engine.ProcessEngine;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/ie/task")
@CrossOrigin(origins = "*")
public class IETaskController {
    @Autowired
    private IEInfoController ieInfoController;
    @Autowired
    private ProcessWTBHService processWTBHService;
    @Autowired
    private IETaskService myService;
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private ProcessEngine processEngine;
    @Autowired
    private HistoryService historyService;


    // 开始一个调查评估流程，进入 委托方节点
    @PostMapping("/process")
    public String startProcessInstance() {
        ProcessInstance processInstance = myService.startProcess();

        //  生成IEInfo并保存，同时记录流程信息
        IEInfo info = GenerateIEInfo.generateIEInfo(
                GenerateIEInfo.randomWTBH());
        ieInfoController.saveGenerateInfo(info);

        processWTBHService.save(
                new ProcessWTBH(processInstance.getId(),
                        info.getWtbh()));

        return "创建调查评估流程 processId：%s\n".formatted(
                processInstance.getId());
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
    @GetMapping(value = "/tasks", produces =
            MediaType.APPLICATION_JSON_VALUE)
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

    @GetMapping("/list")
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
    @PostMapping("/jzjg")
    public String send2JZJG() {
        //生成委托函
        List<ProcessWTBH> list = processWTBHService.query().list();
        ProcessWTBH processWTBH = list.get(list.size() - 1);
        String processId = processWTBH.getProcessId();
        String wtbh = processWTBH.getWtbh();
        ieInfoController.updateWTH(wtbh, "委托调查函");
        Task task = myService.getTasksByProcessId(processId).get(0);
        myService.complete(task.getId());
        return processId + "进入社区矫正机构调查阶段";
    }

    // 矫正机构接收/退回
    @PostMapping("/recv")
    public String recv(@RequestParam("processId") String processId,
                       @RequestParam("res") String res) {
        List<Task> taskList = myService.getTasksByProcessId(
                processId);
        for (Task task : taskList) {
            if (task.getAssignee().equals("jzjg")) {
                HashMap<String, Object> map = new HashMap<>();
                map.put("checkResult", res);
                myService.complete(task.getId(), map);
                String wtbh = processWTBHService.query()
                                                .eq("processId",
                                                        processId)
                                                .one().getWtbh();
                IEInfo info = ieInfoController.getIEInfo(wtbh)
                                              .getData();
                if (res.equals("1")) {
                    info.setFinish(10);
                    ieInfoController.update(info);
                } else {
                    ieInfoController.delete(info);
                }
                break;
            }
        }
        return res.equals(
                "1") ? processId + "已接收" : processId + "已退回";
    }


    // 简单起见，委托方的文书即为调查评估信息表，当委托方apply时，进入决定方
    @PostMapping("/apply")
    public String apply(@RequestParam("tid") int taskNode,
                        @RequestParam("wtbh") String wtbh
            , @RequestParam("processId") String processId,
                        @RequestParam("checkResult") String checkResult) {
        List<Task> taskList = myService.getTasksByProcessId(
                processId);
        String assignee;
        if (taskNode == 1) {
            assignee = "decision";
            for (Task task : taskList) {
                if (task.getAssignee().equals(assignee)) {
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("checkResult", checkResult);
                    myService.complete(task.getId(), map);

                    IEInfo info = ieInfoController.getIEInfo(wtbh)
                                                  .getData();
                    if (checkResult.equals("同意")) {
                        info.setFinish(10);
                        ieInfoController.update(info);
                    } else {
                        ieInfoController.delete(info);
                    }
                    break;
                }
            }
        } else {
            assignee = "prosecutor";
        }


        return "%s commit 调查评估信息%s  ".formatted(assignee, wtbh);
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
