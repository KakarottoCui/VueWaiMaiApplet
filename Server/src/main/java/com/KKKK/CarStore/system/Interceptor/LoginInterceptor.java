package com.kbz1121.CarStore.system.Interceptor;

import com.kbz1121.CarStore.dao.UserDao;
import com.kbz1121.CarStore.dao.UserLoginDao;
import com.kbz1121.CarStore.entity.User;
import com.kbz1121.CarStore.entity.UserLogin;
import com.kbz1121.CarStore.system.utils.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginInterceptor implements HandlerInterceptor{

    public UserDao userDao;
    public UserLoginDao userLoginDao;

    public LoginInterceptor(UserDao userDao,UserLoginDao userLoginDao){
        this.userDao = userDao;
        this.userLoginDao = userLoginDao;
    }
	@Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
		response.setHeader("Content-type", "text/html;charset=UTF-8");
        HttpSession session = request.getSession();
        String token = request.getParameter("token");
        User user = (User) session.getAttribute("userInfo");
        if(user != null && user.getToken().equals(token)){
            return true;
        }
        if(StringUtils.isNotEmpty(token)){
            UserLogin userLogin = userLoginDao.queryById(token);
            if(userLogin != null){
                user = userDao.queryById(userLogin.getUserFk());
                if(userLogin.getType() == 2){
                    user = userDao.queryByIdWithStore(userLogin.getUserFk());
                }else{
                    user = userDao.queryById(userLogin.getUserFk());
                }
                user.setToken(token);
                session.setAttribute("userInfo",user);
                return true;
            }
        }
        session.removeAttribute("userInfo");
        return true;
    }
	
	@Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object o, ModelAndView modelAndView) throws Exception {
 
        
    }
	
	@Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object o, Exception e) throws Exception {
 
        
    }
}
