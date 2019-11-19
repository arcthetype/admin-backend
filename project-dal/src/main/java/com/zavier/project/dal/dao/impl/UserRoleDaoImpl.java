package com.zavier.project.dal.dao.impl;

import com.zavier.project.dal.entity.UserRole;
import com.zavier.project.dal.mapper.UserRoleMapper;
import com.zavier.project.dal.dao.UserRoleDao;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户-角色表 服务实现类
 * </p>
 *
 * @author zhengwei
 * @since 2019-11-19
 */
@Service
public class UserRoleDaoImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleDao {

}
