package org.liubility.typing.server.config;

import org.liubility.commons.handler.GlobalExceptionHandler;
import org.liubility.commons.holder.SpringContextHolder;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;

/**
 * <p></p>
 * <p>create time: 2021/12/27 0:21 </p>
 *
 * @author : Jdragon
 */
@Configurable
public class TypingServerConfig {

    @Bean
    public GlobalExceptionHandler globalExceptionHandler() {
        return new GlobalExceptionHandler();
    }

    @Bean
    public SpringContextHolder getSpringContextHolder() {
        return new SpringContextHolder();
    }

}
