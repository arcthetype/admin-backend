package com.zavier.project.service.user;

import com.zavier.project.common.constant.AdminConstants;
import com.zavier.project.common.exp.CommonException;
import com.zavier.project.common.exp.ExceptionEnum;
import com.zavier.project.common.util.CollectionUtil;
import com.zavier.project.manager.bo.MenuBO;
import com.zavier.project.manager.bo.RoleBO;
import com.zavier.project.manager.bo.UserBO;
import com.zavier.project.manager.manager.PasswordManager;
import com.zavier.project.manager.manager.UserManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserService {

    private final UserManager userManager;

    private final PasswordManager passwordManager;

    public UserService(UserManager userManager, PasswordManager passwordManager) {
        this.userManager = userManager;
        this.passwordManager = passwordManager;
    }

    /**
     * 校验用户密码是否正确
     * @param userBO
     * @return
     */
    public boolean checkUserPassword(UserBO userBO) {
        String userName = userBO.getUserName();
        String encryptPassword = passwordManager.encryptPassword(userBO.getPassword());
        UserBO user = userManager.getUserByUserName(userName);
        if (user == null) {
            log.error("userName:{} does not exist", userBO.getUserName());
            throw new CommonException(ExceptionEnum.USER_NAME_NOT_EXIST);
        }
        log.info("encryptPassword:{}", encryptPassword);
        return Objects.equals(user.getPassword(), encryptPassword);
    }

    /**
     * 获取用户及其角色
     * @param userName
     * @return
     */
    public UserBO listUserAndRoles(String userName) {
        UserBO userBO = userManager.getUserByUserName(userName);
        List<RoleBO> roles = userManager.listUserRoles(userBO.getId());
        userBO.setRoleBOList(roles);
        return userBO;
    }

    /**
     * 获取角色及其菜单
     * @param userName
     * @return
     */
    public List<MenuBO> listRoleMenus(String userName) {
        UserBO userBO = userManager.getUserByUserName(userName);
        List<RoleBO> roles = userManager.listUserRoles(userBO.getId());
        if (CollectionUtil.isEmpty(roles)) {
            return new ArrayList<>();
        }
        List<MenuBO> menuBOList = new ArrayList<>();
        roles.forEach(r -> menuBOList.addAll(userManager.listRoleMenus(r.getId())));
        return menuBOList;
    }

    /**
     * 获取一集菜单及其子菜单
     */
    public List<MenuBO> listFirstLevelWithChildrenMenus(String userName) {
        List<MenuBO> menuBOS = listRoleMenus(userName);
        if (CollectionUtil.isEmpty(menuBOS)) {
            return new ArrayList<>();
        }
        Map<Integer, List<MenuBO>> parentMenuMap = menuBOS.stream().collect(Collectors.groupingBy(MenuBO::getParentId));
        List<MenuBO> firstLevelMenuList = parentMenuMap.get(AdminConstants.DEFAULT_PARENT_ID);
        firstLevelMenuList.forEach(m -> setChildren(m, parentMenuMap));
        return firstLevelMenuList;
    }

    private void setChildren(MenuBO menuBO, Map<Integer, List<MenuBO>> parentMenuMap) {
        List<MenuBO> menuBOS = parentMenuMap.get(menuBO.getId());
        if (CollectionUtil.isEmpty(menuBOS)) {
            return;
        }
        menuBO.setChildMenus(menuBOS);
        for (MenuBO menu : menuBOS) {
            setChildren(menu, parentMenuMap);
        }
    }
}
