package com.zavier.project.manager.bo;

import lombok.Data;

import java.util.Date;

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
}
