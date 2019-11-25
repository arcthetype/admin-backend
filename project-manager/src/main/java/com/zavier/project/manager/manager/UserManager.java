package com.zavier.project.manager.manager;

import com.zavier.project.common.util.BeanUtil;
import com.zavier.project.common.util.CollectionUtil;
import com.zavier.project.dal.entity.*;
import com.zavier.project.dal.mapper.*;
import com.zavier.project.manager.bo.MenuBO;
import com.zavier.project.manager.bo.RoleBO;
import com.zavier.project.manager.bo.UserBO;
import com.zavier.project.manager.mapper.UserBOMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class UserManager {
    private final UserMapper userMapper;
    private final UserRoleMapper userRoleMapper;
    private final RoleMapper roleMapper;
    private final RoleMenuMapper roleMenuMapper;
    private final MenuMapper menuMapper;

    public UserManager(UserMapper userMapper, UserRoleMapper userRoleMapper, RoleMapper roleMapper, RoleMenuMapper roleMenuMapper, MenuMapper menuMapper) {
        this.userMapper = userMapper;
        this.userRoleMapper = userRoleMapper;
        this.roleMapper = roleMapper;
        this.roleMenuMapper = roleMenuMapper;
        this.menuMapper = menuMapper;
    }

    @Cacheable(value = "user", key = "#root.methodName")
    public List<UserBO> listAllUser() {
        List<UserDO> users = userMapper.selectList(null);
        return UserBOMapper.INSTANCE.userToUserBOList(users);
    }

    @Cacheable(value = "user", key = "#root.methodName.concat(#userName)")
    public UserBO getUserByUserName(String userName) {
        List<UserDO> userDOS = userMapper.selectList(userMapper.findByUserNameWrapper(userName));
        if (CollectionUtil.isEmpty(userDOS)) {
            return null;
        }
        return UserBOMapper.INSTANCE.userToUserBO(userDOS.get(0));
    }

    @Cacheable(value = "user", key = "#root.methodName.concat(#userId)")
    public List<RoleBO> listUserRoles(Integer userId) {
        List<UserRoleDO> userRoleDOS = userRoleMapper.selectList(userRoleMapper.findByUserId(userId));
        if (CollectionUtil.isEmpty(userRoleDOS)) {
            return new ArrayList<>();
        }
        List<RoleDO> roleDOS = roleMapper.selectBatchIds(userRoleDOS.stream().map(UserRoleDO::getRoleId).collect(Collectors.toList()));
        return BeanUtil.copyListProperties(roleDOS, RoleBO.class);
    }

    @Cacheable(value = "user", key = "#root.methodName.concat(#roleId)")
    public List<MenuBO> listRoleMenus(Integer roleId) {
        List<RoleMenuDO> roleMenuDOS = roleMenuMapper.selectList(roleMenuMapper.findByRoleId(roleId));
        if (CollectionUtil.isEmpty(roleMenuDOS)) {
            return new ArrayList<>();
        }
        List<MenuDO> menuDOS = menuMapper.selectBatchIds(roleMenuDOS.stream().map(RoleMenuDO::getMenuId).collect(Collectors.toList()));
        return BeanUtil.copyListProperties(menuDOS, MenuBO.class);
    }
}
