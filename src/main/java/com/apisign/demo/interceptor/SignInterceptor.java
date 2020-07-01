package com.apisign.demo.interceptor;

import com.alibaba.fastjson.JSON;
import com.apisign.demo.constant.Constants;
import com.apisign.demo.constant.ResponseCode;
import com.apisign.demo.pojo.HeadRequest;
import com.apisign.demo.util.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/*
*
* interceptor for api sign
*
* */
@Component
public class SignInterceptor implements HandlerInterceptor {
    private static final String SIGN_KEY = "apisign_";
    private static final Logger logger = LogManager.getLogger("bussniesslog");
    @Resource
    private RedisStringUtil redisStringUtil;

    /*
    *@author:liuhongdi
    *@date:2020/7/1 下午4:00
    *@description:
     * @param request：请求对象
     * @param response：响应对象
     * @param handler：处理对象：controller中的信息   *
     * *@return:true表示正常,false表示被拦截
    */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //依次检查各变量是否存在？
        String appId = request.getHeader("appId");
        if (StringUtils.isBlank(appId)) {
            ServletUtil.renderString(response, JSON.toJSONString(ResultUtil.error(ResponseCode.SIGN_NO_APPID)));
            return false;
        }
        String timestampStr = request.getHeader("timestamp");
        if (StringUtils.isBlank(timestampStr)) {
            ServletUtil.renderString(response, JSON.toJSONString(ResultUtil.error(ResponseCode.SIGN_NO_TIMESTAMP)));
            return false;
        }
        String sign = request.getHeader("sign");
        if (StringUtils.isBlank(sign)) {
            ServletUtil.renderString(response, JSON.toJSONString(ResultUtil.error(ResponseCode.SIGN_NO_SIGN)));
            return false;
        }
        String nonce = request.getHeader("nonce");
        if (StringUtils.isBlank(nonce)) {
            ServletUtil.renderString(response, JSON.toJSONString(ResultUtil.error(ResponseCode.SIGN_NO_NONCE)));
            return false;
        }
        //得到正确的sign供检验用
        String origin = appId + Constants.APP_SECRET + timestampStr + nonce + Constants.APP_API_VERSION;
        String signEcrypt = MD5Util.md5(origin);
        long timestamp = 0;
        try {
            timestamp = Long.parseLong(timestampStr);
        } catch (Exception e) {
            logger.error("发生异常",e);
        }
        //前端传过来的时间戳与服务器当前时间戳差值大于180，则当前请求的timestamp无效
        if (Math.abs(timestamp - System.currentTimeMillis() / 1000) > 180) {
            ServletUtil.renderString(response, JSON.toJSONString(ResultUtil.error(ResponseCode.SIGN_TIMESTAMP_INVALID)));
            return false;
        }
        //判断redis中的nonce，确认当前请求是否为重复请求，控制API接口幂等性
        boolean nonceExists = redisStringUtil.hasStringkey(SIGN_KEY+timestampStr+nonce);
        if (nonceExists) {
            ServletUtil.renderString(response, JSON.toJSONString(ResultUtil.error(ResponseCode.SIGN_DUPLICATION)));
            return false;
        }
        //通过后台MD5重新签名校验与前端签名sign值比对，确认当前请求数据是否被篡改
        if (!(sign.equalsIgnoreCase(signEcrypt))) {
            ServletUtil.renderString(response, JSON.toJSONString(ResultUtil.error(ResponseCode.SIGN_VERIFY_FAIL)));
            return false;
        }
        //将nonce存进redis
        redisStringUtil.setStringValue(SIGN_KEY+timestampStr+nonce, nonce, 180L);
        //sign校验无问题,放行
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}