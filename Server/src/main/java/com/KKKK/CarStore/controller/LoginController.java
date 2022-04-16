package com.kbz1121.CarStore.controller;

import com.alibaba.fastjson.JSON;
import com.kbz1121.CarStore.entity.User;
import com.kbz1121.CarStore.service.UserServcie;
import com.kbz1121.CarStore.system.ResponseFormat.BaseResponse;
import com.kbz1121.CarStore.system.ResponseFormat.ResponseResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@BaseResponse
@RequestMapping("/login")
public class LoginController {

    @Resource
    UserServcie userServcie;

    @RequestMapping("/login")
    @ResponseBody
    public ResponseResult login(@RequestParam(required = true) String data){
        User user = JSON.parseObject(data,User.class);
        return userServcie.login(user);
    }

    @RequestMapping("/regist")
    @ResponseBody
    public ResponseResult getArticle(@RequestParam(required = true) String data){
        User user = JSON.parseObject(data,User.class);
        return userServcie.regist(user);
    }



}
