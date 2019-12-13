package cn.chennan.imagesystem.util;

import com.alibaba.fastjson.JSON;

/**
 * @author ChenNan
 * @date 2019-12-07 下午2:23
 **/
public class JSONUtil {

    public static final String COOKIE_NAME_TOKEN = "token";

    public static <T> String beanToString(T t){
        return JSON.toJSONString(t);
    }

    public static <T> T stringToBean(String json, Class<T> clazz){
        return (T) JSON.parseObject(json, clazz);
    }
}
