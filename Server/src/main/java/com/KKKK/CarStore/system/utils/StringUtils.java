package com.kbz1121.CarStore.system.utils;

import org.apache.commons.codec.binary.Base64;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {

    public static String ENCODING = "UTF-8";

    public static boolean isNotEmpty(String s) {
        return s != null && !s.trim().equals("");
    }

    public static boolean isEmpty(String s) {
        return s == null || s.trim().equals("");
    }

    public static boolean isNotNull(String s) {
        return s != null;
    }

    public static boolean isEqual(String s, Object o) {
        return s != null && s.equals(o);
    }

    public static String ArraytoString(String s[]) {
        String result = "";
        if (null != s && s.length > 0) {
            for (int i = 0; i < s.length; i++) {
                result += s[i] + ",";
            }
        }
        if (result.length() > 1) {
            return result.substring(0, result.length() - 1);
        } else {
            return "";
        }
    }

    public static String convert(String s) {
        if (s == null)
            return "";
        return s.replaceAll("&", "&amp;").replace("'", "&apos;").replace("\"",
                "&quot;").replace("<", "&lt;").replace(">", "&gt;");
    }

    public static String string2MD5(String inStr) {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
            return "";
        }
        char[] charArray = inStr.toCharArray();
        byte[] byteArray = new byte[charArray.length];

        for (int i = 0; i < charArray.length; i++)
            byteArray[i] = (byte) charArray[i];
        byte[] md5Bytes = md5.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16)
                hexValue.append("0");
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();

    }

    /**
     * Decode a string using Base64 encoding.
     *
     * @param str
     * @return String
     */
    public static String decodeString(String str) {
        if (str == null)
            return null;
        try {
            byte[] b = Base64.decodeBase64(str.getBytes(ENCODING));
            return new String(b, ENCODING);
        } catch (Exception e) {
            return null;
        }
    }

    // 全角转半角
    public static String ToDBC(String input) {
        char c[] = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == '\u3000') {
                c[i] = ' ';
            } else if (c[i] > '\uFF00' && c[i] < '\uFF5F') {
                c[i] = (char) (c[i] - 65248);

            }
        }
        String returnString = new String(c);
        return returnString;
    }

    //去除空格、回车、换行符、制表符
    public static String replaceBlank(String str) {
        String dest = "";
        if (str != null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }

    /**
     * @param string
     * @return
     */
    public static String swapFirstLetterCase(String string) {
        StringBuffer sbuf = new StringBuffer(string);
        sbuf.deleteCharAt(0);
        if (Character.isLowerCase(string.substring(0, 1).toCharArray()[0])) {
            sbuf.insert(0, string.substring(0, 1).toUpperCase());
        } else {
            sbuf.insert(0, string.substring(0, 1).toLowerCase());
        }
        return sbuf.toString();
    }

    static public String createGUID() {
        // -----------------------------------------------------------------------------------------------------------------
        Random objRandom = new Random();
        String strEncode = "01234567890ABCDEFGH";
        String strGUID = "";
        int nPos = 0;
        char c = '\0';
        // -----------------------------------------------------------------------------------------------------------------
        for (int i = 0; i < 36; i++) {
            if (i == 8 || i == 13 || i == 18 || i == 23) {
                c = '-';
            } else {
                nPos = objRandom.nextInt();
                if (nPos < 0)
                    nPos *= -1;
                nPos = nPos % strEncode.length();
                c = strEncode.charAt(nPos);
            } // if( i
            strGUID += c;
        } // for i
        // -----------------------------------------------------------------------------------------------------------------
        return strGUID;
    }

    /**
     * 将字符串数组转化为in条件用的字符串
     *
     * @param str
     * @return
     */
    public static String getInClause(String[] str) {
        StringBuffer result = new StringBuffer();

        for (int i = 0; i < str.length; i++) {
            if (i > 0) {
                result.append(",");
            }
            result.append("'" + str[i] + "'");
        }
        return result.toString();
    }

    public static String getInClause(List<String> list) {
        String[] arr = (String[]) list.toArray(new String[list.size()]);
        return getInClause(arr);
    }

    /**
     * <p>
     * Checks if a CharSequence is whitespace, empty ("") or null.
     * </p>
     *
     * <pre>
     * StringUtils.isBlank(null)      = true
     * StringUtils.isBlank("")        = true
     * StringUtils.isBlank(" ")       = true
     * StringUtils.isBlank("bob")     = false
     * StringUtils.isBlank("  bob  ") = false
     * </pre>
     *
     * @param cs the CharSequence to check, may be null
     * @return {@code true} if the CharSequence is null, empty or whitespace
     * @since 2.0
     * @since 3.0 Changed signature from isBlank(String) to
     * isBlank(CharSequence)
     */
    public static boolean isBlank(CharSequence cs) {
        int strLen;
        if (cs == null || (strLen = cs.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (Character.isWhitespace(cs.charAt(i)) == false) {
                return false;
            }
        }
        return true;
    }

    /**
     * <p>
     * Checks if a CharSequence is not empty (""), not null and not whitespace
     * only.
     * </p>
     *
     * <pre>
     * StringUtils.isNotBlank(null)      = false
     * StringUtils.isNotBlank("")        = false
     * StringUtils.isNotBlank(" ")       = false
     * StringUtils.isNotBlank("bob")     = true
     * StringUtils.isNotBlank("  bob  ") = true
     * </pre>
     *
     * @param cs the CharSequence to check, may be null
     * @return {@code true} if the CharSequence is not empty and not null and
     * not whitespace
     * @since 2.0
     * @since 3.0 Changed signature from isNotBlank(String) to
     * isNotBlank(CharSequence)
     */
    public static boolean isNotBlank(CharSequence cs) {
        return !StringUtils.isBlank(cs);
    }

    /**
     *
     */
    public static String extract(String regex, String base) {
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(base);
        if (m.find()) {
            return m.group();
        }
        return "";
    }

    /**
     * <p>
     * Removes a substring only if it is at the end of a source string,
     * otherwise returns the source string.
     * </p>
     *
     * <p>
     * A {@code null} source string will return {@code null}. An empty ("")
     * source string will return the empty string. A {@code null} search string
     * will return the source string.
     * </p>
     *
     * <pre>
     * StringUtils.removeEnd(null, *)      = null
     * StringUtils.removeEnd("", *)        = ""
     * StringUtils.removeEnd(*, null)      = *
     * StringUtils.removeEnd("www.domain.com", ".com.")  = "www.domain.com"
     * StringUtils.removeEnd("www.domain.com", ".com")   = "www.domain"
     * StringUtils.removeEnd("www.domain.com", "domain") = "www.domain.com"
     * StringUtils.removeEnd("abc", "")    = "abc"
     * </pre>
     *
     * @param str    the source String to search, may be null
     * @param remove the String to search for and remove, may be null
     * @return the substring with the string removed if found, {@code null} if
     * null String input
     * @since 2.1
     */
    public static String removeEnd(String str, String remove) {
        if (isEmpty(str) || isEmpty(remove)) {
            return str;
        }
        if (str.endsWith(remove)) {
            return str.substring(0, str.length() - remove.length());
        }
        return str;
    }

    // Empty checks
    // -----------------------------------------------------------------------

    /**
     * <p>
     * Checks if a CharSequence is empty ("") or null.
     * </p>
     *
     * <pre>
     * StringUtils.isEmpty(null)      = true
     * StringUtils.isEmpty("")        = true
     * StringUtils.isEmpty(" ")       = false
     * StringUtils.isEmpty("bob")     = false
     * StringUtils.isEmpty("  bob  ") = false
     * </pre>
     *
     * <p>
     * NOTE: This method changed in Lang version 2.0. It no longer trims the
     * CharSequence. That functionality is available in isBlank().
     * </p>
     *
     * @param cs the CharSequence to check, may be null
     * @return {@code true} if the CharSequence is empty or null
     * @since 3.0 Changed signature from isEmpty(String) to
     * isEmpty(CharSequence)
     */
    public static boolean isEmpty(CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

    /**
     * <p>
     * Checks if a CharSequence is not empty ("") and not null.
     * </p>
     *
     * <pre>
     * StringUtils.isNotEmpty(null)      = false
     * StringUtils.isNotEmpty("")        = false
     * StringUtils.isNotEmpty(" ")       = true
     * StringUtils.isNotEmpty("bob")     = true
     * StringUtils.isNotEmpty("  bob  ") = true
     * </pre>
     *
     * @param cs the CharSequence to check, may be null
     * @return {@code true} if the CharSequence is not empty and not null
     * @since 3.0 Changed signature from isNotEmpty(String) to
     * isNotEmpty(CharSequence)
     */
    public static boolean isNotEmpty(CharSequence cs) {
        return !StringUtils.isEmpty(cs);
    }

    /**
     * 字符串转换成为16进制
     *
     * @param str
     * @return
     */
    public static String str2HexStr(String str) {
        char[] chars = "0123456789ABCDEF".toCharArray();
        StringBuilder sb = new StringBuilder("");
        byte[] bs = str.getBytes();
        int bit;
        for (int i = 0; i < bs.length; i++) {
            bit = (bs[i] & 0x0f0) >> 4;
            sb.append(chars[bit]);
            bit = bs[i] & 0x0f;
            sb.append(chars[bit]);
        }
        return sb.toString().trim();
    }

    /**
     * 16进制直接转换成为字符串(无需Unicode解码)
     *
     * @param hexStr
     * @return
     */
    public static String hexStr2Str(String hexStr) {
        String str = "0123456789ABCDEF";
        char[] hexs = hexStr.toCharArray();
        byte[] bytes = new byte[hexStr.length() / 2];
        int n;
        for (int i = 0; i < bytes.length; i++) {
            n = str.indexOf(hexs[2 * i]) * 16;
            n += str.indexOf(hexs[2 * i + 1]);
            bytes[i] = (byte) (n & 0xff);
        }
        return new String(bytes);
    }

    // Base64编码
    public static String encodeBase64(String str) {
        byte[] b = null;
        try {
            b = str.getBytes("utf-8");
        } catch (Exception e) {

        }
        if (b != null) {
            str = Base64.encodeBase64String(b);
        }
        return str;

    }

    // Base64解码
    public static String decodeBase64(String str) {
        str = new String(Base64.decodeBase64(str));
        return str;
    }

    public static String encodeStringURLSafe(String str) {
        if (str == null)
            return null;
        try {
            byte[] b = Base64.encodeBase64URLSafe(str.getBytes(ENCODING));
            return new String(b, ENCODING);
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }



    public static String trimStr(String str) {
        if (str == null) return "";
        //将制表符替换成空格
        str = str.replaceAll("\t", " ");
        str = str.replaceAll("\\\\", "\\\\\\\\");
        str = str.replaceAll("(\r\n|\r|\n|\n\r)", " ");
        return str;
    }

    public static String commonValueReplace(String str) {
        Map<String, String> convertMap = new HashMap<String, String>() {
            {
                put("single", "单选");
                put("mulity", "多选");
                put("judge", "判断");
                put("shortAnswer", "简答");
                put("completion", "填空");
                put("photoLib", "图片试题");
                put("cloze", "完形填空");
                put("reading", "阅读理解");
                put("9999", "only术科");
                put("true", "是");
                put("false", "否");
            }
        };
        String res = convertMap.get(str);
        return res == null ? str : res;
    }
}
