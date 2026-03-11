package cn.dqzkw.dazkw_bysj.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("news_info")
@Schema(description = "新闻资讯")
public class NewsInfo {
    @Schema(description = "新闻ID")
    @TableId(type = IdType.AUTO)
    private Integer id;

    @Schema(description = "新闻标题")
    private String title;

    @Schema(description = "新闻内容 (富文本HTML)")
    private String content; 

    @Schema(description = "封面图 (仅图片新闻需要)")
    private String coverUrl;

    @Schema(description = "来源 (如: 市教育局)")
    private String source; 

    @Schema(description = "标签 (如: 头条, HOT)")
    private String tags; 

    @Schema(description = "是否为轮播图新闻 (1=是, 0=否)")
    private Integer isImageNews; 

    @Schema(description = "展示发布时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime publishTime;

    @Schema(description = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;

    @TableLogic
    private Integer isDeleted;
}