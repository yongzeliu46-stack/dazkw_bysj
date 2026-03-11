package cn.dqzkw.dazkw_bysj.controller;

import cn.dqzkw.dazkw_bysj.common.Result;
import cn.dqzkw.dazkw_bysj.entity.SysUser;
import cn.dqzkw.dazkw_bysj.service.SysUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "用户管理")
@RestController
@RequestMapping("/sys-user")
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;

    @Operation(summary = "获取列表")
    @GetMapping("/list")
    public Result<List<SysUser>> list() {
        return Result.success(sysUserService.list());
    }

    @Operation(summary = "新增")
    @PostMapping("/add")
    public Result<Void> add(@RequestBody SysUser user) {
        sysUserService.save(user);
        return Result.success(null);
    }

    @Operation(summary = "修改")
    @PutMapping("/update")
    public Result<Void> update(@RequestBody SysUser user) {
        sysUserService.updateById(user);
        return Result.success(null);
    }

    @Operation(summary = "删除")
    @DeleteMapping("/delete/{id}")
    public Result<Void> delete(@PathVariable Integer id) {
        sysUserService.removeById(id);
        return Result.success(null);
    }
}
