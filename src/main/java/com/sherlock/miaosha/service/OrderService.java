package com.sherlock.miaosha.service;

import com.sherlock.miaosha.dao.OrderDao;
import com.sherlock.miaosha.domain.MiaoshaOrder;
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
public class OrderService {

    @Autowired
    OrderDao orderDao;

    public MiaoshaOrder getMiaoshaOrderByUserIdAndGoodsId(long id, long goodsId) {
        return orderDao.getMiaoshaOrderByUserIdAndGoodsId(id,goodsId);
    }

    @Transactional
    public OrderInfo createOrder(MiaoshaUser user, GoodsVo goodsvo) {
        //1.生成order_info
        OrderInfo orderInfo=new OrderInfo();
        orderInfo.setDeliveryAddrId(0L);//long类型 private Long deliveryAddrId;   L
        orderInfo.setGoodsCount(1);
        orderInfo.setGoodsId(goodsvo.getId());
        //秒杀价格
        orderInfo.setGoodsPrice(goodsvo.getSeckil_price());
        orderInfo.setOrderChannel(1);
        //订单状态  ---0-新建未支付  1-已支付  2-已发货  3-已收货
        orderInfo.setOrderStatus(0);
        //用户id
        orderInfo.setUserId((long) user.getId());
        //返回orderId
        long orderId=orderDao.insert(orderInfo);
        System.out.println("-----orderId:"+orderId);

        OrderInfo orderquery=orderDao.selectorderInfo(user.getId(), goodsvo.getId());
        long orderIdquery=orderquery.getId();
        System.out.println("-----orderIdquery:"+orderIdquery);

        //2.生成miaosha_order
        MiaoshaOrder miaoshaorder =new MiaoshaOrder();
        miaoshaorder.setGoodsId(goodsvo.getId());
        //将订单id传给秒杀订单里面的订单orderid
        miaoshaorder.setOrderId(orderIdquery);
        miaoshaorder.setUserId((long) user.getId());
        orderDao.insertMiaoshaOrder(miaoshaorder);
        //set(KeyPrefix prefix,String key,T value)   设置缓存数据。
//        redisService.set(OrderKey.getMiaoshaOrderByUidAndGid, ""+user.getId()+"_"+goodsvo.getId(), miaoshaorder);
        return orderInfo;
    }
}
