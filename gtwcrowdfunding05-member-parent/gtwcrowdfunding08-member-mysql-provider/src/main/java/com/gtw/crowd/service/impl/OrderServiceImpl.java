package com.gtw.crowd.service.impl;

import com.gtw.crowd.entity.po.AddressPO;
import com.gtw.crowd.entity.po.AddressPOExample;
import com.gtw.crowd.entity.po.OrderPO;
import com.gtw.crowd.entity.po.OrderProjectPO;
import com.gtw.crowd.entity.vo.AddressVO;
import com.gtw.crowd.entity.vo.OrderProjectVO;
import com.gtw.crowd.entity.vo.OrderVO;
import com.gtw.crowd.mapper.AddressPOMapper;
import com.gtw.crowd.mapper.OrderPOMapper;
import com.gtw.crowd.mapper.OrderProjectPOMapper;
import com.gtw.crowd.service.api.OrderService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author
 * @create 2020-11-22-22:24
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderProjectPOMapper orderProjectPOMapper;

    @Autowired
    AddressPOMapper addressPOMapper;

    @Autowired
    OrderPOMapper orderPOMapper;

    @Override
    public OrderProjectVO getOrderStepOne(Integer returnId) {
        OrderProjectVO orderProjectVO = orderProjectPOMapper.selectOrderProjectVO(returnId);
        return orderProjectVO;
    }

    @Override
    public List<AddressVO> getAddressInfo(Integer memberId) {
        AddressPOExample addressPOExample = new AddressPOExample();
        AddressPOExample.Criteria criteria = addressPOExample.createCriteria();
        criteria.andMemberIdEqualTo(memberId);
        List<AddressPO> addressPOList = addressPOMapper.selectByExample(addressPOExample);

        List<AddressVO> addressVOList=new ArrayList<>();

        for(AddressPO addressPO:addressPOList){
            AddressVO addressVO = new AddressVO();
            BeanUtils.copyProperties(addressPO,addressVO);
            addressVOList.add(addressVO);
        }

        return addressVOList;
    }

    @Override
    public void saveAddress(AddressVO addressVO) {
        AddressPO addressPO = new AddressPO();
        BeanUtils.copyProperties(addressVO,addressPO);
        addressPOMapper.insertSelective(addressPO);
    }

    @Override
    public void saveOrderVO(OrderVO orderVO) {

        OrderPO orderPO = new OrderPO();
        BeanUtils.copyProperties(orderVO,orderPO);
        int insert = orderPOMapper.insert(orderPO);

        OrderProjectPO orderProjectPO = new OrderProjectPO();
        OrderProjectVO orderProjectVO = orderVO.getOrderProjectVO();
        orderProjectVO.setOrderId(insert);
        BeanUtils.copyProperties(orderProjectVO,orderProjectPO);
        orderProjectPOMapper.insert(orderProjectPO);

    }
}
