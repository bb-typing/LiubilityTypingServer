package org.liubility.typing.server.minio;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author JDragon
 * @Date 2021.07.13 上午 11:27
 * @Email 1061917196@qq.com
 * @Des:
 **/
@Data
@Component
@ConfigurationProperties(prefix = "minio")
public class MinioProperties {

    private String host;

    private int port;

    private String accessKey;

    private String secretKey;
}
