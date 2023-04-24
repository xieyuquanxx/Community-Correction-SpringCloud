package com.tars.noexit.controller.bb;

import com.tars.noexit.entity.BBInfo;
import com.tars.noexit.service.bb.BBTaskService;
import org.flowable.engine.HistoryService;
import org.flowable.engine.ProcessEngine;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@CrossOrigin(origins = "*")
public class BBTaskController {
    @Autowired
    private BBTaskService myService;
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private ProcessEngine processEngine;
    @Autowired
    private HistoryService historyService;

    // 开始一个流程，进入 社区矫正机构填写表单环节
    public String startProcessInstance() {
        ProcessInstance processInstance = myService.startProcess();
        return processInstance.getId();
    }

    public void complete(String processId) {
        Task task = myService.getTasksByProcessId(processId).get(0);
        System.out.println(task + "完成");
        myService.complete(task.getId());
    }

    public void sendGongan(BBInfo info) {
        System.out.println("向公安发送报备信息" + info);
        complete(info.getProcessId());
    }

    // 公安同意
    public BBInfo gonganAccept(BBInfo info) {
        info.setStep(info.getStep() + 1);
        HashMap<String, Object> map = new HashMap<>();
        int result = 1;
        map.put("result", result);
        Task task = myService.getTasksByProcessId(info.getProcessId())
                             .get(0);
        myService.complete(task.getId(), map);
        System.out.println("公安同意");
        return info;
    }

}
