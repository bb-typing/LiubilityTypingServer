package org.liubility.typing.server.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;

/**
 * @Author: JDragon
 * @Data:2022/11/9 22:48
 * @Description:
 */
@Configuration
@Slf4j
public class MongoConverterConfig implements InitializingBean {

    private final MappingMongoConverter mappingConverter;

    public MongoConverterConfig(MappingMongoConverter mappingConverter) {
        this.mappingConverter = mappingConverter;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        //去除插入数据库的_class字段
        mappingConverter.setTypeMapper(new DefaultMongoTypeMapper(null));
    }
}