package com.tars.termination.tasks;

import org.flowable.engine.delegate.TaskListener;
import org.flowable.task.service.delegate.DelegateTask;

// 社区矫正机构
public class CorrectionTaskHandler implements TaskListener {
    @Override
    public void notify(DelegateTask delegateTask) {
        delegateTask.setAssignee("term_jzjg");
    }
}
