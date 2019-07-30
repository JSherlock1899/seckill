package com.sherlock.miaosha.controller;

import com.sherlock.miaosha.domain.MiaoshaUser;
import com.sherlock.miaosha.redis.RedisService;
import com.sherlock.miaosha.result.Result;
import com.sherlock.miaosha.service.GoodsService;
import com.sherlock.miaosha.service.MiaoshaUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/user")
@Controller
public class UserController {
	@Autowired
	GoodsService goodsService;
	@Autowired
	RedisService redisService;
	@Autowired
	MiaoshaUserService miaoshaUserService;
	

	@RequestMapping("/info") 
	@ResponseBody
	public Result<MiaoshaUser> info(Model model, MiaoshaUser user) {
		System.out.println(user.getId());
		return Result.success(user);//返回页面login
	}

	
}
