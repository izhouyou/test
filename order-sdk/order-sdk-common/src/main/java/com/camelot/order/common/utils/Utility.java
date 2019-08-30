package com.camelot.order.common.utils;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;


public class Utility {


    /**
     * 判断字符串是否不为空
     *
     * @param str
     * @return
     */
    public static boolean isNotEmpty(String str) {
        return str != null && str.trim().length() > 0;
    }

    /**
     * 判断字符串是否为空
     *
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        return str == null || str.trim().length() == 0;
    }

    /**
     * 判断List是否不为空
     *
     * @param list
     * @param <T>
     * @return
     */
    public static <T> boolean isNotEmpty(List<T> list) {
        return list != null && list.size() > 0;
    }

    /**
     * 判断集合是否为空
     *
     * @param list
     * @param <T>
     * @return
     */
    public static <T> boolean isEmpty(List<T> list) {
        return list == null || list.size() == 0;
    }

    /**
     * 判断数组是否不为空
     *
     * @param objArray
     * @return
     */
    public static boolean isNotEmpty(Object... objArray) {
        return objArray != null && objArray.length > 0;
    }

    /**
     * 判断对象是否不为空
     *
     * @param object
     * @return
     */
    public static boolean isNotEmpty(Object object) {
        return object != null;
    }

    /**
     * 判断对象是否为空
     *
     * @param object
     * @return
     */
    public static boolean isEmpty(Object object) {
        if (object == null || object.getClass() == Object.class || object instanceof Collection && ((Collection) object).isEmpty() || object instanceof Map && ((Map) object).isEmpty()
                || object.getClass().isArray() && Array.getLength(object) == 0 || object instanceof String && StringUtils.isBlank((String) object)) {
            return true;
        }
        return false;
    }
    
    /**
     * 对可能为NUll的值转为String,如果为NULL则转为空
     * 
     */
    public static String objectToString(Object object){
    	if(object == null){
    		return "";
    	}else{
    		return object.toString();
    	}
    }
    
    /**
     * 字符串转Integer
     * 
     */
    public static Integer stringToInteger(String str){
    	if(isNotEmpty(str)){
    		try{
    			return Integer.parseInt(str);
    		}catch(Exception e){
    			return null;
    		}
    		
    	}else{
    		return null;
    	}
    }
    
    /**
     * 字符串转Long
     * 
     */
    public static Long stringToLong(String str){
    	if(isNotEmpty(str)){
    		try{
    			return Long.parseLong(str);
    		}catch(Exception e){
    			return null;
    		}
    		
    	}else{
    		return null;
    	}
    }
    
    /**
     * <p>Title: bigDecimalToString</p>
     * <p>Description: BigDecimal转String，保留两位小数</p>
     * @return
     */
    public static String bigDecimalToString(BigDecimal amount) {
    	if(isNotEmpty(amount)) {
    		DecimalFormat df = new DecimalFormat("#,##0.00");
    		return df.format(amount);
    	}
    	return "0.00";
    }
    
    /**
     * <p>Title: stringToSet</p>
     * <p>Description: 将字符串转化为HashSet</p>
     * @param str
     * @return
     */
    public static List<Integer> stringToList(String str) {
    	if(Utility.isNotEmpty(str)) {
    		List<Integer> result = new ArrayList<>();
    		List<String> asList = Arrays.asList(str.split(","));
    		for (String string : asList) {
    			result.add(Integer.parseInt(string));
			}
    		return result;
    	}
    	return null;
    }
    
    /**
     * <p>Title: getDigest</p>
     * <p>Description: 采用MD5摘要算法处理信息,获取MD5签名</p>
     * @param map 参数集合
     * @param key 秘钥
     * @param charset 字符集
     * @return 签名后字符串
     * @author zhouyou
     * @date 2019年7月5日
     */
    public static String getDigest(TreeMap<String, String> map, String key, String charset){
        StringBuilder sb = new StringBuilder();
        for (Map.Entry entry : map.entrySet()) {
            sb = sb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        }
        sb.insert( 0 , key).deleteCharAt(sb.length()-1).append(key); 
        System.out.println("拼接后的字符："+sb.toString());
        String sign = DigestUtils.md5Hex(getContentBytes(sb.toString(), charset));
        System.out.println("加密后的签名："+sign);
        return sign;
    }
    
    /**
     * <p>Title: getContentBytes</p>
     * <p>Description: 将字符流转换为字节码</p>
     * @param content 字符流
     * @param charset 字符集
     * @return 字节码
     * @author zhouyou
     * @date 2019年7月5日
     */
    private static byte[] getContentBytes(String content, String charset) {
        if (charset == null || "".equals(charset)) {
            return content.getBytes();
        }
        try {
            return content.getBytes(charset);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("MD5签名过程中出现错误,指定的编码集不对,您目前指定的编码集是:" + charset);
        }
    }
    
//    public static void main(String[] args) {
//    	//treeMap默认是key升序排序 ,如果需要降序,可以使用Comparator中的compare方法
//        TreeMap<String,String> map = new TreeMap<String, String>();
//        map.put("timestamp", String.valueOf(System.currentTimeMillis()/1000));
//        map.put("appid", "RMS");
////      map.put("sn", "");
////      map.put("frame_id", "LANDL21B6K0104364");
//        map.put("start_time", String.valueOf(System.currentTimeMillis()/1000-60*60*24*30));
//        map.put("end_time", String.valueOf(System.currentTimeMillis()/1000));
//        System.out.println(map.toString());
//        // MD5签名
//        String sign = Utility.getDigest(map, "A3XhTUDfQ2FrjwB6f4wTEKhsAQ7paZ","utf-8");
//	}
    
}
