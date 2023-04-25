package com.tars.ic.controller;

import com.tars.ic.api.ResponseResult;
import com.tars.ic.entity.oss.OssCallbackResult;
import com.tars.ic.entity.oss.OssPolicyResult;
import com.tars.ic.service.impl.OssServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@Slf4j
@RequestMapping("/aliyun/oss")
public class OssController {
    @Autowired
    private OssServiceImpl ossService;

    //@ApiOperation(value = "oss上传签名生成")
    @RequestMapping(value = "/policy", method = RequestMethod.GET)
    @ResponseBody
    public ResponseResult<OssPolicyResult> policy() {
        OssPolicyResult result = ossService.policy();
        return ResponseResult.success(result);
    }

    //@ApiOperation(value = "oss上传成功回调")
    @RequestMapping(value = "/callback", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult<OssCallbackResult> callback(HttpServletRequest request) {
        OssCallbackResult ossCallbackResult = ossService.callback(request);
        return ResponseResult.success(ossCallbackResult);
    }
}
