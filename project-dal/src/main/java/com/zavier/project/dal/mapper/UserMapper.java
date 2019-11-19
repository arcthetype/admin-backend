package com.zavier.project.dal.mapper;

import com.zavier.project.dal.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
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
public interface UserMapper extends BaseMapper<User> {

}
