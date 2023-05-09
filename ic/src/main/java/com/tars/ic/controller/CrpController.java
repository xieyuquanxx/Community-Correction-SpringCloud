package com.tars.ic.controller;

import com.tars.ic.api.ResponseResult;
import com.tars.ic.entity.CorrectionPeople;
import com.tars.ic.entity.CrpCheck;
import com.tars.ic.entity.data.NumberData;
import com.tars.ic.entity.others.*;
import com.tars.ic.remote.*;
import com.tars.ic.service.CrpCheckService;
import com.tars.ic.service.CrpService;
import com.tars.ic.enums.XB;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    private RemoteOssService ossService;
    @Autowired
    private RemoteWordService wordService;
    @Autowired
    private WorkerController workerController;

    private static final SimpleDateFormat formatter =
            new SimpleDateFormat(
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


    @PostMapping("/upload")
    public ResponseResult<String> uploadFile(@RequestParam("file") MultipartFile file
    ) {
        try {
            return ossService.upload(file);
        } catch (Exception e) {
            return ResponseResult.fail("", e.getMessage());
        }
    }

    @PostMapping("/export")
    public ResponseResult<String> export(
            @RequestBody CorrectionPeople crp) {
        try {
            Map<String, Object> dataMap = new HashMap<>();
            dataMap.put("xmlName", "crpInfo.xml");
            dataMap.put("xm", crp.getXm());
            dataMap.put("sfzhm", crp.getSfzhm());
            dataMap.put("xb", XB.map(crp.getXb()));
            dataMap.put("mz", crp.getMz());
            dataMap.put("csrq", crp.getCsrq());
            dataMap.put("whcd", crp.getWhcd());
            dataMap.put("yzzmm", crp.getXzzmm());
            dataMap.put("hyzk", crp.getHyzk());
            dataMap.put("grlxdh", crp.getGrlxdh());
            dataMap.put("jzlb", crp.getJzlb());
            // 根据宣告信息生成word，上传到oss上后将url返回
            return wordService.exportInfo(dataMap);
        } catch (Exception e) {
            return ResponseResult.fail("", e.getMessage());
        }
    }

    @GetMapping("/count")
    public ResponseResult<Long> getCount() {
        try {
            return ResponseResult.success(service.count());
        } catch (Exception e) {
            return ResponseResult.fail(-1L, e.getMessage());
        }
    }

    @GetMapping("/counts")
    public ResponseResult<NumberData> getStatic() {
        NumberData numberData = NumberData.builder()
                .total(service.count())
                .received(service.query().eq("status", JZStatus.IN.code).count())
                .build();
        numberData.setUnreceived(numberData.getTotal() - numberData.getReceived());
        return ResponseResult.success(numberData);
    }

    @GetMapping("/all")
    public ResponseResult<List<CorrectionPeople>> getAll() {
        try {
            return ResponseResult.success(service.list());
        } catch (Exception e) {
            return ResponseResult.fail(null, e.getMessage());
        }
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

    @GetMapping("/{dxbh}")
    public ResponseResult<CorrectionPeople> getCrp(
            @PathVariable("dxbh") String dxbh
    ) {
        CorrectionPeople temp = service.query().eq("dxbh", dxbh)
                .one();
        if (temp == null) {
            return ResponseResult.fail(null,
                    "没有找到对象编号为" + dxbh + "的矫正对象");
        } else {
            return ResponseResult.success(temp);
        }
    }

    /**
     * 为入矫人员初始化出境信息
     */
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

    /**
     * 为入矫人员初始化管理类别
     */
    void initCategory(CorrectionPeople crp) {
        CrpCategory cate = new CrpCategory(crp.getDxbh(),
                crp.getXm(), "01");
        cateService.saveCategory(cate);
    }

    /**
     * 为入矫人员初始化考核奖惩信息
     */
    void initAssessmentScore(CorrectionPeople crp) {
        ScoreInfo scoreInfo = new ScoreInfo(crp.getDxbh(),
                crp.getXm(), 10);
        assessmentService.saveScore(scoreInfo);
    }

    @PostMapping("/register")
    public ResponseResult<Boolean> register(@RequestBody CorrectionPeople crp) {
        try {
            crp.setStatus(JZStatus.NOT.code)
                    .setSfdcpg("0")
                    .setGmt_create(formatter.format(new Date()))
                    .setGmt_modified(formatter.format(new Date()));
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
            crp.setGmt_modified(formatter.format(new Date()));
            if (crp.getTeam().length() > 0) {
                workerController.updateWorker(crp.getDxbh(), crp.getTeam());
            }
            service.update().eq("dxbh", crp.getDxbh())
                    .update(crp);
            return ResponseResult.success(true);
        } catch (Exception e) {
            return ResponseResult.fail(false, "更新失败！");
        }
    }

    // 调查评估完成
    @PostMapping("/dcpg/{dxbh}")
    public ResponseResult<Boolean> finishDcpg(@PathVariable("dxbh") String dxbh) {
        try {
            service.update().eq("dxbh", dxbh)
                    .set("sfdcpg", "1")
                    .set("gmt_modified", formatter.format(new Date()))
                    .update();
            return ResponseResult.success(true);
        } catch (Exception e) {
            return ResponseResult.fail(false, "更新失败！");
        }
    }

    @PostMapping("/recv")
    public ResponseResult<Boolean> recv(@RequestBody CorrectionPeople crp) {
        try {
            service.update().eq("dxbh", crp.getDxbh())
                    .set("status", JZStatus.IN.code)
                    .set("gmt_modified", formatter.format(new Date()))
                    .update();

            return ResponseResult.success(true);
        } catch (Exception e) {
            return ResponseResult.fail(false, "更新失败！");
        }
    }

    @Getter
    enum JZStatus {
        NOT("0", "待入矫"), IN("1", "在矫");


        private final String code;
        private final String description;

        JZStatus(String code, String description) {
            this.code = code;
            this.description = description;
        }
    }
}
