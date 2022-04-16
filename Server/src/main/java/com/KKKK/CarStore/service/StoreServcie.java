package com.kbz1121.CarStore.service;

import com.kbz1121.CarStore.dao.GoodsTypeDao;
import com.kbz1121.CarStore.dao.StoreDao;
import com.kbz1121.CarStore.entity.GoodsType;
import com.kbz1121.CarStore.system.ResponseFormat.ResponseCode;
import com.kbz1121.CarStore.system.ResponseFormat.ResponseResult;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class StoreServcie {

    @Resource
    StoreDao storeDao;
    @Resource
    GoodsTypeDao goodsTypeDao;

    public ResponseResult getGoodsType(){

        GoodsType goodsType = new GoodsType();
        goodsType.setValidFlag(1);

        return new ResponseResult(ResponseCode.SUCCESS,"获取成功",goodsTypeDao.queryAll(goodsType));

    }

}
