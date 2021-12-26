package org.liubility.typing.server.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.liubility.commons.interceptor.translation.TranslationInterceptor;
import org.liubility.typing.server.enums.MobileEnum;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MybatisPlusConfig {
    /**
     * 分页插件
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }

    /**
     * 翻译插件
     */
    @Bean
    public TranslationInterceptor translationInterceptor() {
        TranslationInterceptor translationInterceptor = new TranslationInterceptor();
        translationInterceptor.register(MobileEnum.class);
        return translationInterceptor;
    }

    @Bean
    public void registerTranslation() {

    }
}
