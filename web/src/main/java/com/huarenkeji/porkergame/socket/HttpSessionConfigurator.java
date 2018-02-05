package com.huarenkeji.porkergame.socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.servlet.http.HttpSession;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;

/**
 *
 */
public class HttpSessionConfigurator extends ServerEndpointConfig.Configurator implements ApplicationContextAware {
    private static final Logger logger = LoggerFactory.getLogger(HttpSessionConfigurator.class);
    private static volatile BeanFactory context;
    @Override
    public void modifyHandshake(ServerEndpointConfig config, HandshakeRequest request, HandshakeResponse response){
        HttpSession httpSession = (HttpSession)request.getHttpSession();
        if(httpSession == null) {
            return;
        }


        config.getUserProperties().put(HttpSession.class.getName(),httpSession);
    }



    @Override
    public <T> T getEndpointInstance(Class<T> clazz) throws InstantiationException
    {
        return context.getBean(clazz);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException
    {
        System.out.println("auto load"+this.hashCode());
        HttpSessionConfigurator.context = applicationContext;
    }

}
