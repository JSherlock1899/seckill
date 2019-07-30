package com.sherlock.miaosha.controller;

import com.sherlock.miaosha.domain.MiaoshaOrder;
import com.sherlock.miaosha.domain.MiaoshaUser;
import com.sherlock.miaosha.domain.OrderInfo;
import com.sherlock.miaosha.result.CodeMsg;
import com.sherlock.miaosha.service.GoodsService;
import com.sherlock.miaosha.service.MiaoshaService;
import com.sherlock.miaosha.service.MiaoshaUserService;
import com.sherlock.miaosha.service.OrderService;
import com.sherlock.miaosha.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @program: miaosha
 * @description:
 * @author: Mr.Jiang
 * @create: 2019-07-25 10:34
 **/

@Controller
@RequestMapping("/miaosha")
public class MiaoshaController {

    @Autowired
    MiaoshaUserService userService;

    @Autowired
    GoodsService goodsService;

    @Autowired
    OrderService orderService;

    @Autowired
    MiaoshaService miaoshaService;

    @RequestMapping("/do_miaosha")
    public String toLogin(Model model, MiaoshaUser user,
                          @RequestParam("goodsId") long goodsId){
        model.addAttribute("user",user);
        if (user == null){
            return "login";
        }
        //判断库存
        GoodsVo goodsVo = goodsService.getGoodsVoByGoodsId(goodsId);
        int stock = goodsVo.getStock_count();
        if (stock <= 0){
            model.addAttribute("errmsg", CodeMsg.MIAOSHA_OVER.getMsg());
            return "miaosha_fail";
        }
        //判断是否秒杀到了
        MiaoshaOrder order = orderService.getMiaoshaOrderByUserIdAndGoodsId(user.getId(),goodsId);
        if (order != null){
            model.addAttribute("errmsg", CodeMsg.REPEATE_MIAOSHA.getMsg());
            return "miaosha_fail";
        }
        //减库存  下订单  写入秒杀订单
        OrderInfo orderInfo = miaoshaService.miaosha(user,goodsVo);
        model.addAttribute("goods",goodsVo);
        model.addAttribute("orderInfo",orderInfo);
        return "order_detail";
    }


}
