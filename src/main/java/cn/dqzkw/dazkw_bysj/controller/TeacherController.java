package cn.dqzkw.dazkw_bysj.controller;

import cn.dqzkw.dazkw_bysj.common.Result;
import cn.dqzkw.dazkw_bysj.entity.Teacher;
import cn.dqzkw.dazkw_bysj.service.TeacherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "教师管理", description = "名师相关接口")
@RestController
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @Operation(summary = "获取所有教师列表")
    @GetMapping("/list")
    public Result<List<Teacher>> list(
            @RequestParam(required = false) Integer subjectId,
            @RequestParam(required = false, defaultValue = "id") String orderBy
    ) {
        com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper<Teacher> wrapper = new com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper<>();
        if (subjectId != null) wrapper.eq(Teacher::getSubjectId, subjectId);
        if ("popularity".equals(orderBy)) {
            wrapper.orderByDesc(Teacher::getPopularity);
        } else {
            wrapper.orderByDesc(Teacher::getCreatedAt);
        }
        return Result.success(teacherService.list(wrapper));
    }

    @Operation(summary = "新增教师")
    @PostMapping("/add")
    public Result<Void> add(@RequestBody Teacher teacher) {
        teacherService.save(teacher);
        return Result.success(null);
    }

    @Operation(summary = "修改教师")
    @PutMapping("/update")
    public Result<Void> update(@RequestBody Teacher teacher) {
        teacherService.updateById(teacher);
        return Result.success(null);
    }

    @Operation(summary = "删除教师")
    @DeleteMapping("/delete/{id}")
    public Result<Void> delete(@PathVariable Integer id) {
        teacherService.removeById(id);
        return Result.success(null);
    }
}
