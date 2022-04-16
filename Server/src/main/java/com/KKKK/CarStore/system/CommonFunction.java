package com.kbz1121.CarStore.system;


import com.kbz1121.CarStore.entity.User;
import org.apache.commons.codec.binary.Base64;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonFunction {

    public static User getUserInfo(HttpServletRequest request){
        return  (User)request.getSession().getAttribute("userInfo");
    }

    public static String SaveBase64PicWithPath(String imgBase64, String path, String fileName) {

        try{

            String[] dataInfo = imgBase64.split(",");
            String codeString = dataInfo[1];
            String typeString = dataInfo[0];
            String type = getSubUtilSimple(typeString, "data:image/(.*?);base64");
            fileName = fileName.length() > 0 ? fileName : UUID.randomUUID().toString() + new Date().getTime();
            checkPath(path);
            path= path + "/" + fileName + "." + type;
            savePic(path, Base64ToByte(codeString));
            return fileName + "." + type;
        } catch (Exception e) {

            e.printStackTrace();

            return null;

        }
    }

    // 正则截取特定字符之间值

    public static String getSubUtilSimple(String soap, String rgex) {

        Pattern pattern = Pattern.compile(rgex);// 匹配的模式

        Matcher m = pattern.matcher(soap);

        while(m.find()) {

            return m.group(1);

        }

        return "";

    }




    public static void checkPath(String path) {

        File folder = new File(path);

// 文件夹路径不存在

        if (!folder.exists() && !folder.isDirectory()) {

            folder.mkdirs();

        }

    }




    public static boolean savePic(String path, byte[] imageByte) {

        OutputStream out;

        try{

            out = new FileOutputStream(path);

            out.write(imageByte);

            out.flush();

            out.close();

            return true;

        } catch (Exception e) {

// TODO Auto-generated catch block

            e.printStackTrace();

            return false;

        }

    }




    public static byte[] Base64ToByte(String imgBase64) {

        Base64 decoder = new Base64();

        byte[] imageByte = decoder.decode(imgBase64.replace("\r\n", ""));

        for (int i = 0; i < imageByte.length; ++i) {

            if (imageByte[i] < 0) {// 调整异常数据

                imageByte[i] += 256;

            }

        }

        return imageByte;

    }

}
