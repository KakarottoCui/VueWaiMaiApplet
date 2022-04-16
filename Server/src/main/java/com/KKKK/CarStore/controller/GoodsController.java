package com.kbz1121.CarStore.controller;

import com.alibaba.fastjson.JSON;
import com.kbz1121.CarStore.entity.Goods;
import com.kbz1121.CarStore.entity.GoodsItem;
import com.kbz1121.CarStore.entity.User;
import com.kbz1121.CarStore.service.GoodsService;
import com.kbz1121.CarStore.service.PicService;
import com.kbz1121.CarStore.system.CommonFunction;
import com.kbz1121.CarStore.system.ResponseFormat.BaseResponse;
import com.kbz1121.CarStore.system.ResponseFormat.ResponseResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;

@Controller
@BaseResponse
@RequestMapping("/goods")
public class GoodsController {

    @Resource
    GoodsService goodsService;
    @Resource
    PicService picService;

    @RequestMapping("/saveGoods")
    @ResponseBody
    public ResponseResult saveGoods(@RequestParam(required = true) String data, HttpServletRequest request){
        Goods goods = JSON.parseObject(data,Goods.class);
        User user = CommonFunction.getUserInfo(request);
        return goodsService.saveGoods(goods,user);
    }

    @RequestMapping("/getGoods")
    @ResponseBody
    public ResponseResult getGoods(@RequestParam(required = true) String data, HttpServletRequest request){
        Goods goods = JSON.parseObject(data,Goods.class);
        User user = CommonFunction.getUserInfo(request);
        return goodsService.getGoods(goods);
    }

    @RequestMapping("/getGoodsByStore")
    @ResponseBody
    public ResponseResult getGoodsByStore(HttpServletRequest request){
        User user = CommonFunction.getUserInfo(request);
        return goodsService.getGoodsByStore(user);
    }

    @RequestMapping("/addGoodsItem")
    @ResponseBody
    public ResponseResult addGoodsToList(@RequestParam(required = true) String data, HttpServletRequest request){
        User user = CommonFunction.getUserInfo(request);
        GoodsItem goodsItem = JSON.parseObject(data,GoodsItem.class);
        return goodsService.setGoodsItem(goodsItem,user);
    }

    @RequestMapping("/getGoodsItem")
    @ResponseBody
    public ResponseResult getGoodsItem(HttpServletRequest request){
        User user = CommonFunction.getUserInfo(request);
        return goodsService.getGoodsItem(user);
    }

    @RequestMapping(value = "/getPicById")
    public void getPicById(Integer id, HttpServletRequest request, HttpServletResponse response) {

        String path = picService.getPicPathById(id);

    // 设置编码
        response.setCharacterEncoding("UTF-8");

        FileInputStream objInputStream = null;

        ServletOutputStream objOutStream = null;

        String[] imgArr = path.split("/");

        response.setContentType("image/jpeg");

        response.setHeader("Content-Disposition", "attachment;fileName="+imgArr[imgArr.length-1]);

        try{

            objInputStream= new FileInputStream(path);

            objOutStream = response.getOutputStream();

            int aRead = 0;

            while ((aRead = objInputStream.read()) != -1 & objInputStream != null) {

                objOutStream.write(aRead);

            }

            objOutStream.flush();

        } catch (Exception e) {

// TODO Auto-generated catch block

            e.printStackTrace();

        }finally {

            try {

                objOutStream.close();

            }catch (IOException e) {

            }
        }
    }
}
