package com.tars.business.task.Ban;

import org.flowable.engine.delegate.TaskListener;
import org.flowable.task.service.delegate.DelegateTask;


// 委托司法所
public class BanSFSTaskHandler implements TaskListener {

    @Override
    public void notify(DelegateTask delegateTask) {
        delegateTask.setAssignee("ban_sfs");
    }


}