package cn.dqzkw.dazkw_bysj.service.Impl;

import cn.dqzkw.dazkw_bysj.common.Result;
import cn.dqzkw.dazkw_bysj.service.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    private static final String UPLOAD_DIR = System.getProperty("user.dir") + File.separator + "uploads" + File.separator;

    @Override
    public Result<String> upload(MultipartFile file) {
        if (file.isEmpty()) {
            return Result.error("上传文件不能为空");
        }

        // 确保上传目录存在
        File dir = new File(UPLOAD_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        // 生成新文件名，防止重名
        String originalFilename = file.getOriginalFilename();
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        String fileName = UUID.randomUUID().toString() + suffix;

        try {
            // 保存文件
            file.transferTo(new File(UPLOAD_DIR + fileName));
            // 返回文件的访问URL
            String fileUrl = "http://localhost:8080/uploads/" + fileName;
            return Result.success(fileUrl);
        } catch (IOException e) {
            e.printStackTrace();
            return Result.error("文件上传失败: " + e.getMessage());
        }
    }
}
