package com.hang.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hang.entity.Menu;

import java.util.List;


/**
 * 菜单权限表(Menu)表服务接口
 *
 * @author makejava
 * @since 2023-08-10 14:25:53
 */
public interface MenuService extends IService<Menu> {

    List<String> selectRermsByUserId(Long id);
}

