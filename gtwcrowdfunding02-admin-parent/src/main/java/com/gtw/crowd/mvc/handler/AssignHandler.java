package com.gtw.crowd.mvc.handler;



import com.gtw.crowd.service.api.AdminService;
import com.gtw.crowd.service.api.AuthService;
import com.gtw.crowd.service.api.RoleService;
import com.gtw.crowd.entity.Auth;
import com.gtw.crowd.entity.Role;
import com.gtw.crowd.util.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * @author
 * @create 2020-10-31-21:33
 */
@Controller
public class AssignHandler {
    @Autowired
    private AdminService adminService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private AuthService authService;

    @RequestMapping("auth/tree.json")
    @ResponseBody
    public ResultEntity getWholeAuthTree(){
        List<Auth> authList = authService.getAll();
        return ResultEntity.success().add("authList",authList);
    }

    @ResponseBody
    @RequestMapping("auth/assigned.json")
    public ResultEntity getAssignedAuthList(@RequestParam("roleId") Integer roleId){
        List<Integer> assignedAuthList = authService.getAssignedAuthIdList(roleId);
        return ResultEntity.success().add("assignedAuthList",assignedAuthList);
    }

    @ResponseBody
    @RequestMapping("auth/save.json")
    public ResultEntity saveAssignedAuthList(@RequestBody Map<String,List<Integer>> map){
        Integer roleId=map.get("roleId").get(0);
        List<Integer> authList=map.get("authIdArray");
        authService.saveAssignedAuthList(roleId,authList);
        return ResultEntity.success();
    }

    @RequestMapping("admin/to/assign/role/page.html")
    public String toAssignRole(@RequestParam(value = "keyword",required = false,defaultValue = "")String keyword,
                               @RequestParam(value = "adminId",required = false,defaultValue = "1")Integer adminId,
                               @RequestParam(value = "pageNum",required = false,defaultValue = "1")Integer pageNum,
                               Map map){

        List<Role> assignedRoleList=roleService.getAssignedRoleList(adminId);
        List<Role>  unassignedRoleList=roleService.getUnassignedRoleList(adminId);
        map.put("assignedRoleList",assignedRoleList);
        map.put("unassignedRoleList",unassignedRoleList);
        return "assign-main";
    }

    @
    RequestMapping("assign/role/save.html")
    public String saveAssignRoleList(@RequestParam(value = "keyword",required = false,defaultValue = "")String keyword,
                                     @RequestParam(value = "adminId",required = false,defaultValue = "1")Integer adminId,
                                     @RequestParam(value = "pageNum",required = false,defaultValue = "1")Integer pageNum,
                                     @RequestParam(value = "roleList",required = false) List<Integer> roleIds){

        adminService.saveAssignedRoleList(adminId,roleIds);
        return "redirect:/admin/page.html?pageNum="+pageNum+"&keyword="+keyword;
    }
}
