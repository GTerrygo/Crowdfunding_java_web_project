package com.gtw.crowd.mvc.handler;

import com.github.pagehelper.PageInfo;


import com.gtw.crowd.service.api.RoleService;
import com.gtw.crowd.entity.Role;
import com.gtw.crowd.util.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

/**
 * @author
 * @create 2020-10-29-19:01
 */
@Controller
public class RoleHandler {
    @Autowired
    private RoleService roleService;

    @RequestMapping("role/page.json")
    @ResponseBody
    public ResultEntity getRoles(@RequestParam(value = "keyword",required = false,defaultValue = "")String keyword,
                                 @RequestParam(value = "pageNum",required = false,defaultValue = "1")Integer pageNum,
                                 @RequestParam(value = "pageSize",required = false,defaultValue = "5")Integer pageSize,
                                 HttpServletRequest request){
        PageInfo rolePage = roleService.getRolePage(pageNum, pageSize, keyword);
        return ResultEntity.success().add("pageInfo",rolePage);
    }

    @RequestMapping("role/save.json")
    @ResponseBody
    public ResultEntity saveRole(Role role){
        roleService.saveRole(role);
        return ResultEntity.success();
    }

    @RequestMapping("role/edit.json")
    @ResponseBody
    public ResultEntity editRole( Role role){
        roleService.editRole(role);
        return ResultEntity.success();
    }

    @ResponseBody
    @RequestMapping("role/delete.json")
    public ResultEntity deleteRoles(@RequestParam("ids") String ids){
        String[] slipedId = ids.split("-");
        ArrayList<Integer> list = new ArrayList<>();
        for(String id:slipedId){
            list.add(Integer.parseInt(id));
        }
        roleService.deleteRolesById(list);
        return ResultEntity.success();
    }
}
