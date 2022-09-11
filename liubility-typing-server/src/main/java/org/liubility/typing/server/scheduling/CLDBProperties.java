package org.liubility.typing.server.scheduling;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author: JDragon
 * @Data:2022/9/11 14:25
 * @Description:
 */
@Data
@Component
@ConfigurationProperties(prefix = "cl-db")
public class CLDBProperties {

    private String host;

    private Integer port;

    private String user;

    private String password;

    private boolean synOpen;
}
