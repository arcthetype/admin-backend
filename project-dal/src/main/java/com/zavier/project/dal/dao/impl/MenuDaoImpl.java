package com.zavier.project.dal.dao.impl;

import com.zavier.project.dal.entity.MenuDO;
import com.zavier.project.dal.mapper.MenuMapper;
import com.zavier.project.dal.dao.MenuDao;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 菜单表 服务实现类
 * </p>
 *
 * @author zhengwei
 * @since 2019-11-19
 */
@Service
public class MenuDaoImpl extends ServiceImpl<MenuMapper, MenuDO> implements MenuDao {

}
