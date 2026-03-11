package cn.dqzkw.dazkw_bysj.service;

import cn.dqzkw.dazkw_bysj.common.Result;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    Result<String> upload(MultipartFile file);
}
