package com.app.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * @Package com.config
 * @ClassName AppZuulFilter
 * @Author shaobin.wang
 * @Date 2019/03/20 11:18
 * @Version 1.0
 * @Description:
 **/
@Slf4j
@Component
public class AppZuulFilter extends ZuulFilter {

    /**
     * 路径白名单
     */
    @Value("${white-url}}")
    private String whiteUrl;

    @Override
    public String filterType() {
        //枚举值：pre, routing, post, error
        //PRE: 该类型的filters在Request routing到源web-service之前执行。用来实现Authentication、选择源服务地址等
        //ROUTING：该类型的filters用于把Request routing到源web-service，源web-service是实现业务逻辑的服务。这里使用HttpClient请求web-service。
        //POST：该类型的filters在ROUTING返回Response后执行。用来实现对Response结果进行修改，收集统计数据以及把Response传输会客户端。
        //ERROR：上面三个过程中任何一个出现错误都交由ERROR类型的filters进行处理。
        //主要关注 pre、post和error。分别代表前置过滤，后置过滤和异常过滤。
        //如果你的filter是pre的，像上一篇那种，就是指请求先进入pre的filter类，你可以进行一些权限认证，日志记录，或者额外给Request增加一些属性供后续的filter使用。pre会优先按照order从小到大执行，然后再去执行请求转发到业务服务。
        //再说post，如果type为post，那么就会执行完被路由的业务服务后，再进入post的filter，在post的filter里，一般做一些日志记录，或者额外增加response属性什么的。
        //最后error，如果在上面的任何一个地方出现了异常，就会进入到type为error的filter中。
        return "pre";
    }

    @Override
    public int filterOrder() {
        //优先级， 0是最高优先级即最先执行
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        //写逻辑，是否需要执行过滤。true会执行run函数，false不执行run函数
        return true;
    }

    @Override
    public Object run() throws ZuulException {

        log.debug("执行了过滤器");

        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        log.info(String.format("%s %s", request.getMethod(), request.getRequestURL().toString()));
        //获取token
        String accessToken = request.getHeader("Access-Token");
        //获取设备
        String client = request.getHeader("client");
        //获取用户名
        String username = request.getHeader("username");

        Boolean flag = false;
        if (org.apache.commons.lang.StringUtils.isNotBlank(whiteUrl)) {
            String[] split = whiteUrl.split(",");
            for (String s : split) {
                if (request.getRequestURL().toString().contains(s)) {
                    flag = true;
                    break;
                }
            }
        }
        if (flag) {
            ctx.setSendZuulResponse(true);
            ctx.setResponseStatusCode(200);
            return null;
        }

        //判断释放有token自动
        if (!StringUtils.isEmpty(accessToken)) {
            log.warn("token is empty");
            ctx.setSendZuulResponse(false);
            ctx.setResponseStatusCode(401);
            try {
                ctx.setResponseBody("token is error");
                ctx.getResponse().getWriter().write("token is empty");
            } catch (Exception e) {
            }
        } else {
            //Access-Token存在，刷新token
            ctx.setSendZuulResponse(true);
            ctx.setResponseStatusCode(200);
            log.info("Access-Token存在，刷新token");
        }
        return null;
    }
}
