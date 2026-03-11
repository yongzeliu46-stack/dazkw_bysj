package cn.dqzkw.dazkw_bysj.controller;

import cn.dqzkw.dazkw_bysj.common.Result;
import cn.dqzkw.dazkw_bysj.entity.StudyMaterial;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import cn.dqzkw.dazkw_bysj.service.StudyMaterialService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "瀛︿範璧勬枡绠＄悊")
@RestController
@RequestMapping("/study-material")
public class StudyMaterialController {

    @Autowired
    private StudyMaterialService studyMaterialService;

    @Operation(summary = "鑾峰彇鍒楄〃")
    @GetMapping("/list")
    public Result<List<StudyMaterial>> list(
            @RequestParam(required = false) Integer subjectId,
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) Integer type,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String orderBy
    ) {
        com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper<StudyMaterial> wrapper = new com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper<>();
        if (subjectId != null) wrapper.eq(StudyMaterial::getSubjectId, subjectId);
        if (year != null) wrapper.eq(StudyMaterial::getYear, year);
        if (type != null) wrapper.eq(StudyMaterial::getType, type);
        if (keyword != null && !keyword.isEmpty()) wrapper.like(StudyMaterial::getTitle, keyword);
        
        if ("downloadCount".equals(orderBy)) {
            wrapper.orderByDesc(StudyMaterial::getDownloadCount);
        } else {
            wrapper.orderByDesc(StudyMaterial::getCreatedAt);
        }
        return Result.success(studyMaterialService.list(wrapper));
    }

    @Operation(summary = "鏂板")
    @PostMapping("/add")
    public Result<Void> add(@RequestBody StudyMaterial studyMaterial) {
        studyMaterialService.save(studyMaterial);
        return Result.success(null);
    }

    @Operation(summary = "淇敼")
    @PutMapping("/update")
    public Result<Void> update(@RequestBody StudyMaterial studyMaterial) {
        studyMaterialService.updateById(studyMaterial);
        return Result.success(null);
    }

    @Operation(summary = "鍒犻櫎")
    @DeleteMapping("/delete/{id}")
    public Result<Void> delete(@PathVariable Integer id) {
        studyMaterialService.removeById(id);
        return Result.success(null);
    }

    @Operation(summary = "Download material and count")
    @GetMapping("/download/{id}")
    public Result<String> download(@PathVariable Integer id) {
        StudyMaterial material = studyMaterialService.getById(id);
        if (material == null) {
            return Result.error("Material not found");
        }

        LambdaUpdateWrapper<StudyMaterial> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(StudyMaterial::getId, id)
                .setSql("download_count = IFNULL(download_count, 0) + 1");
        studyMaterialService.update(updateWrapper);

        return Result.success(material.getFileUrl());
    }
}
