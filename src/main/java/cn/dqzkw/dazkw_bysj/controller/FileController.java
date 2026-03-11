package cn.dqzkw.dazkw_bysj.controller;

import cn.dqzkw.dazkw_bysj.common.Result;
import cn.dqzkw.dazkw_bysj.service.FileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "文件上传管理")
@RestController
@RequestMapping("/file")
public class FileController {

    @Autowired
    private FileService fileService;

    @Operation(summary = "通用文件上传")
    @PostMapping("/upload")
    public Result<String> upload(@RequestParam("file") MultipartFile file) {
        return fileService.upload(file);
    }
}
