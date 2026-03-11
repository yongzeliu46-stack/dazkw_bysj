package cn.dqzkw.dazkw_bysj.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("teacher")
@Schema(description = "名师信息")
public class Teacher {
    @Schema(description = "教师ID")
    @TableId(type = IdType.AUTO)
    private Integer id;

    @Schema(description = "教师姓名")
    private String name;

    @Schema(description = "所属学校")
    private String school;

    @Schema(description = "头像图片地址")
    private String avatarUrl;

    @Schema(description = "主授学科ID")
    private Integer subjectId; 

    @Schema(description = "人气值/热度")
    private Integer popularity; 

    @Schema(description = "教师简介/教学风格")
    private String description;

    @Schema(description = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;

    @Schema(description = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;

    @TableLogic
    @Schema(description = "是否删除")
    private Integer isDeleted;
}