package com.gtw.crowd.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.gtw.crowd.entity.Admin;
import com.gtw.crowd.entity.AdminExample;
import com.gtw.crowd.mapper.AdminMapper;
import com.gtw.crowd.service.api.AdminService;
import com.gtw.crowd.util.constant.Constant;
import com.gtw.crowd.util.exception.LoginAcctAlreadyUseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author
 * @create 2020-10-25-4:15
 */
@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

//    @Override
//    public Admin getAdminByAcct(String loginAcct,String userPswd) {
//        AdminExample adminExample = new AdminExample();
//        AdminExample.Criteria criteria = adminExample.createCriteria();
//        criteria.andLoginAcctEqualTo(loginAcct);
//        List<Admin> adminList = adminMapper.selectByExample(adminExample);
//        //if the account not exists
//        if(adminList.isEmpty() || adminList.size()>1)
//            throw new LoginFailedException(Constant.LOGINACCT_STRING_NONEXIST);
//        Admin admin=adminList.get(0);
//        if (admin==null)
//            throw new LoginFailedException(Constant.LOGINACCT_STRING_NONEXIST);
//        //match passwords
//        String encodedUserPswd = CrowdUtil.md5Code(userPswd);
//        if(!admin.getUserPswd().equals(encodedUserPswd)){
//            throw new LoginFailedException(Constant.PASSWORD_STRING_ERROR);
//        }
//        return admin;
//    }
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public Admin getAdminByAcct(String loginAcct) {
        AdminExample adminExample = new AdminExample();
        AdminExample.Criteria criteria = adminExample.createCriteria();
        criteria.andLoginAcctEqualTo(loginAcct);
        List<Admin> adminList = adminMapper.selectByExample(adminExample);
        Admin admin=adminList.get(0);
        return admin;
    }

    @Override
    public void saveAdmin(Admin admin) {
        //add create time
        Date date = new Date();
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String createTime=format.format(date);
        admin.setCreateTime(createTime);

        //do md5 encoding for password
        String encodedPassword=passwordEncoder.encode(admin.getUserPswd());
        admin.setUserPswd(encodedPassword);

        try {
            adminMapper.insert(admin);
        }catch (Exception exception){
            exception.printStackTrace();
            if(exception instanceof DuplicateKeyException){
                throw new LoginAcctAlreadyUseException(Constant.MESSAGE_LOGIN_ACCT_REPEAT);
            }
        }

    }

    @Override
    public List<Admin> getAll() {
        return adminMapper.selectByExample(new AdminExample());
    }

    @Override
    public PageInfo getAdminPage(String keyword, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Admin> adminList = adminMapper.selectAdminListByKeyword(keyword);
        PageInfo pageInfo=new PageInfo(adminList,5);
        return pageInfo;
    }

    @Override
    public void editAdmin(Admin admin) {
        adminMapper.updateByPrimaryKeySelective(admin);
    }

    @Override
    public Admin getAdminById(Integer id) {
        return adminMapper.selectByPrimaryKey(id);
    }

    @Override
    public void deleteAdmin(Integer id) {
        adminMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void saveAssignedRoleList(Integer adminId,List<Integer> roleIds) {
        adminMapper.deleteOldRelationship(adminId);
        adminMapper.insertNewRelationship(adminId,roleIds);
    }
}
