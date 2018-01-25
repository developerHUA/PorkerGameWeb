package com.huarenkeji.porkergame.socket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebSocketConfigurator {


    @Bean
    public HttpSessionConfigurator customSpringConfigurator() {
        return new HttpSessionConfigurator(); // This is just to get context
    }

}
