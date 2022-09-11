package org.liubility.typing.server.config;

import org.liubility.commons.util.TimingMap;
import org.liubility.typing.server.code.parse.TrieWordParser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: JDragon
 * @Data:2022/9/11 20:33
 * @Description:
 */

@Configuration
public class CodeConfig {
    @Bean
    public TimingMap<Long, TrieWordParser> wordParserTimingMap(){
        return new TimingMap<>();
    }
}
