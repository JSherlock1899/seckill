package com.sherlock.miaosha.result;

/**
 * @program: miaosha
 * @description:
 * @author: Mr.Jiang
 * @create: 2019-07-22 15:25
 **/

public class CodeMsg {

    private int code;
    private String msg;

    public static CodeMsg SUCCESS = new CodeMsg(200000,"操作成功");
    //通用异常
    public static CodeMsg SERVER_ERROR = new CodeMsg(500100,"服务器端异常");
    //登录模块
    public static CodeMsg SESSION_ERROR = new CodeMsg(500210,"Session不存在或者已失效");
    public static CodeMsg PASSWORD_EMPTY = new CodeMsg(500211,"登录密码不能为空");
    public static CodeMsg MOBILE_EMPTY = new CodeMsg(500212,"手机号不能为空");
    public static CodeMsg MOBILE_ERROR = new CodeMsg(500213,"手机号格式错误");
    public static CodeMsg MOBILE_NOT_EXIST = new CodeMsg(500214,"手机号不存在");
    public static CodeMsg PASSWORD_ERROR = new CodeMsg(500215,"密码错误");
    //秒杀模块
    public static CodeMsg MIAOSHA_OVER = new CodeMsg(500500,"库存不足");
    public static CodeMsg REPEATE_MIAOSHA = new CodeMsg(500501,"不能重复秒杀");
    public CodeMsg(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
