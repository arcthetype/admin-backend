package com.zavier.project.manager.mapper;

import com.zavier.project.dal.entity.UserDO;
import com.zavier.project.manager.bo.UserBO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface UserBOMapper {
    UserBOMapper INSTANCE = Mappers.getMapper(UserBOMapper.class);

    UserBO userToUserBO(UserDO user);

    List<UserBO> userToUserBOList(List<UserDO> users);
}
