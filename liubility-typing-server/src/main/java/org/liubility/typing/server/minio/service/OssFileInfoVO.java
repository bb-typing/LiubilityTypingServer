package org.liubility.typing.server.minio.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author JDragon
 * @Date 2021.07.14 下午 7:18
 * @Email 1061917196@qq.com
 * @Des:
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OssFileInfoVO {

    private String fileName;

    private String filePath;

    private String bucket;

    private String url;

    private String lastModified;

    private long size;

    private String message;

}
