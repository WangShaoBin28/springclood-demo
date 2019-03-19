package com.app.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Package com.app.controller
 * @ClassName IndexController
 * @Author shaobin.wang
 * @Date 2019/03/19 14:23
 * @Version 1.0
 * @Description:
 **/
@RestController
@RequestMapping("/api/")
public class IndexController {

    @GetMapping("index")
    public String hello() {

        return "调用成功！";
    }
}
