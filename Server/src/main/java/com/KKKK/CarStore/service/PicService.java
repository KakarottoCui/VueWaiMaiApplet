package com.kbz1121.CarStore.service;

import com.kbz1121.CarStore.dao.GoodsDao;
import com.kbz1121.CarStore.dao.PicDao;
import com.kbz1121.CarStore.dao.StoreDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class PicService {

    @Resource
    GoodsDao goodsDao;
    @Resource
    StoreDao storeDao;
    @Resource
    PicDao picDao;

    private String picPath = "D:\\pic";

    public String getPicPathById(Integer id){
         return picPath + "\\" +picDao.queryById(id).getPath();
    }


}
