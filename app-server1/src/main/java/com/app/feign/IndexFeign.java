package com.app.feign;

import com.app.hystrix.IndexHystrix;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Package com.app.feign
 * @ClassName IndexFeign
 * @Author shaobin.wang
 * @Date 2019/03/19 14:28
 * @Version 1.0
 * @Description:
 **/
@FeignClient(value = "${app-server2}",fallbackFactory = IndexHystrix.class)
public interface IndexFeign {

    @RequestMapping("/api/index")
    public String getIndex();
}
