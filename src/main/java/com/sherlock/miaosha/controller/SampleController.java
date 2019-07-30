package com.sherlock.miaosha.controller;

import com.sherlock.miaosha.domain.User;
import com.sherlock.miaosha.redis.UserKey;
import com.sherlock.miaosha.result.CodeMsg;
import com.sherlock.miaosha.result.Result;
import com.sherlock.miaosha.service.UserService;
import com.sherlock.miaosha.redis.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @program: miaosha
 * @description:
 * @author: Mr.Jiang
 * @create: 2019-07-22 14:38
 **/
@Controller
@RequestMapping("/demo")
public class SampleController {

    @Autowired
    UserService userService;

    @Autowired
    RedisService redisService;

    @RequestMapping("/dbGet")
    @ResponseBody
    public Result<User> dbGet(){
        User user = userService.getById(1410080408);
        return Result.success(user);
    }

    @RequestMapping("/dbTx")
    @ResponseBody
    public Result<Boolean> dbTx(){
        userService.tx();
        return Result.success(true);
    }



    @RequestMapping("/thymeleaf")
    @ResponseBody
    public String thymeleaf(Model model){
        model.addAttribute("name","sherlock");
        return "hello";
    }

    @RequestMapping("/hello")
    @ResponseBody
    public Result<String> hello(){
       return Result.success("hello sherlock");
    }

    @RequestMapping("/error")
    @ResponseBody
    public Result<String> error(){
        return Result.error(CodeMsg.SERVER_ERROR);
    }

    @RequestMapping("/redis/get")
    @ResponseBody
    public Result<User> redisGet(){
        User user = redisService.get(UserKey.getById,"" + 1, User.class);
        return Result.success(user);
    }

    @RequestMapping("/redis/set")
    @ResponseBody
    public Result<Boolean> redisSet(){
        User user = new User();
        user.setId(22);
        user.setName("JSherlock");
        redisService.set(UserKey.getById,"" + 2,user);
        return Result.success(true);
    }
}
