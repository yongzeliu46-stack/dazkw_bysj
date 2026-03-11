package cn.dqzkw.dazkw_bysj.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Tag(name = "页面跳转", description = "处理页面导航的控制器")
@Controller
public class IndexController {

    @Operation(summary = "首页", description = "跳转到系统首页")
    @GetMapping({"/", "/index"})
    public String index() {
        return "首页";
    }

    @Operation(summary = "名师点睛页", description = "跳转到名师点睛页面")
    @GetMapping("/expert")
    public String expert() {
        return "名师点睛";
    }

    @Operation(summary = "资料中心页", description = "跳转到资料中心页面")
    @GetMapping("/resource")
    public String resource() {
        return "资料中心";
    }
}
