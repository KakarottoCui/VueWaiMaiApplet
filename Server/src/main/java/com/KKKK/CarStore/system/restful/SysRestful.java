package com.kbz1121.CarStore.system.restful;

import com.kbz1121.CarStore.system.RestTemp.RestTemp;

public class SysRestful {

    private static final String WX_GET_KEY = "https://api.weixin.qq.com/sns/jscode2session?grant_type=authorization_code";

    private static final String APP_SECRET = "33686d15edcf0fb040964015b0eb7a5c";

    private static final String APP_ID = "wx077e6a4b181b4ba7";

    private static RestTemp restTemp;

    static {
        createRestTemplate();
    }

    public static RestTemp getRestTemplate(){
        if( restTemp == null ) restTemp = new RestTemp();
        return restTemp;
    }

    private static void createRestTemplate() {
        if( restTemp == null ) restTemp = new RestTemp();
    }

    public String wxGetUserKey(String js_code){
        String url = WX_GET_KEY + "&appid="+APP_ID+"&secret="+APP_SECRET+"&js_code="+js_code;
        return restTemp.getForString(url);
    }

}
