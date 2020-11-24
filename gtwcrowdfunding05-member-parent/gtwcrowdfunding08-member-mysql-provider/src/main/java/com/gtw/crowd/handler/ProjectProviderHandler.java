package com.gtw.crowd.handler;

import com.gtw.crowd.entity.vo.DetailProjectVO;
import com.gtw.crowd.entity.vo.PortalTypeVO;
import com.gtw.crowd.entity.vo.ProjectVO;
import com.gtw.crowd.service.api.ProjectService;
import com.gtw.crowd.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author
 * @create 2020-11-20-3:19
 */
@RestController
public class ProjectProviderHandler {

    @Autowired
    private ProjectService projectService;
    @RequestMapping("/save/project/remote")
    ResultEntity saveProjectRemote(@RequestBody ProjectVO projectVO, @RequestParam("memberId") Integer memberId) {

        try {
            projectService.saveProject(projectVO,memberId);
            return ResultEntity.success();
        } catch (Exception exception) {
            return ResultEntity.error(exception.getMessage());
        }
    }

    @RequestMapping("/get/portal/type/list/remote")
    ResultEntity<List<PortalTypeVO>> getPortalTypeVOListRemote(){

        try {
            List<PortalTypeVO> portalTypeVOList = projectService.getPortalTypeVOList();
            ResultEntity resultEntity = new ResultEntity<List<PortalTypeVO>>(1).add(portalTypeVOList);
            return resultEntity;
        } catch (Exception exception) {
            return new ResultEntity<List<PortalTypeVO>>(0,exception.getMessage()).add(null);
        }
    }

    @RequestMapping("/get/detail/project/info/remote")
    ResultEntity<DetailProjectVO> getDetailProjectInfoRemote(@RequestParam("projectId") Integer projectId){

        try {
            DetailProjectVO detailProjectVO=projectService.getDetailProjectVO(projectId);
            return new ResultEntity<DetailProjectVO>(1).add(detailProjectVO);
        } catch (Exception exception) {
            return new ResultEntity<DetailProjectVO>(0,exception.getMessage()).add(null);
        }
    }
}
