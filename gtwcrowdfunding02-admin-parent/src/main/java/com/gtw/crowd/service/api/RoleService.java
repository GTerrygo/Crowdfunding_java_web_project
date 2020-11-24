package com.gtw.crowd.service.api;


import com.github.pagehelper.PageInfo;
import com.gtw.crowd.entity.Role;


import java.util.ArrayList;
import java.util.List;

/**
 * @author
 * @create 2020-10-29-19:10
 */
public interface RoleService {

    PageInfo getRolePage(Integer pageNum, Integer pageSize, String keyword);

    void saveRole(Role role);

    void editRole(Role role);

    void deleteRolesById(ArrayList<Integer> list);

    List<Role> getAssignedRoleList(Integer adminId);

    List<Role> getUnassignedRoleList(Integer adminId);
}
