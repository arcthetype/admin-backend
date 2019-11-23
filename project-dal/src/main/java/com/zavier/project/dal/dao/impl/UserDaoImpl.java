package com.zavier.project.dal.dao.impl;

import com.zavier.project.dal.entity.UserDO;
import com.zavier.project.dal.mapper.UserMapper;
import com.zavier.project.dal.dao.UserDao;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author zhengwei
 * @since 2019-11-19
 */
@Service
public class UserDaoImpl extends ServiceImpl<UserMapper, UserDO> implements UserDao {

}
