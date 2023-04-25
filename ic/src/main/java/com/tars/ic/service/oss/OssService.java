package com.tars.ic.service.oss;

import com.tars.ic.entity.oss.OssCallbackResult;
import com.tars.ic.entity.oss.OssPolicyResult;
import jakarta.servlet.http.HttpServletRequest;

public interface OssService {
    /**
     * oss上传策略生成
     */
    OssPolicyResult policy();

    /**
     * oss上传成功回调
     */
    OssCallbackResult callback(HttpServletRequest request);
}
