package com.sherlock.miaosha.service;

import com.sherlock.miaosha.domain.MiaoshaUser;
import com.sherlock.miaosha.domain.OrderInfo;
import com.sherlock.miaosha.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @program: miaosha
 * @description:
 * @author: Mr.Jiang
 * @create: 2019-07-22 16:08
 **/
@Service
public class MiaoshaService {

    @Autowired
    GoodsService goodsService;
    @Autowired
    OrderService OrderService;

    @Transactional
    public OrderInfo miaosha(MiaoshaUser user, GoodsVo goodsVo) {
        //减库存   下订单   写入秒杀订单
        goodsService.reduceStock(goodsVo);
        OrderInfo orderInfo = OrderService.createOrder(user,goodsVo);
        return orderInfo;
    }
}
