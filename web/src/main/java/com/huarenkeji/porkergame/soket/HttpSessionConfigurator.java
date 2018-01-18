package com.huarenkeji.porkergame.soket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpSession;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;

/**
 *
 */
public class HttpSessionConfigurator extends ServerEndpointConfig.Configurator  {
    private static final Logger logger = LoggerFactory.getLogger(HttpSessionConfigurator.class);
    @Override
    public void modifyHandshake(ServerEndpointConfig config, HandshakeRequest request, HandshakeResponse response){
        HttpSession httpSession = (HttpSession)request.getHttpSession();
        if(httpSession == null) {
            logger.debug("httpSession == null");
            return;
        }


        config.getUserProperties().put(HttpSession.class.getName(),httpSession);
    }
}
