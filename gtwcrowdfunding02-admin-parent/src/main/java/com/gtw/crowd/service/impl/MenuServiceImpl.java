package com.gtw.crowd.service.impl;

import com.gtw.crowd.entity.Menu;
import com.gtw.crowd.entity.MenuExample;
import com.gtw.crowd.mapper.MenuMapper;
import com.gtw.crowd.service.api.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author
 * @create 2020-10-30-21:27
 */
@Repository
public class MenuServiceImpl implements MenuService {
    @Autowired
    private MenuMapper menuMapper;
    @Override
    public Menu getWholeTree() {
        List<Menu> menus = menuMapper.selectByExample(new MenuExample());

        Menu root=null;

        Map<Integer,Menu> menuMap=new HashMap<>();

        for(Menu menu:menus){
            Integer id=menu.getId();
            menuMap.put(id,menu);
        }

        for(Menu menu:menus){
            Integer pid=menu.getPid();
            if(pid==null){
                root=menu;
                continue;
            }
            Menu father=menuMap.get(pid);
            father.getChildren().add(menu);
        }
        return root;
    }

    @Override
    public void addMenu(Menu menu) {
        menuMapper.insert(menu);
    }

    @Override
    public void editMenuById(Menu menu) {
        menuMapper.updateByPrimaryKeySelective(menu);
    }

    @Override
    public void removeMenuById(Integer id) {
        menuMapper.deleteByPrimaryKey(id);
    }
}
