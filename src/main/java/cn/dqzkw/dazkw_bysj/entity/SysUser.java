package cn.dqzkw.dazkw_bysj.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("sys_user")
@Schema(description = "系统用户 (管理员/学生)")
public class SysUser {
    @Schema(description = "用户ID")
    @TableId(type = IdType.AUTO)
    private Integer id;

    @Schema(description = "登录用户名")
    private String username;
    
    @Schema(description = "登录密码 (加密存储)")
    private String password; 
    
    @Schema(description = "用户昵称")
    private String nickname;
    
    @Schema(description = "角色: 1=学生, 2=管理员")
    private Integer role;
    
    @Schema(description = "用户头像URL")
    private String avatarUrl;

    @Schema(description = "注册时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;

    @Schema(description = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;

    @Schema(description = "是否删除")
    @TableLogic
    private Integer isDeleted;
}