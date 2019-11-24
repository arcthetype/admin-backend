package com.zavier.project.dal.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 用户登录表
 * </p>
 *
 * @author zhengwei
 * @since 2019-11-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_log_in_log")
public class LogInLogDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 是否删除:Y/N
     */
    @TableLogic
    private String isDeleted;

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
     * 登录用户名
     */
    private String logInUserName;

    /**
     * 登录时间
     */
    private Date logInTime;

    /**
     * 登录IP
     */
    private String logInIp;


}
