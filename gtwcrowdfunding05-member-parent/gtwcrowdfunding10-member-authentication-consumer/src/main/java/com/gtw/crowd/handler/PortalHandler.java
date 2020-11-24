package com.gtw.crowd.handler;


import com.gtw.crowd.api.MySQLRemoteService;
import com.gtw.crowd.constant.Constant;
import com.gtw.crowd.entity.vo.MemberLoginVO;
import com.gtw.crowd.entity.vo.PortalTypeVO;
import com.gtw.crowd.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;


/**
 * @author
 * @create 2020-11-15-23:36
 */
@Controller
public class PortalHandler {

    @Autowired
    MySQLRemoteService mySQLRemoteService;

    @RequestMapping("/")
    public String showPortalPage(Map map){
        //load projectInfo from database
        ResultEntity<List<PortalTypeVO>> portalTypeVOListResult = mySQLRemoteService.getPortalTypeVOListRemote();
        //2.check result entity
        if(portalTypeVOListResult.getCode().equals(200)) {
            map.put(Constant.ATTR_NAME_MESSAGE,portalTypeVOListResult.getMessage());
            return "portal";
        }
        List<PortalTypeVO> portalTypeVOList = portalTypeVOListResult.getData();
        map.put(Constant.ATTR_NAME_PORTALTYPES,portalTypeVOList);
        return "portal";
    }



}
