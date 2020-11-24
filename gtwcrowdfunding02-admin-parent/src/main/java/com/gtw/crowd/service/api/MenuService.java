package com.gtw.crowd.service.api;


import com.gtw.crowd.entity.Menu;

/**
 * @author
 * @create 2020-10-30-21:27
 */
public interface MenuService {
    Menu getWholeTree();

    void addMenu(Menu menu);

    void editMenuById(Menu menu);

    void removeMenuById(Integer id);
}