package com.gtw.crowd.service.impl;


import com.gtw.crowd.entity.Auth;
import com.gtw.crowd.entity.AuthExample;
import com.gtw.crowd.mapper.AuthMapper;
import com.gtw.crowd.service.api.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author
 * @create 2020-11-01-1:37
 */
@Repository
public class AuthServiceImpl implements AuthService {
    @Autowired
    private AuthMapper authMapper;

    @Override
    public List<Auth> getAll() {
        return authMapper.selectByExample(new AuthExample());
    }

    @Override
    public List<Integer> getAssignedAuthIdList(Integer roleId) {
        return authMapper.selectAssignedAuthIdList(roleId);
    }

    @Override
    public void saveAssignedAuthList(Integer roleId, List<Integer> authList) {
        authMapper.deleteOldAssignedAuthList(roleId);
        authMapper.insertAssignedAuthList(roleId,authList);
    }

    @Override
    public List<String> getAssignedAuthListByadminId(Integer adminId) {
        return authMapper.selectAssignedAuthListByAdminId(adminId);
    }
}
