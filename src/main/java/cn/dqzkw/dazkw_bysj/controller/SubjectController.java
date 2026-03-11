package cn.dqzkw.dazkw_bysj.controller;


import cn.dqzkw.dazkw_bysj.common.Result;
import cn.dqzkw.dazkw_bysj.entity.Subject;
import cn.dqzkw.dazkw_bysj.service.SubjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "学科管理", description = "学科相关接口")
@RestController
@RequestMapping("/subject") // 定义父路径
public class SubjectController {

    @Autowired
    private SubjectService subjectService;

    @Operation(summary = "获取所有学科列表", description = "从数据库中查询并返回所有学科数据")
    @GetMapping("/list")
    public Result<List<Subject>> list() {
        return Result.success(subjectService.list());
    }

    @Operation(summary = "根据ID获取学科", description = "传入学科ID获取详情")
    @GetMapping("/{id}")
    public Result<Subject> getById(@PathVariable @Parameter(description = "学科ID") Integer id) {
        return Result.success(subjectService.getById(id));
    }

    @Operation(summary = "新增学科", description = "保存一个新的学科信息")
    @PostMapping("/add")
    public Result<Void> add(@RequestBody Subject subject) {
        subjectService.save(subject);
        return Result.success(null);
    }

    @Operation(summary = "修改学科", description = "根据ID修改学科信息")
    @PutMapping("/update")
    public Result<Void> update(@RequestBody Subject subject) {
        subjectService.updateById(subject);
        return Result.success(null);
    }

    @Operation(summary = "删除学科", description = "根据ID逻辑删除学科")
    @DeleteMapping("/delete/{id}")
    public Result<Void> delete(@PathVariable @Parameter(description = "学科ID") Integer id) {
        subjectService.removeById(id);
        return Result.success(null);
    }
}