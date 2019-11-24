package com.zavier.project.dal.dao.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zavier.project.dal.dao.LogInLogDao;
import com.zavier.project.dal.entity.LogInLogDO;
import com.zavier.project.dal.mapper.LogInLogMapper;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户登录表 服务实现类
 * </p>
 *
 * @author zhengwei
 * @since 2019-11-24
 */
@Service
public class LogInLogDaoImpl extends ServiceImpl<LogInLogMapper, LogInLogDO> implements LogInLogDao {

}
