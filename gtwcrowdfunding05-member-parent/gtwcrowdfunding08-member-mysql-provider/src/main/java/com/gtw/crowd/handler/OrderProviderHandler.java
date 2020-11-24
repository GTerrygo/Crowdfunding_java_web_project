package com.gtw.crowd.handler;

import com.gtw.crowd.entity.vo.AddressVO;
import com.gtw.crowd.entity.vo.OrderProjectVO;
import com.gtw.crowd.entity.vo.OrderVO;
import com.gtw.crowd.service.api.OrderService;
import com.gtw.crowd.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author
 * @create 2020-11-22-22:23
 */
@RestController
public class OrderProviderHandler {

    @Autowired
    private OrderService orderService;
    @RequestMapping("/show/order/step/one/remote")
    ResultEntity<OrderProjectVO> showOrderStepOneRemote(@RequestParam("returnId") Integer returnId){

        try {
            OrderProjectVO orderProjectVO=orderService.getOrderStepOne(returnId);
            return new ResultEntity<OrderProjectVO>(1).add(orderProjectVO);
        } catch (Exception e) {
            return new ResultEntity<OrderProjectVO>(0,e.getMessage()).add(null);
        }
    }

    @RequestMapping("/get/address/info/remote")
    ResultEntity<List<AddressVO>> getAddressInfoRemote(@RequestParam("memberId") Integer memberId){

        try {
            List<AddressVO> addressVOList=orderService.getAddressInfo(memberId);
            return new ResultEntity<List<AddressVO>>(1).add(addressVOList);
        } catch (Exception e) {
            return new ResultEntity<List<AddressVO>>(0,e.getMessage()).add(null);
        }
    }

    @RequestMapping("/save/address/remote")
    ResultEntity<String> saveAddressRemote(@RequestBody AddressVO addressVO){
        try {
            orderService.saveAddress(addressVO);
            return new ResultEntity<String>(1);
        } catch (Exception e) {
            return new ResultEntity<String>(0,e.getMessage());
        }
    }

    @RequestMapping("/save/ordervo/remote")
    ResultEntity<String> saveOrderVORemote(@RequestBody OrderVO orderVO){
        try {
            orderService.saveOrderVO(orderVO);
            return new ResultEntity<String>(1);
        } catch (Exception e) {
            return new ResultEntity<String>(0,e.getMessage());
        }
    }
}
