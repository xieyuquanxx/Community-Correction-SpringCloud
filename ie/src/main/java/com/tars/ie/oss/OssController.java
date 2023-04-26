package com.tars.ie.oss;

import com.tars.ie.api.ResponseResult;
import com.tars.ie.oss.entity.OssCallbackResult;
import com.tars.ie.oss.entity.OssPolicyResult;
import com.tars.ie.oss.service.OssServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
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
        OssCallbackResult ossCallbackResult = ossService.callback(
                request);
        return ResponseResult.success(ossCallbackResult);
    }
}
