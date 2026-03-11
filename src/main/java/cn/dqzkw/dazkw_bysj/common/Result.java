package cn.dqzkw.dazkw_bysj.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "统一返回结果")
public class Result<T> {
    @Schema(description = "状态码: 200成功, 500失败")
    private Integer code; // 状态码: 200成功, 500失败
    
    @Schema(description = "提示信息")
    private String msg;   // 提示信息
    
    @Schema(description = "返回的数据")
    private T data;       // 返回的数据

    // 成功时候调用的方法
    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>();
        result.setCode(200);
        result.setMsg("操作成功");
        result.setData(data);
        return result;
    }

    // 失败时候调用的方法
    public static <T> Result<T> error(String msg) {
        Result<T> result = new Result<>();
        result.setCode(500);
        result.setMsg(msg);
        return result;
    }
}