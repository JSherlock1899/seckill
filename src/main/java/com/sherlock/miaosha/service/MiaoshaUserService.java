package com.sherlock.miaosha.service;

import com.sherlock.miaosha.dao.MiaoshaUserDao;
import com.sherlock.miaosha.domain.MiaoshaUser;
import com.sherlock.miaosha.redis.MiaoshaUserKey;
import com.sherlock.miaosha.redis.RedisService;
import com.sherlock.miaosha.result.CodeMsg;
import com.sherlock.miaosha.util.MD5Util;
import com.sherlock.miaosha.util.UUIDUtil;
import com.sherlock.miaosha.vo.LoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * @program: miaosha
 * @description:
 * @author: Mr.Jiang
 * @create: 2019-07-24 15:52
 **/
@Service
public class MiaoshaUserService {

    public static final String COOK1_NAME_TOKEN = "token";
    @Autowired
    MiaoshaUserDao miaoshaUserDao;

    @Autowired
    RedisService redisService;

    public MiaoshaUser getById(Long id){
        //取缓存
        MiaoshaUser user = redisService.get(MiaoshaUserKey.getById, "" + id, MiaoshaUser.class);
        if (user != null){
            return user;
        }
        //取数据库
        user = miaoshaUserDao.getById(id);
        return miaoshaUserDao.getById(id);
    }

    public CodeMsg login(HttpServletResponse response,LoginVo loginVo) {
        if(loginVo == null){
            return CodeMsg.SERVER_ERROR;
        }
        String mobile = loginVo.getMobile();
        String formPass = loginVo.getPassword();
        //判断手机号是否存在
        MiaoshaUser user = getById(Long.parseLong(mobile));
        if (user == null){
            return CodeMsg.MOBILE_NOT_EXIST;
        }
        //验证密码
        String dbPass = user.getPassword();
        String saltDB = user.getSalt();
        String calcPass = MD5Util.FromPassToDBPass(formPass, saltDB);
        if (!calcPass.equals(dbPass)){
            return CodeMsg.PASSWORD_ERROR;
        }
        //生成cookie
        addCookie(response,user);
        return CodeMsg.SUCCESS;
    }

    public MiaoshaUser getByToken(HttpServletResponse response,String token) {
        if (StringUtils.isEmpty(token)){
            return null;
        }
        MiaoshaUser user = redisService.get(MiaoshaUserKey.token, token, MiaoshaUser.class);
        //延长有效期
        if (user !=null){
            addCookie(response,user);
        }
        return user;
    }

    private void addCookie(HttpServletResponse response,MiaoshaUser user){
        String token = UUIDUtil.uuid();
        redisService.set(MiaoshaUserKey.token,token,user);
        Cookie cookie = new Cookie(COOK1_NAME_TOKEN,token);
        cookie.setMaxAge(MiaoshaUserKey.token.expireSeconds());
        cookie.setPath("/");
        response.addCookie(cookie);
    }
}
