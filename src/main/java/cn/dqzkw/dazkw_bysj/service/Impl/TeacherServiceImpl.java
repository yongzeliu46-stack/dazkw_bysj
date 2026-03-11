package cn.dqzkw.dazkw_bysj.service.Impl;

import cn.dqzkw.dazkw_bysj.entity.Teacher;
import cn.dqzkw.dazkw_bysj.mapper.TeacherMapper;
import cn.dqzkw.dazkw_bysj.service.TeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements TeacherService {
}
