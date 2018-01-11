package com.huarenkeji.porkergame.conf;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.huarenkeji.porkergame.base.Result;
import com.huarenkeji.porkergame.common.MD5;
import com.huarenkeji.porkergame.common.RequestJsonUtils;
import com.huarenkeji.porkergame.config.NetConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SpringMVCInterceptor extends HandlerInterceptorAdapter {
    private static final Logger logger = LoggerFactory.getLogger(SpringMVCInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {

        String json = RequestJsonUtils.getRequestPostStr(httpServletRequest);
        JSONObject jsonObject = JSON.parseObject(json);
        String key = jsonObject.getString("key");
        String time = jsonObject.getString("time");
        jsonObject = jsonObject.getJSONObject("params");
        String paramsJson = jsonObject.toJSONString();

        String curKey = MD5.getMessageDigest(MD5.getMessageDigest((paramsJson + time + NetConfig.SALT).getBytes()).getBytes());
        if (curKey.equals(key)) {
            // key is valid
            return true;
        } else {
            httpServletResponse.getWriter().append(Result.getInValidKeyJson() + "---" + curKey);
            return false;
        }

    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        logger.debug("postHandle");
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        logger.debug("afterCompletion");
    }
}
