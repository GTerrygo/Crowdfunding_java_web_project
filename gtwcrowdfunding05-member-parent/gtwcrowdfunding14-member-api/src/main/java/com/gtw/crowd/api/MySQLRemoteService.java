package com.gtw.crowd.api;

import com.gtw.crowd.entity.po.Member;
import com.gtw.crowd.entity.vo.*;
import com.gtw.crowd.util.ResultEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


/**
 * @author
 * @create 2020-11-15-20:56
 */
@FeignClient(value = "gtw-crowd-mysql")
@Component
public interface MySQLRemoteService {
    @RequestMapping("/get/memberpo/by/login/acct/remote")
    ResultEntity<Member> getMemberPOByLoginAcctRemote(@RequestParam("loginacct") String loginacct);

    @RequestMapping("/auth/member/do/reg/remote")
    ResultEntity saveMemberRemote(@RequestBody Member member);

    @RequestMapping("/save/project/remote")
    ResultEntity saveProjectRemote(@RequestBody ProjectVO projectVO,@RequestParam("memberId")Integer memberId);

    @RequestMapping("/get/portal/type/list/remote")
    ResultEntity<List<PortalTypeVO>> getPortalTypeVOListRemote();

    @RequestMapping("/get/detail/project/info/remote")
    ResultEntity<DetailProjectVO> getDetailProjectVORemote(@RequestParam("projectId") Integer projectId);

    @RequestMapping("/show/order/step/one/remote")
    ResultEntity<OrderProjectVO> showOrderStepOneRemote(@RequestParam("returnId") Integer returnId);

    @RequestMapping("/get/address/info/remote")
    ResultEntity<List<AddressVO>> getAddressInfoRemote(@RequestParam("memberId") Integer memberId);

    @RequestMapping("/save/address/remote")
    ResultEntity<String> saveAddressRemote(@RequestBody AddressVO addressVO);

    @RequestMapping("/save/ordervo/remote")
    ResultEntity<String> saveOrderVORemote(@RequestBody OrderVO orderVO);
}
