package com.tars.business.task.Visitor;

import org.flowable.engine.delegate.TaskListener;
import org.flowable.task.service.delegate.DelegateTask;

// 社区矫正机构
public class VisitorJZJGTaskHandler implements TaskListener {
    @Override
    public void notify(DelegateTask delegateTask) {
        delegateTask.setAssignee("visitor_jzjg");
    }
}
