package org.liubility.typing.server.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.apache.ibatis.reflection.MetaObject;
import org.liubility.commons.interceptor.translation.TranslationInterceptor;
import org.liubility.typing.server.code.libs.WordLib;
import org.liubility.typing.server.domain.entity.WordLibInfo;
import org.liubility.typing.server.enums.MobileEnum;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

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

    @Component
    public static class WordLibInsertFillOriginHandler implements MetaObjectHandler {

        @Override
        public void insertFill(MetaObject metaObject) {
            String originFieldName = "originId";
            Class<?> aClass = metaObject.getOriginalObject().getClass();
            if (!aClass.equals(WordLibInfo.class)) {
                return;
            }
            if (metaObject.getValue(originFieldName) == null) {
                this.setFieldValByName(originFieldName, metaObject.getValue("id"), metaObject);
            }
        }

        @Override
        public void updateFill(MetaObject metaObject) {

        }
    }
}
