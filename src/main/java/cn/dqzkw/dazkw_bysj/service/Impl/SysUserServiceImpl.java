package cn.dqzkw.dazkw_bysj.service.Impl;

import cn.dqzkw.dazkw_bysj.entity.SysUser;
import cn.dqzkw.dazkw_bysj.mapper.SysUserMapper;
import cn.dqzkw.dazkw_bysj.service.SysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {
}
