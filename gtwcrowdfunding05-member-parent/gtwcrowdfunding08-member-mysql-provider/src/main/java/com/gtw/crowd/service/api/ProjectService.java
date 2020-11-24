package com.gtw.crowd.service.api;

import com.gtw.crowd.entity.vo.DetailProjectVO;
import com.gtw.crowd.entity.vo.PortalTypeVO;
import com.gtw.crowd.entity.vo.ProjectVO;

import java.util.List;

/**
 * @author
 * @create 2020-11-20-3:20
 */
public interface ProjectService {

    void saveProject(ProjectVO projectVO, Integer memberId);

    List<PortalTypeVO> getPortalTypeVOList();

    DetailProjectVO getDetailProjectVO(Integer projectId);
}
