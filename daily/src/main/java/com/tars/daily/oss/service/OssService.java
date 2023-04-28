package com.tars.daily.oss.service;

import com.tars.daily.oss.entity.OssCallbackResult;
import com.tars.daily.oss.entity.OssPolicyResult;
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
