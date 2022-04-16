package com.kbz1121.CarStore.service;

import com.kbz1121.CarStore.dao.StoreDao;
import com.kbz1121.CarStore.dao.UserDao;
import com.kbz1121.CarStore.dao.UserLoginDao;
import com.kbz1121.CarStore.entity.Store;
import com.kbz1121.CarStore.entity.User;
import com.kbz1121.CarStore.entity.UserLogin;
import com.kbz1121.CarStore.system.ResponseFormat.ResponseCode;
import com.kbz1121.CarStore.system.ResponseFormat.ResponseResult;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class UserServcie {

    @Resource
    UserDao userDao;
    @Resource
    UserLoginDao userLoginDao;
    @Resource
    StoreDao storeDao;

    public ResponseResult login(User user){
        User testUser = new User();
        testUser.setName(user.getName());
        testUser.setValidFlag(1);
        List<User> users = userDao.queryAll(testUser);
        if(users.size()==0)
            return new ResponseResult(ResponseCode.SERVICE_ERROR,"用户不存在");
        User checkUser = users.get(0);
        if(checkUser.getName().equals(user.getName()) && checkUser.getPass().equals(user.getPass())){
            String uuid = UUID.randomUUID().toString()+System.currentTimeMillis();
            UserLogin userLogin = new UserLogin();
            userLogin.setToken(uuid);
            userLogin.setUserFk(checkUser.getUid());
            userLogin.setType(checkUser.getType());
            userLogin.setValidFlag(1);
            userLogin.setCreateTime(new Date());
            userLogin.setUpdateTime(new Date());
            userLoginDao.insert(userLogin);
            checkUser.setToken(uuid);
            return new ResponseResult(ResponseCode.SUCCESS,"登录成功",checkUser);
        }
        return new ResponseResult(ResponseCode.SERVICE_ERROR,"用户名或密码错误");
    }

    public ResponseResult regist(User user){
        User checkUser = new User();
        checkUser.setValidFlag(1);
        checkUser.setName(user.getName());
        List<User> users = userDao.queryAll(checkUser);
        if(users.size()>0)
            return new ResponseResult(ResponseCode.SERVICE_ERROR,"用户名重复，请重新命名");
        user.setValidFlag(1);
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
        if(user.getType() == 2){
            user.setStat(0);
        }
        userDao.insert(user);
        return new ResponseResult(ResponseCode.SUCCESS,"注册成功");
    }

    public ResponseResult getUncheckStore(){
        User checkUser = new User();
        checkUser.setValidFlag(1);
        checkUser.setType(2);
        return new ResponseResult(ResponseCode.SUCCESS,"查询成功",userDao.queryAll(checkUser));
    }

    public ResponseResult setUncheckStore(User user){
        user.setUpdateTime(new Date());
        userDao.update(user);
        Store store = new Store();
        store.setUserFk(user.getUid());
        store.setValidFlag(1);
        List<Store> stores = storeDao.queryAll(store);
        if(user.getStat() == 1){
            if(stores.size()>0){
                store = stores.get(0);
                store.setStat(1);
                storeDao.update(store);
            }else {
                store.setStat(1);
                store.setCreateTime(new Date());
                store.setName(user.getMemo());
                storeDao.insert(store);
            }
        }else {
            if(stores.size()>0){
                store = stores.get(0);
                store.setStat(0);
                storeDao.update(store);
            }
        }
        return new ResponseResult(ResponseCode.SUCCESS,"修改成功");
    }

}
