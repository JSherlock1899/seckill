package com.sherlock.miaosha.dao;

import com.sherlock.miaosha.domain.MiaoshaOrder;
import com.sherlock.miaosha.domain.OrderInfo;
import org.apache.ibatis.annotations.*;

@Mapper
public interface OrderDao {

    @Select("select * from seckill_order where user_id=#{userId} and goods_id=#{goodsId}")
    public MiaoshaOrder getMiaoshaOrderByUserIdAndGoodsId(@Param("userId") Long userId, @Param("goodsId") Long goodsId);

    @Insert("insert into order_info (user_id,goods_id,goods_name,goods_count,goods_price,order_channel,status,create_date) values "
            + "(#{userId},#{goodsId},#{goodsName},#{goodsCount},#{goodsPrice},#{orderChannel},#{orderStatus},#{createDate})")
    @SelectKey(keyColumn="id",keyProperty="id",resultType=long.class,before=false,statement="select last_insert_id()")
    public long insert(OrderInfo orderInfo);//使用注解获取返回值，返回上一次插入的id

    @Select("select * from order_info where user_id=#{userId} and goods_id=#{goodsId}")
    public OrderInfo selectorderInfo(@Param("userId") int userId, @Param("goodsId") Long goodsId);//使用注解获取返回值，返回上一次插入的id

    @Insert("insert into seckill_order (user_id,goods_id,order_id) values (#{userId},#{goodsId},#{orderId})")
    public void insertMiaoshaOrder(MiaoshaOrder miaoshaorder);

    @Select("select * from order_info where id=#{orderId}")
    public OrderInfo getOrderByOrderId(@Param("orderId") long orderId);

}
