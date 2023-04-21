package com.tars.assessment.task;

import org.flowable.engine.delegate.TaskListener;
import org.flowable.task.service.delegate.DelegateTask;

// 市级社区矫正机构
public class RewardLgSJTaskHandler implements TaskListener {
    @Override
    public void notify(DelegateTask delegateTask) {
        delegateTask.setAssignee("lg_sj_jzjg");
    }
}
