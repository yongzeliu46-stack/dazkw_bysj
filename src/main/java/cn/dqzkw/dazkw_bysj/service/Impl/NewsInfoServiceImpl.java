package cn.dqzkw.dazkw_bysj.service.Impl;

import cn.dqzkw.dazkw_bysj.entity.NewsInfo;
import cn.dqzkw.dazkw_bysj.mapper.NewsInfoMapper;
import cn.dqzkw.dazkw_bysj.service.NewsInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class NewsInfoServiceImpl extends ServiceImpl<NewsInfoMapper, NewsInfo> implements NewsInfoService {
}
