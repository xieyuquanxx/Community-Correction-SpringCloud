package com.tars.business.task.Visitor;

import org.flowable.engine.delegate.TaskListener;
import org.flowable.task.service.delegate.DelegateTask;


// 委托司法所
public class VisitorSFSTaskHandler implements TaskListener {

    @Override
    public void notify(DelegateTask delegateTask) {
        delegateTask.setAssignee("visitor_sfs");
    }


}