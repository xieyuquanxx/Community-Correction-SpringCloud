package com.tars.ic.controller;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import com.tars.ic.api.ResponseResult;
import com.tars.ic.entity.*;
import com.tars.ic.entity.oss.OssPolicyResult;
import com.tars.ic.entity.others.BBInfo;
import com.tars.ic.entity.others.Exit;
import com.tars.ic.entity.others.ScoreInfo;
import com.tars.ic.entity.others.ZJInfo;
import com.tars.ic.service.CrpCheckService;
import com.tars.ic.service.CrpService;
import com.tars.ic.service.remote.RemoteAssessmentService;
import com.tars.ic.service.remote.RemoteCateService;
import com.tars.ic.service.remote.RemoteNoExitService;
import com.tars.ic.service.remote.RemoteOssService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/ic/crp")
@CrossOrigin(origins = "*")
@Slf4j
public class CrpController {
    @Autowired
    private CrpService service;
    @Autowired
    private CrpCheckService checkService;

    @Autowired
    private RemoteCateService cateService;
    @Autowired
    private RemoteAssessmentService assessmentService;
    @Autowired
    private RemoteNoExitService exitService;
    @Autowired
    private FileController fileController;
    @Autowired
    private OssController ossController;
    @Autowired
    private RemoteOssService ossService;

    private static final SimpleDateFormat formatter = new SimpleDateFormat(
            "yyyy-MM-dd");

    @GetMapping("/xm/{dxbh}")
    public String getName(@PathVariable("dxbh") String dxbh) {
        CorrectionPeople dx = service.query().eq("dxbh", dxbh).one();
        return dx.getXm();
    }

    @GetMapping("/random")
    public String randomDxbh() {
        List<CorrectionPeople> list = service.list();
        Random random = new Random();
        int i = random.nextInt(list.size());
        return list.get(i).getDxbh();
    }

    @Autowired
    private OSSClient ossClient;

    @PostMapping("/upload")
    public ResponseResult<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            OssPolicyResult policy = ossController.policy().getData();
//            log.info("policy: " + policy.toString());
//            Map<String, String> formFields = new LinkedHashMap<String, String>();
//            formFields.put("OSSAccessKeyId", policy.getAccessKeyId());
//            formFields.put("policy", policy.getPolicy());
//            formFields.put("signature", policy.getSignature());
//            formFields.put("key", policy.getDir() + "${filename}");
//            formFields.put("success_action_status", "200");
//            formFields.put("file", file.getBytes());
//            ossService.upload(params);

            PutObjectRequest putObjectRequest =
                    new PutObjectRequest("ccorr-bucket",
                            policy.getDir() + "/" + file.getOriginalFilename(),
                            new ByteArrayInputStream(file.getBytes()));

            ossClient.putObject(putObjectRequest);
//            log.warn(String.valueOf(result.getResponse().getStatusCode()));
            String url = "https://ccorr-bucket.oss-cn-shenzhen.aliyuncs.com/" +
                    policy.getDir() + "/" + file.getOriginalFilename();
            return ResponseResult.success(url);
        } catch (Exception e) {
            return ResponseResult.fail("", e.getMessage());
        }

    }

    @GetMapping("/count")
    public ResponseResult<Long> getCount() {
        return ResponseResult.success(service.count());
    }

    @GetMapping("/all")
    public ResponseResult<List<CorrectionPeople>> getAll() {
        return ResponseResult.success(service.list());
    }

    @GetMapping("/nocheck/all")
    public ResponseResult<List<CrpCheck>> getNoCheckAll() {
        try {
            List<CrpCheck> list = checkService.query()
                    .eq("status", "0")
                    .list().stream()
                    .peek(e -> e.setXm(
                            getName(e.getDxbh())))
                    .toList();
            return ResponseResult.success(list);
        } catch (Exception e) {
            return ResponseResult.fail(null, e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseResult<CorrectionPeople> getCrp(@PathVariable(
            "id") String id) {
        CorrectionPeople temp = service.query().eq("dxbh", id)
                .one();
        if (temp == null) {
            return ResponseResult.fail(null,
                    "没有找到对象编号为" + id + "的矫正对象");
        } else {
            return ResponseResult.success(temp);
        }
    }

    void initNoExit(CorrectionPeople crp) {
        BBInfo info = new BBInfo();
        info.setDxbh(crp.getDxbh());
        info.setStep(0);
        exitService.saveBBInfo(info);


        Exit exit = new Exit();
        exit.setDxbh(crp.getDxbh());
        exit.setXm(crp.getXm());
        exit.setBb("0");
        exit.setZj("06");
        exit.setBk("0");
        exitService.saveExitInfo(exit);

        ZJInfo zjInfo = new ZJInfo();
        zjInfo.setDxbh(crp.getDxbh());
        zjInfo.setStep(0);
        zjInfo.setZj("06");
        exitService.saveZJInfo(zjInfo);
    }

    void initCategory(CorrectionPeople crp) {
        CrpCategory cate = new CrpCategory(crp.getDxbh(),
                crp.getXm(), "01");
        cateService.saveCategory(cate);
    }

    void initAssessmentScore(CorrectionPeople crp) {
        ScoreInfo scoreInfo = new ScoreInfo(crp.getDxbh(),
                crp.getXm(), 10);
        assessmentService.saveScore(scoreInfo);
    }

    @PostMapping("/register")
    public ResponseResult<Boolean> register(@RequestBody CorrectionPeople crp) {
        try {
            crp.setStatus("待入矫");
            crp.setSfdcpg("0");
            service.save(crp);
            // 还需要在 不准出境 模块生成出入境信息
            initNoExit(crp);
            // 在分类管理模块下生成管理信息
            initCategory(crp);
            // 在奖励惩罚模块 记录 分数信息
            initAssessmentScore(crp);
            // 创建未报到信息
            CrpCheck check = new CrpCheck(crp.getDxbh(),
                    crp.getXm(), formatter.format(new Date()), "0");
            checkService.save(check);

            return ResponseResult.success(true);
        } catch (Exception e) {
            return ResponseResult.fail(false, e.getMessage());
        }
    }

    @PostMapping("/nocheck/update")
    public ResponseResult<Boolean> firstCheck(@RequestBody String dxbh) {
        try {
            checkService.update().eq("dxbh", dxbh)
                    .set("status", "1")
                    .update();
            return ResponseResult.success(true);
        } catch (Exception e) {
            return ResponseResult.fail(false, "首次报到失败！");
        }
    }

    @PostMapping("/update")
    public ResponseResult<Boolean> update(@RequestBody CorrectionPeople crp) {
        try {
            service.update().eq("dxbh", crp.getDxbh())
                    .update(crp);
            return ResponseResult.success(true);
        } catch (Exception e) {
            return ResponseResult.fail(false, "更新失败！");
        }
    }

    @PostMapping("/recv")
    public ResponseResult<Boolean> recv(@RequestBody CorrectionPeople crp) {
        try {
            service.update().eq("dxbh", crp.getDxbh())
                    .set("status", "在矫")
                    .update();

            return ResponseResult.success(true);
        } catch (Exception e) {
            return ResponseResult.fail(false, "更新失败！");
        }
    }
}
