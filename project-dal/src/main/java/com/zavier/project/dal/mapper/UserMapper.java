package com.zavier.project.dal.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.zavier.project.dal.entity.UserDO;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author zhengwei
 * @since 2019-11-19
 */
@Repository
public interface UserMapper extends BaseMapper<UserDO> {

    default Wrapper<UserDO> findByUserNameWrapper(String userName) {
        return Wrappers.<UserDO>lambdaQuery().eq(UserDO::getUserName, userName);
    }
}
