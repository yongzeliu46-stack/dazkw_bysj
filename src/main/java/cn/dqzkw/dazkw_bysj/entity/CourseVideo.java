package cn.dqzkw.dazkw_bysj.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("course_video")
@Schema(description = "视频课程")
public class CourseVideo {
    @Schema(description = "视频ID")
    @TableId(type = IdType.AUTO)
    private Integer id;

    @Schema(description = "课程标题")
    private String title;

    @Schema(description = "视频封面图URL")
    private String coverUrl; 

    @Schema(description = "视频文件播放地址")
    private String videoUrl; 

    @Schema(description = "视频时长 (格式 15:30)")
    private String duration; 

    @Schema(description = "关联教师ID")
    private Integer teacherId;

    @Schema(description = "关联学科ID")
    private Integer subjectId;

    @Schema(description = "播放次数")
    private Integer viewCount;

    @Schema(description = "是否推荐 (1=推荐, 0=普通)")
    private Integer isRecommend; 

    @Schema(description = "发布时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime publishTime; 

    @Schema(description = "入库时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;

    @TableLogic
    private Integer isDeleted;
}