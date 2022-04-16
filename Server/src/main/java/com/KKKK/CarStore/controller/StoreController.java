package com.kbz1121.CarStore.controller;

import com.kbz1121.CarStore.service.StoreServcie;
import com.kbz1121.CarStore.system.ResponseFormat.BaseResponse;
import com.kbz1121.CarStore.system.ResponseFormat.ResponseResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@BaseResponse
@RequestMapping("/store")
public class StoreController {

    @Resource
    StoreServcie storeServcie;

    @RequestMapping("/getGoodsType")
    @ResponseBody
    public ResponseResult getUncheckStore(){
        return storeServcie.getGoodsType();
    }


}
