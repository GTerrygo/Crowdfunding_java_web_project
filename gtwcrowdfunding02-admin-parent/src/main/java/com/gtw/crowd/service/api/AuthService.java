package com.gtw.crowd.service.api;





import com.gtw.crowd.entity.Auth;

import java.util.List;

/**
 * @author
 * @create 2020-11-01-1:36
 */
public interface AuthService {
    List<Auth>  getAll();

    List<Integer> getAssignedAuthIdList(Integer roleId);

    void saveAssignedAuthList(Integer roleId, List<Integer> authList);

    List<String> getAssignedAuthListByadminId(Integer adminId);
}
