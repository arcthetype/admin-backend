package com.zavier.project.dal.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.zavier.project.dal.entity.RoleMenuDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 角色-菜单表 Mapper 接口
 * </p>
 *
 * @author zhengwei
 * @since 2019-11-19
 */
@Repository
public interface RoleMenuMapper extends BaseMapper<RoleMenuDO> {

    default Wrapper<RoleMenuDO> findByRoleId(Integer roleId) {
        return Wrappers.<RoleMenuDO>lambdaQuery().eq(RoleMenuDO::getRoleId, roleId);
    }
}
