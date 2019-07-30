package com.sherlock.miaosha.redis;

/**
 * @program: miaosha
 * @description:
 * @author: Mr.Jiang
 * @create: 2019-07-28 11:56
 **/

public class GoodsKey extends BasePrefix {

    public GoodsKey(int expireSeconds ,String prefix) {
        super(expireSeconds,prefix);
    }

    public static GoodsKey getById = new GoodsKey(60,"gl");
    public static GoodsKey getGoodsDetail = new GoodsKey(60,"glDetail");
}
