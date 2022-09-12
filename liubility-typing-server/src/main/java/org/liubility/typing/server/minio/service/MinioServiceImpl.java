package org.liubility.typing.server.minio.service;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import io.minio.StatObjectResponse;
import io.minio.errors.*;
import lombok.extern.slf4j.Slf4j;
import org.liubility.commons.exception.LBRuntimeException;
import org.liubility.typing.server.minio.Minio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.UUID;

/**
 * @Author: JDragon
 * @Data:2022/9/10 0:35
 * @Description:
 */
@Slf4j
@Service
public class MinioServiceImpl {

    private final Minio minio;

    public MinioServiceImpl(Minio minio) {
        this.minio = minio;
    }

    public OssFileInfoVO upload(MultipartFile file, String bucketName) {
        String objectName = generateName(file);
        try {
            minio.putObject(bucketName, objectName, file.getInputStream());
        } catch (IOException e) {
            log.error("上传文件获取流失败", e);
            throw new LBRuntimeException("上传文件获取流失败", e);
        } catch (XmlParserException | ServerException | NoSuchAlgorithmException | InsufficientDataException | InvalidKeyException | InvalidResponseException | ErrorResponseException | InternalException e) {
            log.error("上传文件到对象存储失败", e);
            throw new LBRuntimeException("上传文件到对象存储失败", e);
        }
        return getFileInfo(bucketName, objectName);
    }

    public OssFileInfoVO getFileInfo(String bucketName, String objectName) {
        StatObjectResponse statObjectResponse;
        try {
            statObjectResponse = minio.statObject(bucketName, objectName);
            return OssFileInfoVO.builder()
                    .fileName(statObjectResponse.object())
                    .filePath(objectName)
                    .bucket(bucketName)
                    .lastModified(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss").format(statObjectResponse.lastModified()))
                    .size(statObjectResponse.size())
                    .build();
        } catch (Exception e) {
            throw new LBRuntimeException("获取信息失败");
        }
    }

    public String generateName(MultipartFile file) {
        String suffix = FileUtil.extName(file.getOriginalFilename());
        String objectName = UUID.randomUUID().toString();
        if (StrUtil.isNotBlank(suffix)) {
            objectName = objectName + "." + suffix;
        }
        return objectName;
    }

    public void delete(String bucketName, String objectName) {
        minio.removeObjects(bucketName, objectName);
    }
}
