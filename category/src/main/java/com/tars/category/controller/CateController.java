package com.tars.category.controller;

import com.tars.category.api.ResponseResult;
import com.tars.category.entity.CategoryInfo;
import com.tars.category.service.CateService;
import com.tars.category.utils.CrpHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cate/info")
@CrossOrigin(origins = "*")
public class CateController {
    @Autowired
    private CateService service;

    @GetMapping("/all")
    public ResponseResult<List<CategoryInfo>> getAll() {
        List<CategoryInfo> list = service.list().stream()
                .peek(item -> item.setXm(
                        CrpHelper.getXm(
                                item.getDxbh())))
                .toList();
        return ResponseResult.success(list);
    }

    public String getGLLB(String dxbh) {
        return service.query().eq("dxbh", dxbh)
                .one().getGllb();
    }

    @GetMapping("/{id}")
    public ResponseResult<CategoryInfo> getCategoryBtId(@PathVariable String id) {
        CategoryInfo info = service.query().eq("dxbh", id).one();
        info.setXm(CrpHelper.getXm(id));
        return ResponseResult.success(info);
    }


    @PostMapping("/save")
    public ResponseResult<Boolean> saveCategory(@RequestBody CategoryInfo info) {
        try {
            System.out.println("save category");
            return ResponseResult.success(service.save(info));
        } catch (Exception e) {
            return ResponseResult.fail(false, "保存失败!");
        }
    }

    @PostMapping("/update")
    public ResponseResult<Boolean> updateCateInfo(@RequestBody CategoryInfo info) {
        try {
            return ResponseResult.success(
                    service.update().eq("dxbh", info.getDxbh())
                            .update(info));
        } catch (Exception e) {
            return ResponseResult.fail(false, "更新失败!");
        }
    }
}
