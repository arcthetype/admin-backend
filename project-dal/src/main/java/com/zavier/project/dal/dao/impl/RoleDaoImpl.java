package com.zavier.project.dal.dao.impl;

import com.zavier.project.dal.entity.RoleDO;
import com.zavier.project.dal.mapper.RoleMapper;
import com.zavier.project.dal.dao.RoleDao;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author zhengwei
 * @since 2019-11-19
 */
@Service
public class RoleDaoImpl extends ServiceImpl<RoleMapper, RoleDO> implements RoleDao {

}
