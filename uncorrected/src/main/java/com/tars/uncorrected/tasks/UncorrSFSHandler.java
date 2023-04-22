package com.tars.uncorrected.tasks;

import org.flowable.engine.delegate.TaskListener;
import org.flowable.task.service.delegate.DelegateTask;

// 公安机关
public class UncorrSFSHandler implements TaskListener {
    @Override
    public void notify(DelegateTask delegateTask) {
        delegateTask.setAssignee("term_sfs");
    }
}
