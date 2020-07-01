package com.apisign.demo.util;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;
/**
*@author:liuhongdi
*@date:2020/7/1 下午4:17
*@description:访问sing类型型edis的
*/
@Service
public class RedisStringUtil {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    //设置一个值
    //参数: hash名字，hash中的key,hash中key对应的value
    public boolean setStringValue(String stringKey,Object stringValue,Long seconds) {

        boolean flag = false;
        try{
            //System.out.println("=============================:hashName:"+hashName);
            //redisTemplate.opsForHash().put(hashName, hashKey, hashValue);
            redisTemplate.opsForValue().set(stringKey,stringValue,seconds, TimeUnit.SECONDS);
            //redisTemplate.opsForValue().set(stringKey,stringValue);
            flag = true;
        } catch (Exception e){
            e.printStackTrace();
        }
        return flag;
    }

    //判断一个hash中的某个key是否存在
    //参数:hash名字，hash中的key
    public boolean hasStringkey(String stringkey) {
        //boolean bool = redisTemplate.opsForHash().hasKey(hashName, hashkey);
        boolean bool = redisTemplate.hasKey(stringkey);
        return bool;
    }

    //得到某个hash中的某个key的值
    //参数:hash名字，hash中的key
    public Object getStringValue(String stringkey) {
        //boolean flag = false;

        Object value=redisTemplate.opsForValue().get(stringkey);

        return value;
    }

    //从hash中删除一个key
    public boolean deleteStringKey(String stringkey) {

        boolean isDel = redisTemplate.delete(stringkey);
        //return flag;
        return isDel;
    }

}