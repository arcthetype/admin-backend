package com.zavier.project.service.mapper;

import com.zavier.project.dal.entity.User;
import com.zavier.project.service.dto.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface UserDTOMapper {
    UserDTOMapper INSTANCE = Mappers.getMapper(UserDTOMapper.class);

    UserDTO areaToAreaDto(User user);

    List<UserDTO> areaToAreaDtoList(List<User> users);
}
