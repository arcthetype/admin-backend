package com.zavier.project.manager.bo;

import com.zavier.project.common.util.CollectionUtil;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class UserBO {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 创建时间
     */
    private Date gmtCreate;

    /**
     * 修改时间
     */
    private Date gmtModified;

    /**
     * 创建人
     */
    private Integer creator;

    /**
     * 修改人
     */
    private Integer modifier;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 密码
     */
    private String password;

    /**
     * 用户登录IP
     */
    private String ip;

    /**
     * 角色列表
     */
    private List<RoleBO> roleBOList;

    /**
     * 菜单列表
     */
    private List<MenuBO> menuBOList;


    public String getRoles() {
        if (CollectionUtil.isEmpty(roleBOList)) {
            return "";
        }
        return roleBOList.stream().map(RoleBO::getRoleName).collect(Collectors.joining(","));
    }
}
