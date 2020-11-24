package com.gtw.crowd.mvc.handler;


import com.gtw.crowd.service.api.MenuService;
import com.gtw.crowd.entity.Menu;
import com.gtw.crowd.util.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author
 * @create 2020-10-30-21:26
 */
@Controller
public class MenuHandler {
    @Autowired
    private MenuService menuService;

    @PreAuthorize("hasRole('SA')")
    @RequestMapping("menu/tree.json")
    @ResponseBody
    public ResultEntity getWholeTreeNew(){
        Menu root = menuService.getWholeTree();
        return ResultEntity.success().add("root",root);
    }

    @PreAuthorize("hasRole('PG - 程序员')")
    @ResponseBody
    @RequestMapping("menu/add.json")
    public ResultEntity addMenu(Menu menu){
        menuService.addMenu(menu);
        return ResultEntity.success();
    }

    @ResponseBody
    @RequestMapping("menu/edit.json")
    public ResultEntity editMenu(Menu menu){
        menuService.editMenuById(menu);
        return ResultEntity.success();
    }

    @RequestMapping("menu/remove.json")
    @ResponseBody
    public ResultEntity removeMenu(@RequestParam("id") Integer id){
        menuService.removeMenuById(id);
        return ResultEntity.success();
    }
}
