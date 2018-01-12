package com.huarenkeji.porkergame.conf;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.huarenkeji.porkergame.base.Result;
import com.huarenkeji.porkergame.common.MD5;
import com.huarenkeji.porkergame.common.RequestJsonUtils;
import com.huarenkeji.porkergame.config.NetConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class CheckKeyFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(CheckKeyFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (checkKey(request, response, chain)) {
            chain.doFilter(request, response);

        }
    }

    @Override
    public void destroy() {

    }


    private boolean checkKey(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (request instanceof HttpServletRequest) {
            BodyReaderHttpServletRequestWrapper bodyRequest = new BodyReaderHttpServletRequestWrapper((HttpServletRequest) request);
            String json = RequestJsonUtils.getRequestPostStr(bodyRequest);
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
                response.getWriter().append(Result.getInValidKeyJson()).append("----").append(curKey);
                return false;
            }
        }
        return true;
    }

}
