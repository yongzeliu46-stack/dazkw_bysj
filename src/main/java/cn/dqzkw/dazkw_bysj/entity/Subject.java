package cn.dqzkw.dazkw_bysj.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("subject") // 对应数据库表名
@Schema(description = "学科实体信息")
public class Subject {
    @TableId(type = IdType.AUTO) // 主键自增
    @Schema(description = "学科ID")
    private Integer id;
    
    @Schema(description = "学科名称")
    private String name;
    
    @Schema(description = "排序值")
    private Integer sortOrder;
    
    // 下面这两个字段由数据库自动处理，Java里有时候可以不传，查询时会用到
    @Schema(description = "创建时间")
    private LocalDateTime createdAt;
    
    @Schema(description = "更新时间")
    private LocalDateTime updatedAt;
    
    @Schema(description = "是否删除(0:未删, 1:已删)")
    private Integer isDeleted;
}