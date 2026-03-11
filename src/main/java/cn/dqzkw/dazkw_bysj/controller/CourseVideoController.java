package cn.dqzkw.dazkw_bysj.controller;

import cn.dqzkw.dazkw_bysj.common.Result;
import cn.dqzkw.dazkw_bysj.entity.CourseVideo;
import cn.dqzkw.dazkw_bysj.service.CourseVideoService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Course Video Management")
@RestController
@RequestMapping("/course-video")
public class CourseVideoController {

    @Autowired
    private CourseVideoService courseVideoService;

    @Operation(summary = "Get video list")
    @GetMapping("/list")
    public Result<List<CourseVideo>> list(
            @RequestParam(required = false) Integer subjectId,
            @RequestParam(required = false) Integer teacherId,
            @RequestParam(required = false) Integer isRecommend,
            @RequestParam(required = false) String orderBy
    ) {
        LambdaQueryWrapper<CourseVideo> wrapper = new LambdaQueryWrapper<>();
        if (subjectId != null) wrapper.eq(CourseVideo::getSubjectId, subjectId);
        if (teacherId != null) wrapper.eq(CourseVideo::getTeacherId, teacherId);
        if (isRecommend != null) wrapper.eq(CourseVideo::getIsRecommend, isRecommend);

        if ("viewCount".equals(orderBy)) {
            wrapper.orderByDesc(CourseVideo::getViewCount);
        } else {
            wrapper.orderByDesc(CourseVideo::getPublishTime);
        }
        return Result.success(courseVideoService.list(wrapper));
    }

    @Operation(summary = "Add video")
    @PostMapping("/add")
    public Result<Void> add(@RequestBody CourseVideo courseVideo) {
        courseVideoService.save(courseVideo);
        return Result.success(null);
    }

    @Operation(summary = "Update video")
    @PutMapping("/update")
    public Result<Void> update(@RequestBody CourseVideo courseVideo) {
        courseVideoService.updateById(courseVideo);
        return Result.success(null);
    }

    @Operation(summary = "Delete video")
    @DeleteMapping("/delete/{id}")
    public Result<Void> delete(@PathVariable Integer id) {
        courseVideoService.removeById(id);
        return Result.success(null);
    }

    @Operation(summary = "Play video and count")
    @GetMapping("/play/{id}")
    public Result<String> play(@PathVariable Integer id) {
        CourseVideo video = courseVideoService.getById(id);
        if (video == null) {
            return Result.error("Video not found");
        }

        LambdaUpdateWrapper<CourseVideo> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(CourseVideo::getId, id)
                .setSql("view_count = IFNULL(view_count, 0) + 1");
        courseVideoService.update(updateWrapper);

        return Result.success(video.getVideoUrl());
    }
}
