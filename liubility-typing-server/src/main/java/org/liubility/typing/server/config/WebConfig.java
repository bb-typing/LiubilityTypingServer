package org.liubility.typing.server.config;

import org.liubility.commons.interceptor.ContextHolderInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author JDragon
 * @Date 2021.04.29 下午 9:39
 * @Email 1061917196@qq.com
 * @Des:
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Bean
    public ContextHolderInterceptor contextHolderInterceptor() {
        return new ContextHolderInterceptor();
    }
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addWebRequestInterceptor(contextHolderInterceptor());
    }

//    @Bean
//    public CorsFilter corsFilter() {
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", buildConfig());
//        return new CorsFilter(source);
//    }
//
//    private CorsConfiguration buildConfig() {
//        CorsConfiguration corsConfiguration = new CorsConfiguration();
//        // 1允许任何域名使用
//        corsConfiguration.addAllowedOrigin("*");
//        // 2允许任何头
//        corsConfiguration.addAllowedHeader("*");
//        // 3允许任何方法（post、get等）
//        corsConfiguration.addAllowedMethod("*");
//        return corsConfiguration;
//    }
}
