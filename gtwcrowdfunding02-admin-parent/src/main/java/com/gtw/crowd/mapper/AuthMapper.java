package com.gtw.crowd.mapper;



import com.gtw.crowd.entity.Auth;
import com.gtw.crowd.entity.AuthExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AuthMapper {
    long countByExample(AuthExample example);

    int deleteByExample(AuthExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Auth record);

    int insertSelective(Auth record);

    List<Auth> selectByExample(AuthExample example);

    Auth selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Auth record, @Param("example") AuthExample example);

    int updateByExample(@Param("record") Auth record, @Param("example") AuthExample example);

    int updateByPrimaryKeySelective(Auth record);

    int updateByPrimaryKey(Auth record);

    List<Integer> selectAssignedAuthIdList(Integer roleId);

    void deleteOldAssignedAuthList(Integer roleId);

    void insertAssignedAuthList(@Param("roleId") Integer roleId, @Param("list") List<Integer> authList);

    List<String> selectAssignedAuthListByAdminId(Integer adminId);
}