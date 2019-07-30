package com.sherlock.miaosha.vo;

import com.sherlock.miaosha.domain.Goods;

import java.util.Date;

/**
 * @program: miaosha
 * @description:
 * @author: Mr.Jiang
 * @create: 2019-07-27 09:05
 **/

public class GoodsVo extends Goods {

    private double seckil_price;

    private Integer stock_count;

    private Date start_date;

    private Date end_date;

    public double getSeckil_price() {
        return seckil_price;
    }

    public void setSeckil_price(double seckill_price) {
        this.seckil_price = seckill_price;
    }

    public Integer getStock_count() {
        return stock_count;
    }

    public void setStock_count(Integer stock_count) {
        this.stock_count = stock_count;
    }

    public Date getStart_date() {
        return start_date;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public Date getEnd_date() {
        return end_date;
    }

    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }


}
