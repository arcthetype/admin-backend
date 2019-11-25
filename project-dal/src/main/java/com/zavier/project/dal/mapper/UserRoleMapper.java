package com.zavier.project.dal.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.zavier.project.dal.entity.UserRoleDO;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 用户-角色表 Mapper 接口
 * </p>
 *
 * @author zhengwei
 * @since 2019-11-19
 */
@Repository
public interface UserRoleMapper extends BaseMapper<UserRoleDO> {

    default Wrapper<UserRoleDO> findByUserId(Integer userId) {
        return Wrappers.<UserRoleDO>lambdaQuery().eq(UserRoleDO::getUserId, userId);
    }
}
