package com.kbz1121.CarStore.controller;

import com.kbz1121.CarStore.entity.User;
import com.kbz1121.CarStore.service.OrderService;
import com.kbz1121.CarStore.system.CommonFunction;
import com.kbz1121.CarStore.system.ResponseFormat.BaseResponse;
import com.kbz1121.CarStore.system.ResponseFormat.ResponseResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
@BaseResponse
@RequestMapping("/order")
public class OrderController {

    @Resource
    OrderService orderService;

    @RequestMapping("/saveOrders")
    @ResponseBody
    public ResponseResult saveOrders(@RequestParam(required = true) String data, HttpServletRequest request){
        User user = CommonFunction.getUserInfo(request);
        return orderService.saveOrders(data,user);
    }

    @RequestMapping("/getOrder")
    @ResponseBody
    public ResponseResult getOrder(@RequestParam(required = true) String data, HttpServletRequest request){
        User user = CommonFunction.getUserInfo(request);
        return orderService.getOrders(data,user);
    }

    @RequestMapping("/setOrder")
    @ResponseBody
    public ResponseResult setOrder(@RequestParam(required = true) Integer id,@RequestParam(required = true) Integer stat,HttpServletRequest request){
        User user = CommonFunction.getUserInfo(request);
        return orderService.setOrders(id,stat,user);
    }

    @RequestMapping("/setCommnt")
    @ResponseBody
    public ResponseResult setCommnt(@RequestParam(required = true) String data,HttpServletRequest request){
        User user = CommonFunction.getUserInfo(request);
        return orderService.setComment(data,user);
    }

    @RequestMapping("/getCommnt")
    @ResponseBody
    public ResponseResult getCommnt(@RequestParam(required = true) Integer id,HttpServletRequest request){
        return orderService.getComment(id);
    }
}
