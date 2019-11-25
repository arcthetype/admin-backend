package com.zavier.project.manager.bo;

import lombok.Data;

import java.util.List;

@Data
public class MenuBO {

    private Integer id;

    /**
     * 父菜单ID
     */
    private Integer parentId;

    /**
     * 菜单名称
     */
    private String menuName;

    /**
     * 菜单url
     */
    private String menuUrl;

    /**
     * icon url
     */
    private String iconUrl;

    /**
     * 子菜单
     */
    List<MenuBO> childMenus;
}
