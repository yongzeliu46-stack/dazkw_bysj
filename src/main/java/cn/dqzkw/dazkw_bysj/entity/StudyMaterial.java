package cn.dqzkw.dazkw_bysj.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("study_material")
@Schema(description = "学习资料 (试卷/笔记)")
public class StudyMaterial {
    @Schema(description = "资料ID")
    @TableId(type = IdType.AUTO)
    private Integer id;

    @Schema(description = "资料标题")
    private String title;

    @Schema(description = "资料下载地址")
    private String fileUrl; 

    @Schema(description = "文件大小 (如 2.5MB)")
    private String fileSize; 

    @Schema(description = "所属学科ID")
    private Integer subjectId;

    @Schema(description = "资料年份 (如 2026)")
    private Integer year; 

    @Schema(description = "资料类型: 1=真题, 2=模拟, 3=笔记")
    private Integer type;

    @Schema(description = "下载次数")
    private Integer downloadCount;

    @Schema(description = "上传时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;

    @TableLogic
    private Integer isDeleted;
}