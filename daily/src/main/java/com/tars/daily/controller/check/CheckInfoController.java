package com.tars.daily.controller.check;

import com.tars.daily.api.ResponseResult;
import com.tars.daily.entity.check.CheckInfo;
import com.tars.daily.mapper.check.CheckInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/daily/check")
@CrossOrigin(origins = "*")
public class CheckInfoController {
    @Autowired
    private CheckInfoMapper mapper;

    @GetMapping("/all")
    public ResponseResult<List<CheckInfo>> getAll() {
        try {
            List<CheckInfo> allCheckInfo = mapper.getAllCheckInfo()
                                                 .stream()
                                                 .peek(e -> e.setCount(
                                                         mapper.getCheckCount(
                                                                 e.getDxbh())))
                                                 .toList();
            return ResponseResult.success(allCheckInfo);
        } catch (Exception e) {
            return ResponseResult.fail(null, e.getMessage());
        }

    }
}
