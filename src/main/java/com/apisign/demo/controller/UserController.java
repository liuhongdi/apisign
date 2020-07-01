package com.apisign.demo.controller;

import com.apisign.demo.util.ResultUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    /*
    *@author:liuhongdi
    *@date:2020/7/1 下午3:53
    *@description:登录
     * @param username 用户名
     * @param passward  密码
    *@return:json
    */
    @PostMapping(value = "/login")
    public ResultUtil login(String username, String passward) {

        return ResultUtil.success(null);
    }
}