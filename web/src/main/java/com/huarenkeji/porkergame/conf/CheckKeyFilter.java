package com.huarenkeji.porkergame.conf;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
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


        }
    }

    @Override
    public void destroy() {

    }


    private boolean checkKey(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (request instanceof HttpServletRequest  ) {
            BodyReaderHttpServletRequestWrapper bodyRequest = new BodyReaderHttpServletRequestWrapper((HttpServletRequest) request);

            if("POST".equals(((HttpServletRequest) request).getMethod())) {
                String json = RequestJsonUtils.getRequestPostStr(bodyRequest);
//            JSONObject jsonObject = JSON.parseObject(json);
//            String key = jsonObject.getString("key");
//            String time = jsonObject.getString("time");
//            jsonObject = jsonObject.getJSONObject("params");
//            String paramsJson = jsonObject.toJSONString();
                try {
                    // Gson
                    JsonElement jsonElement = new JsonParser().parse(json);
                    String paramsJson = jsonElement.getAsJsonObject().get("params").toString();
                    String key = jsonElement.getAsJsonObject().get("key").getAsString();
                    String time = jsonElement.getAsJsonObject().get("time").getAsString();

                    String curKey = MD5.md5(MD5.md5((paramsJson + time + NetConfig.SALT)));
                    if (curKey.equals(key)) {
                        // key is valid
                        chain.doFilter(bodyRequest, response);
                        return true;
                    } else {
                        response.getWriter().append(Result.getInValidKeyResult().toJson());
                        return false;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    response.getWriter().append(Result.getParamError().toJson());
                    return false;
                }
            }else {
                chain.doFilter(bodyRequest, response);
            }
        }

        return true;
    }

}
