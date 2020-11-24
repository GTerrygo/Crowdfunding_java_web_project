package com.gtw.crowd.service.api;

import com.gtw.crowd.entity.vo.AddressVO;
import com.gtw.crowd.entity.vo.OrderProjectVO;
import com.gtw.crowd.entity.vo.OrderVO;

import java.util.List;

/**
 * @author
 * @create 2020-11-22-22:24
 */
public interface OrderService {
    OrderProjectVO getOrderStepOne(Integer returnId);
    List<AddressVO> getAddressInfo(Integer memberId);

    void saveAddress(AddressVO addressVO);

    void saveOrderVO(OrderVO orderVO);
}
