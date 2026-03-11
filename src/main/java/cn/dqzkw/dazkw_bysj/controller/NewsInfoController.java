package cn.dqzkw.dazkw_bysj.controller;

import cn.dqzkw.dazkw_bysj.common.Result;
import cn.dqzkw.dazkw_bysj.entity.NewsInfo;
import cn.dqzkw.dazkw_bysj.service.NewsInfoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "新闻资讯管理")
@RestController
@RequestMapping("/news-info")
public class NewsInfoController {

    @Autowired
    private NewsInfoService newsInfoService;

    @Operation(summary = "获取列表")
    @GetMapping("/list")
    public Result<List<NewsInfo>> list(
            @RequestParam(required = false) Integer isImageNews,
            @RequestParam(required = false) String tag,
            @RequestParam(required = false) String keyword
    ) {
        com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper<NewsInfo> wrapper = new com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper<>();
        if (isImageNews != null) {
            wrapper.eq(NewsInfo::getIsImageNews, isImageNews);
        }
        if (tag != null && !tag.isEmpty()) {
            wrapper.like(NewsInfo::getTags, tag);
        }
        if (keyword != null && !keyword.isEmpty()) {
            wrapper.and(w -> w.like(NewsInfo::getTitle, keyword).or().like(NewsInfo::getContent, keyword));
        }
        wrapper.orderByDesc(NewsInfo::getPublishTime);
        return Result.success(newsInfoService.list(wrapper));
    }

    @Operation(summary = "获取资讯详情")
    @GetMapping("/{id}")
    public Result<NewsInfo> getById(@PathVariable Integer id) {
        NewsInfo newsInfo = newsInfoService.getById(id);
        if (newsInfo == null) {
            return Result.error("News not found");
        }
        return Result.success(newsInfo);
    }

    @Operation(summary = "新增")
    @PostMapping("/add")
    public Result<Void> add(@RequestBody NewsInfo newsInfo) {
        newsInfoService.save(newsInfo);
        return Result.success(null);
    }

    @Operation(summary = "修改")
    @PutMapping("/update")
    public Result<Void> update(@RequestBody NewsInfo newsInfo) {
        newsInfoService.updateById(newsInfo);
        return Result.success(null);
    }

    @Operation(summary = "删除")
    @DeleteMapping("/delete/{id}")
    public Result<Void> delete(@PathVariable Integer id) {
        newsInfoService.removeById(id);
        return Result.success(null);
    }
}
