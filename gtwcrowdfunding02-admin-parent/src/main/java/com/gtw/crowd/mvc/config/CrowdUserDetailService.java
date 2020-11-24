package com.gtw.crowd.mvc.config;



import com.gtw.crowd.service.api.AdminService;
import com.gtw.crowd.service.api.AuthService;
import com.gtw.crowd.service.api.RoleService;
import com.gtw.crowd.entity.Admin;
import com.gtw.crowd.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;

/**
 * @author
 * @create 2020-11-03-2:46
 */
public class CrowdUserDetailService implements UserDetailsService {
    @Autowired
    private AdminService adminService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private AuthService authService;
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        // 1.根据账号名称查询Admin 对象
            Admin admin=adminService.getAdminByAcct(s);
        // 2.获取adminId
            Integer adminId=admin.getId();
        // 3.根据adminId 查询角色信息
        List<Role> assignedRoleList = roleService.getAssignedRoleList(adminId);
        // 4.根据adminId 查询权限信息
        List<String> assignedAuthList = authService.getAssignedAuthListByadminId(adminId);
        // 5.创建集合对象用来存储GrantedAuthority
        List<GrantedAuthority> authorities = new ArrayList<>();
        // 6.遍历assignedRoleList 存入角色信息
        for(Role role:assignedRoleList){
            String roleName="ROLE_"+role.getRoleName();
            SimpleGrantedAuthority simpleGrantedAuthority = new
                    SimpleGrantedAuthority(roleName);
            authorities.add(simpleGrantedAuthority);
        }
        for(String authName:assignedAuthList){
            SimpleGrantedAuthority simpleGrantedAuthority = new
                    SimpleGrantedAuthority(authName);
            authorities.add(simpleGrantedAuthority);
        }
        SecurityAdmin securityAdmin=new SecurityAdmin(admin,authorities);
        return securityAdmin;
    }
}
