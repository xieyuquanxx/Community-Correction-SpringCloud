package com.tars.business.task.Ban;

import org.flowable.engine.delegate.TaskListener;
import org.flowable.task.service.delegate.DelegateTask;

// 社区矫正机构
public class BanJZJGTaskHandler implements TaskListener {
    @Override
    public void notify(DelegateTask delegateTask) {
        delegateTask.setAssignee("ban_jzjg");
    }
}
