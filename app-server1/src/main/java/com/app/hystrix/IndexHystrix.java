package com.app.hystrix;

import com.app.feign.IndexFeign;
import org.springframework.stereotype.Component;

/**
 * @Package com.app.hystrix
 * @ClassName IndexHystrix
 * @Author shaobin.wang
 * @Date 2019/03/19 14:30
 * @Version 1.0
 * @Description:
 **/
@Component
public class IndexHystrix implements IndexFeign {

    @Override
    public String getIndex() {
        return "调用失败！";
    }

}
