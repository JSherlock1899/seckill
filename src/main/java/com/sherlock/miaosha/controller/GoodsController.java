package com.sherlock.miaosha.controller;

import com.sherlock.miaosha.domain.MiaoshaUser;
import com.sherlock.miaosha.redis.GoodsKey;
import com.sherlock.miaosha.redis.RedisService;
import com.sherlock.miaosha.service.GoodsService;
import com.sherlock.miaosha.service.MiaoshaUserService;
import com.sherlock.miaosha.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @program: miaosha
 * @description:
 * @author: Mr.Jiang
 * @create: 2019-07-25 10:34
 **/

@Controller
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    MiaoshaUserService userService;

    @Autowired
    GoodsService goodsService;

    @Autowired
    RedisService redisService;

    @Autowired
    ThymeleafViewResolver thymeleafViewResolver;

    @RequestMapping(value = "/to_list",produces = "text/html")
    @ResponseBody
    public String toList(HttpServletRequest request, HttpServletResponse response,Model model, MiaoshaUser miaoshaUser){
        //查询商品列表
        List<GoodsVo> goodsList = goodsService.listGoodsVo();
        model.addAttribute("goodsList",goodsList);
        //取缓存
        String html = redisService.get(GoodsKey.getById, "", String.class);
        if (!StringUtils.isEmpty(html)){
            return html;
        }
        WebContext webContext = new WebContext(request,response,request.getServletContext(),
                request.getLocale(),model.asMap());
        //手动渲染
        html = thymeleafViewResolver.getTemplateEngine().process("goods_list", webContext);
        if (!StringUtils.isEmpty(html)){
            redisService.set(GoodsKey.getById,"",html);
        }
        return html;
    }

    @RequestMapping(value="/to_detail/{goodsId}",produces = "text/html")
    @ResponseBody
    public String toDetail(HttpServletRequest request, HttpServletResponse response,
                           Model model, MiaoshaUser user, @PathVariable("goodsId") long goodsId){
        // 1.取缓存
        // public <T> T get(KeyPrefix prefix,String key,Class<T> data)
        String html = redisService.get(GoodsKey.getGoodsDetail, ""+goodsId, String.class);//不同商品页面不同的详情
        if (!StringUtils.isEmpty(html)) {
            return html;
        }
        //缓存中没有，则将业务数据取出，放到缓存中去。
        model.addAttribute("user",user);
        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
        model.addAttribute("goods",goods);
        long start_date = goods.getStart_date().getTime();
        long end_date = goods.getEnd_date().getTime();
        long now = System.currentTimeMillis();
        //秒杀状态
        int miaoshaStatus = 0;
        //秒杀剩余时间
        int remainSeconds = 0;
        if (now < start_date){  //秒杀还没开始，倒计时
            miaoshaStatus = 0;
            remainSeconds = (int) ((start_date - now) / 1000);
        }else if(now > end_date){   //秒杀已经结束
            miaoshaStatus = 2;
        }else{  //秒杀中
            miaoshaStatus = 1;
        }
        model.addAttribute("miaoshaStatus",miaoshaStatus);
        model.addAttribute("remainSeconds",remainSeconds);
        // 2.手动渲染 使用模板引擎 templateName:模板名称 String templateName="goods_detail";
        WebContext context = new WebContext(request, response, request.getServletContext(),
                request.getLocale(), model.asMap());
        html = thymeleafViewResolver.getTemplateEngine().process("goods_detail", context);
        // 将渲染好的html保存至缓存
        if (!StringUtils.isEmpty(html)) {
            System.out.println("!!!!!!!!!!!!!!!");
            redisService.set(GoodsKey.getGoodsDetail, ""+goodsId, html);
        }
        return html;//html是已经渲染好的html文件
    }
}
