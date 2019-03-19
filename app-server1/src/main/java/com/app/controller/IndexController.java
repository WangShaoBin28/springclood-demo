package com.app.controller;

import com.app.feign.IndexFeign;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private IndexFeign indexFeign;


    @GetMapping("hello")
    public String hello() {
        return indexFeign.getIndex();
    }
}
