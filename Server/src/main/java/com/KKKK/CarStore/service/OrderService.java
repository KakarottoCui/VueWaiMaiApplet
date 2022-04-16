package com.kbz1121.CarStore.service;

import com.alibaba.fastjson.JSON;
import com.kbz1121.CarStore.dao.*;
import com.kbz1121.CarStore.entity.*;
import com.kbz1121.CarStore.system.ResponseFormat.ResponseCode;
import com.kbz1121.CarStore.system.ResponseFormat.ResponseResult;
import com.kbz1121.CarStore.system.utils.StringUtils;
import com.sun.org.apache.xpath.internal.operations.Or;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class OrderService {

    @Resource
    GoodsDao goodsDao;
    @Resource
    PicDao picDao;
    @Resource
    OrdersDao orderDao;
    @Resource
    GoodsItemDao goodsItemDao;
    @Resource
    ConmmentDao conmmentDao;
    @Resource
    UserDao userDao;

    public synchronized ResponseResult saveOrders(String data,  User user){
        try {
            List<GoodsItem> goodsItems = JSON.parseArray(data,GoodsItem.class);
            Map<Integer,List<GoodsItem>> storeGroup = new HashMap<Integer,List<GoodsItem>>();
            Map<Integer,Float> storeGroupPrice = new HashMap<Integer,Float>();
            for (GoodsItem goodsItem:goodsItems){
                Goods goods = goodsDao.queryById(goodsItem.getGoodsFk());
                if(goods ==null || goods.getValidFlag()!=1 || goods.getStat()!=3 || goods.getNum() < goodsItem.getNum()){
                    return new ResponseResult(ResponseCode.SUCCESS,goods.getName()+"已经无法购买，可能下架或数量不足。");
                }

                if(goodsItem.getId() == null){
                    goodsItem.setValidFlag(1);
                    goodsItem.setPrice(goods.getPrice());
                    goodsItem.setStoreFk(goods.getStoreFk());
                    goodsItem.setName(goods.getName());
                    goodsItem.setType(0);
                    goodsItem.setUserFk(user.getUid());
                    goodsItem.setGoodsFk(goods.getStoreFk());
                }

                Integer storeId = goods.getStoreFk();
                List<GoodsItem> items = new ArrayList<GoodsItem>();
                if ( storeGroup.get(storeId) != null){
                    items = storeGroup.get(storeId);
                    items.add(goodsItem);
                }else{
                    items.add(goodsItem);
                }
                Float price = 0f;
                if ( storeGroupPrice.get(storeId) != null){
                    price = storeGroupPrice.get(storeId) + goods.getPrice();
                }else{
                    price = goods.getPrice();
                    storeGroupPrice.put(storeId,price);
                }
                storeGroup.put(storeId,items);

            }

            for (Integer i : storeGroup.keySet()) {
                Integer storeId = i;
                List<GoodsItem> items = storeGroup.get(i);
                Orders order = new Orders();
                order.setStoreFk(storeId);
                order.setCreateTime(new Date());
                order.setStat(1);
                order.setAddress(user.getAddress());
                order.setPayTime(new Date());
                order.setUpdateTime(new Date());
                order.setUserFk(user.getUid());
                order.setPhone(user.getPhone());
                order.setPriceAll(storeGroupPrice.get(storeId));
                order.setUserName(user.getName());
                orderDao.insert(order);

                for (GoodsItem goodsItem:items){
                    if(goodsItem.getId() == null){
                        goodsItem.setOrderFk(order.getId());
                        goodsItemDao.insert(goodsItem);
                    }else {
                        goodsItem = goodsItemDao.queryById(goodsItem.getId());
                        goodsItem.setType(0);
                        goodsItem.setOrderFk(order.getId());
                        goodsItemDao.update(goodsItem);
                    }

                    Goods goods = goodsDao.queryById(goodsItem.getGoodsFk());
                    goods.setNum(goods.getNum() - goodsItem.getNum());
                    goodsDao.update(goods);
                }
            }
            return new ResponseResult(ResponseCode.SUCCESS,"下单成功");
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseResult(ResponseCode.SERVICE_ERROR,"下单失败");
        }
    }

    public ResponseResult setOrders(Integer id,Integer stat,User user){
        Orders orders = orderDao.queryById(id);
        orders.setStat(stat);
        orders.setUpdateTime(new Date());
        orderDao.update(orders);
        return new ResponseResult(ResponseCode.SUCCESS,"操作成功");
    }

    public ResponseResult getOrders(String data,  User user){

        Orders orders = JSON.parseObject(data,Orders.class);
        switch (user.getType()){
            case 1:
                orders.setUserFk(user.getUid());
                break;
            case 2:
                orders.setStoreFk(user.getStore());
                break;
        }
        List<Orders> ordersList = orderDao.queryAll(orders);
        if(ordersList.size()>0 && orders.getType()>0){
            for (Orders o:ordersList){
                GoodsItem goodsItem = new GoodsItem();
                goodsItem.setValidFlag(1);
                goodsItem.setType(0);
                goodsItem.setOrderFk(orders.getId());
                List<GoodsItem> goodsItems = goodsItemDao.queryAll(goodsItem);
                List<Goods> goodsList = new ArrayList<Goods>();
                for (GoodsItem g:goodsItems){
                    Goods search = new Goods();
                    search.setId(g.getGoodsFk());
                    List<Goods> dataList = goodsDao.queryGoods(search);
                    if(dataList.size()>0){
                        getPic(dataList.get(0));
                        dataList.get(0).setNum(g.getNum());
                        goodsList.add( dataList.get(0));
                    }
                }
                o.setGoods(goodsList);
            }
        }

        return new ResponseResult(ResponseCode.SUCCESS,"查询成功",ordersList);
    }

    public ResponseResult setComment(String data,  User user) {
        List<Goods> goods = JSON.parseArray(data, Goods.class);
        for(Goods g:goods){
            Conmment conmment = new Conmment();
            conmment.setName(g.getName());
            conmment.setValidFlag(1);
            conmment.setCreateTime(new Date());
            conmment.setContext(g.getMemo());
            conmment.setUserFk(user.getUid());
            conmment.setGoodsFk(g.getId());
            conmmentDao.insert(conmment);
        }
        try {
            if(goods.size() > 0){
                Integer id = goods.get(0).getType();
                Orders orders = orderDao.queryById(id);
                orders.setStat(4);
                orderDao.update(orders);
            }
        }catch (Exception e){

        }


        return new ResponseResult(ResponseCode.SUCCESS,"评论成功");
    }
    public ResponseResult getComment(Integer id) {

        Conmment conmment = new Conmment();
        conmment.setValidFlag(1);
        conmment.setGoodsFk(id);
        List<Conmment> conmments = conmmentDao.queryAll(conmment);

        for (Conmment c:conmments){
            User user = userDao.queryById(c.getUserFk());
            c.setUname(user.getName());
        }

        return new ResponseResult(ResponseCode.SUCCESS,"获取成功",conmments);
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
