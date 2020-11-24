package com.gtw.crowd.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.gtw.crowd.service.api.RoleService;
import com.gtw.crowd.entity.Role;
import com.gtw.crowd.mapper.RoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * @author
 * @create 2020-10-29-19:11
 */
@Repository
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public PageInfo getRolePage(Integer pageNum, Integer pageSize, String keyword) {
        PageHelper.startPage(pageNum,pageSize);
        List<Role> roles = roleMapper.selectRoleListByKeyword(keyword);
        PageInfo pageInfo=new PageInfo(roles,5);
        return pageInfo;
    }

    @Override
    public void saveRole(Role role) {
        int insert = roleMapper.insert(role);
    }

    @Override
    public void editRole(Role role) {
        int i = roleMapper.updateByPrimaryKey(role);
    }

    @Override
    public void deleteRolesById(ArrayList<Integer> list) {
        roleMapper.deleteRolesByIds(list);
    }

    @Override
    public List<Role> getAssignedRoleList(Integer adminId) {
        return roleMapper.selectAssignedRoleList(adminId);
    }

    @Override
    public List<Role> getUnassignedRoleList(Integer adminId) {
        return roleMapper.selectUnassignedRoleList(adminId);
    }


}
