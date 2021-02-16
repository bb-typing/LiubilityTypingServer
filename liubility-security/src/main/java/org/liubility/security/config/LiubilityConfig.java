package org.liubility.security.config;

import org.liubility.commons.JwtServiceImpl;
import org.liubility.commons.handler.GlobalExceptionHandler;
import org.liubility.commons.holder.SpringContextHolder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author JDragon
 * @Date 2021.02.16 下午 6:07
 * @Email 1061917196@qq.com
 * @Des:
 */


@Configuration
public class LiubilityConfig {


    @Bean
    public SpringContextHolder getSpringContextHolder(){return new SpringContextHolder();}

}
