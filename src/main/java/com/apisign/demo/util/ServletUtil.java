package com.apisign.demo.util;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * servlet工具
 *
 * @author liuhongdi
 */
public class ServletUtil {

    //获取request
    public static HttpServletRequest getRequest() {
        return getRequestAttributes().getRequest();
    }

    //获取response
    public static HttpServletResponse getResponse() {
        return getRequestAttributes().getResponse();
    }

    //获取session
    public static HttpSession getSession() {
        return getRequest().getSession();
    }

    //获取ServletRequestAttributes
    public static ServletRequestAttributes getRequestAttributes() {
        RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        return (ServletRequestAttributes) attributes;
    }

    /**
     * 将字符串渲染到客户端
     *
     * @param response 渲染对象
     * @param string   待渲染的字符串
     * @return null
     */
    public static String renderString(HttpServletResponse response, String string) {
        try {
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            response.getWriter().print(string);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}