package com.kbz1121.CarStore;

import com.kbz1121.CarStore.dao.UserDao;
import com.kbz1121.CarStore.dao.UserLoginDao;
import com.kbz1121.CarStore.system.Interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Resource
    UserDao userDao;
    @Resource
    UserLoginDao userLoginDao;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor(userDao, userLoginDao)).addPathPatterns("/**").excludePathPatterns(
                "/login/**");
//		 //特定URL拦截器
//		 registry.addInterceptor(new MsWhAppInterceptor()).addPathPatterns("/WhApp/**");//WH App请求
//
//		 registry.addInterceptor(new MsWhAppInterceptor()).addPathPatterns("/getCellTest/**"); //测试
//
//		 registry.addInterceptor(new MsWhAppInterceptor()).addPathPatterns("/getOrgPublicKey/**"); //获取ORG公钥
//
//		 registry.addInterceptor(new InfomationPaperMsInterceptor()).addPathPatterns("/InfomationPaperPcPage","/saveInformationPaperData","/getInformationPaperData","/uploadPaperPic","/getPaperPicByType/**","/toftyListPage"); //返乡登记url
//
//		 registry.addInterceptor(new AppOnlyMsInterceptor()).addPathPatterns("/toExcelHtmlReview/**","/toPDFReview/**","/getPdfFile/**"); //内网转发文件预览url
//
//		//不通过拦截器
//		registry.addInterceptor(new MsInterceptor()).addPathPatterns("/**").excludePathPatterns(
//				"/", //直接访问
//				"/qrcodeScannerRequset",//二维码请求
//				"/qc", //二维码请求，缩短版
//				"/mobilePptReview/**", //手机ppt预览
//				//"/mobileServiceRequsetFoward/**",//内网请求转发，弃用
//				//"/WhApp/**",//WH App请求
//				"/MyIVO-WH/**",//WH H5访问
//				"/MyIVO-Extension/**", //MyIVO-Extension H5访问
//				"/login", //管理页面登录
//				"/error", //报错页面
//				"/static/**", //静态资源
//				"/logout", //退出登录
//				"/getOrgPublicKey/**", //获取ORG的RSA公钥接口
//				"/getMobileLogin/**",  //移动端加密登录
//				"/paperCheckLogin/**", //调查问卷登录
//				"/examCheckLogin/**",  //移动考试登录
//				"/ajaxCreate.do", //MyIVO首页自动创建session登录
//				"/OrderFoodLogin", //隔离订餐登录
//				"/checkModelLogin/**",//扫码点检登录
//				"/mobileServiceRequestListFoward",//请求转发接口，限制url
//				"/passportCheck",
//				"/getPassPortPicBase64/**",
//				"/getFPassPortPicBase64/**",
//				"/getPassPort",
//				"/getPassPortReportData",
//				"/mainPage2",
//				"/getBaseData",//点检数据获取
//				"/saveInfoData",//保存点检数据
//				"/checkInfoData",//点检数据核查,同一版本,相同线别工位重复选择时增加提示
//				"/getMaterialData",//通过品名进行模糊查询，
//				"/saveBetweenData",//保存数据
//				"/spVerInfoValidQuery",//数据校验"
//				"/saveftyCheckPaperData",
//				"/getFtyCheckData",
//				"/getCommonPicByPath",
//				"/getFtyCheckDataByIdcard",
//				"/startDraw",
//				"/LuckDrawLogin",
//				"/getLuckDrawGiftList",
//				"/getOneGiftNumByDid",//元宵节抽奖获取奖品数量
//				"/getApartmentInfo"//床位信息
//				,"/getFtyChargeData",
//				"/saveFtyChargeData",
//				"/getFtyChargeReportData",
//				"/toChargeReportPage",
//				"/common/getQrCodePic"//通用二维码生成接口
//				,"/checkInfomationPaperUpdateState",//特殊员工检查
//				"/test/**",
//				"/saveInformationPaperData",
//				"/saveInfomationPaperPic",
//				"/quartz/**"//定时器相关接口
//				);
    }

}
