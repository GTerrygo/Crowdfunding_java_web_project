package com.gtw.crowd.service.api;

import com.github.pagehelper.PageInfo;
import com.gtw.crowd.entity.Admin;


import java.util.List;

/**
 * @author
 * @create 2020-10-25-4:14
 */
public interface AdminService {

//    Admin getAdminByAcct(String loginAcct,String userPswd) ;

    Admin getAdminByAcct(String loginAcct);

    void saveAdmin(Admin admin);

    List<Admin> getAll();

    PageInfo getAdminPage(String keyword, Integer pageNum, Integer pageSize);

    void editAdmin(Admin admin);

    Admin getAdminById(Integer id);

    void deleteAdmin(Integer id);

    void saveAssignedRoleList(Integer adminId,List<Integer> roleIds);
}
