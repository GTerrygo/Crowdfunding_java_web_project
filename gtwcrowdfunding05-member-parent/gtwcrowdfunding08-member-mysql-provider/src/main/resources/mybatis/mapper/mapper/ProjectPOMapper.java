package com.gtw.crowd.mapper;

import com.gtw.crowd.entity.po.ProjectPO;
import com.gtw.crowd.entity.po.ProjectPOExample;
import com.gtw.crowd.entity.vo.DetailProjectVO;
import com.gtw.crowd.entity.vo.PortalTypeVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProjectPOMapper {
    long countByExample(ProjectPOExample example);

    int deleteByExample(ProjectPOExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ProjectPO record);

    int insertSelective(ProjectPO record);

    List<ProjectPO> selectByExample(ProjectPOExample example);

    ProjectPO selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ProjectPO record, @Param("example") ProjectPOExample example);

    int updateByExample(@Param("record") ProjectPO record, @Param("example") ProjectPOExample example);

    int updateByPrimaryKeySelective(ProjectPO record);

    int updateByPrimaryKey(ProjectPO record);

    int insertTypeRelationship(@Param("typeIdList") List<Integer> typeIdList,@Param("projectId") Integer projectId);

    int insertTagRelationship(@Param("tagIdList") List<Integer> tagIdList,@Param("projectId") Integer projectId);

    int insertDetailPicturePath(@Param("detailPicturePathList") List<String> detailPicturePathList,@Param("projectId") Integer projectId);

    List<PortalTypeVO> selectPortalTypeVOList();

    DetailProjectVO selectDetailProjectVO(@Param("projectId") Integer projectId);
}