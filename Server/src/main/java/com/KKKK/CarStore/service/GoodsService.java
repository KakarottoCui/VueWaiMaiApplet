package com.kbz1121.CarStore.service;

import com.kbz1121.CarStore.dao.GoodsDao;
import com.kbz1121.CarStore.dao.GoodsItemDao;
import com.kbz1121.CarStore.dao.PicDao;
import com.kbz1121.CarStore.dao.StoreDao;
import com.kbz1121.CarStore.entity.*;
import com.kbz1121.CarStore.system.CommonFunction;
import com.kbz1121.CarStore.system.ResponseFormat.ResponseCode;
import com.kbz1121.CarStore.system.ResponseFormat.ResponseResult;
import com.kbz1121.CarStore.system.utils.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class GoodsService {

    @Resource
    GoodsDao goodsDao;
    @Resource
    StoreDao storeDao;
    @Resource
    PicDao picDao;
    @Resource
    GoodsItemDao goodsItemDao;

    private String picPath = "D:\\pic";

    public ResponseResult saveGoods(Goods goods,  User user){
        Goods hisGoods = new Goods();
        switch (goods.getType()){
            case 0:
                hisGoods = goodsDao.queryById(goods.getId());
                if(hisGoods!=null){
                    hisGoods.setValidFlag(0);
                    hisGoods.setUpdateTime(new Date());
                    goodsDao.update(hisGoods);
                }
                break;
            case 1:
                hisGoods = goodsDao.queryById(goods.getId());
                if(hisGoods!=null){
                    hisGoods.setStat(goods.getStat());
                    hisGoods.setUpdateTime(new Date());
                    goodsDao.update(hisGoods);
                }
                break;
            case 2:
                Store store = new Store();
                store.setValidFlag(1);
                store.setStat(1);
                store.setUserFk(user.getUid());
                List<Store> stores = storeDao.queryAll(store);
                if(stores.size() > 0){
                    goods.setStat(0);
                    goods.setValidFlag(1);
                    goods.setCreateTime(new Date());
                    goods.setUpdateTime(new Date());
                    goods.setStoreFk(stores.get(0).getSid());
                    goodsDao.insert(goods);
                    Integer id = goods.getId();
                    savePicWithBase64(goods.getPic1(),0,id);
                    savePicWithBase64(goods.getPic2(),1,id);
                    savePicWithBase64(goods.getPic3(),1,id);
                }else{
                    return new ResponseResult(ResponseCode.SERVICE_ERROR,"你的店铺未通过审核，待管理员审核后可添加商品");
                }
                break;
        }
        return new ResponseResult(ResponseCode.SUCCESS,"保存成功");

    }

    public boolean savePicWithBase64(String base64,Integer type,Integer goodsId){
        try {
            String path = CommonFunction.SaveBase64PicWithPath(base64,picPath, UUID.randomUUID().toString()+System.currentTimeMillis());
            if (StringUtils.isNotEmpty(path)){
                Pic pic = new Pic();
                pic.setGoodsFk(goodsId);
                pic.setValidFlag(1);
                pic.setType(type);
                pic.setPath(path);
                picDao.insert(pic);
                return true;
            }else {
                return false;
            }
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public ResponseResult getGoods(Goods goods){

        List<Goods> goodsList = goodsDao.queryGoods(goods);
        getPic(goodsList);
        return new ResponseResult(ResponseCode.SUCCESS,"查询成功",goodsList);
    }

    public ResponseResult getGoodsByStore( User user){

        Store store = new Store();
        store.setValidFlag(1);
        store.setUserFk(user.getUid());
        List<Store> stores = storeDao.queryAll(store);
        List<Goods> goodsList = new ArrayList<Goods>();
        if(stores.size() > 0){
            int sid = stores.get(0).getSid();
            Goods goods = new Goods();
            goods.setStoreFk(sid);
            goodsList = goodsDao.queryGoods(goods);
            getPic(goodsList);
        }
        return new ResponseResult(ResponseCode.SUCCESS,"查询成功",goodsList);
    }

    public ResponseResult setGoodsItem(GoodsItem goodsItem,User user){
        GoodsItem search = new GoodsItem();
        if(goodsItem.getId()!=null){
            search = goodsItemDao.queryById(goodsItem.getId());
            if (goodsItem.getChangeType() == 0){
                search.setValidFlag(0);
            }else {
                search.setNum(search.getNum() + goodsItem.getNum());
            }
            goodsItemDao.update(search);
        }else {
            search.setValidFlag(1);
            search.setType(goodsItem.getType());
            search.setGoodsFk(goodsItem.getGoodsFk());
            List<GoodsItem> goodsItems = goodsItemDao.queryAll(search);
            if(goodsItems.size()>0){
                GoodsItem his =  goodsItems.get(0);
                his.setNum(his.getNum() + goodsItem.getNum());
                goodsItemDao.update(his);
            }else{
                goodsItem.setType(goodsItem.getType());
                goodsItem.setValidFlag(1);
                goodsItemDao.insert(goodsItem);
            }
        }
        return new ResponseResult(ResponseCode.SUCCESS,"操作成功");
    }

    public ResponseResult getGoodsItem(User user){
        GoodsItem search = new GoodsItem();
        search.setValidFlag(1);
        search.setType(1);
        search.setUserFk(user.getUid());
        List<GoodsItem> goodsItems = goodsItemDao.queryAll(search);
        if(goodsItems.size()>0){
            for (GoodsItem goodsItem:goodsItems){
                Pic pic = new Pic();
                pic.setValidFlag(1);
                pic.setType(0);
                pic.setGoodsFk(goodsItem.getGoodsFk());
                List<Pic> pics = picDao.queryAll(pic);
                if(pics.size()>0){
                    goodsItem.setPid(pics.get(0).getId());
                }
            }
        }
        return new ResponseResult(ResponseCode.SUCCESS,"查询成功",goodsItems);
    }

    public void getPic(List<Goods> goodsList){
        for (Goods goods :goodsList){
            getPic(goods);
        }
    }

    public void getPic(Goods goods){
        Pic pic = new Pic();
        pic.setValidFlag(1);
        pic.setType(0);
        pic.setGoodsFk(goods.getId());
        List<Pic> pics = picDao.queryAll(pic);
        if(pics.size()>0){
            goods.setPic1(Integer.toString(pics.get(0).getId()));
        }
        pic.setType(1);
        pics = picDao.queryAll(pic);
        if(pics.size()==2){
            goods.setPic2(Integer.toString(pics.get(0).getId()));
            goods.setPic3(Integer.toString(pics.get(1).getId()));
        }
    }

}
