package com.gtw.crowd.mapper;



import com.gtw.crowd.entity.Role;
import com.gtw.crowd.entity.RoleExample;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.List;

public interface RoleMapper {
    long countByExample(RoleExample example);

    int deleteByExample(RoleExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Role record);

    int insertSelective(Role record);

    List<Role> selectByExample(RoleExample example);

    List<Role> selectRoleListByKeyword(String keyword);

    Role selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Role record, @Param("example") RoleExample example);

    int updateByExample(@Param("record") Role record, @Param("example") RoleExample example);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);

    void deleteRolesByIds(ArrayList<Integer> list);

    List<Role> selectAssignedRoleList(Integer adminId);

    List<Role> selectUnassignedRoleList(Integer adminId);
}