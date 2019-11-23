package com.zavier.project.service.mapper;

import com.zavier.project.dal.entity.UserDO;
import com.zavier.project.service.dto.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface UserDTOMapper {
    UserDTOMapper INSTANCE = Mappers.getMapper(UserDTOMapper.class);

    UserDTO userToUserDTO(UserDO user);

    List<UserDTO> userToUserDTOList(List<UserDO> users);
}
