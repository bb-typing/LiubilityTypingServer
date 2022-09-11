package org.liubility.typing.server.minio;

import io.minio.*;
import io.minio.errors.*;
import io.minio.messages.DeleteError;
import io.minio.messages.DeleteObject;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: JDragon
 * @Data:2022/9/9 20:32
 * @Description:
 */

@Slf4j
@Builder
public class Minio{

    private final String endpoint;

    private final String accessKey;

    private final String secretKey;

    @Getter
    private MinioClient minioClient;

    public void init() {
        minioClient = MinioClient.builder()
                .endpoint(endpoint)
                .credentials(accessKey, secretKey)
                .build();
    }

    /**
     * 创建一个新的存储桶
     *
     * @param bucketName 新的存储桶名称
     */
    public void makeBucket(String bucketName) throws IOException, InvalidResponseException, InvalidKeyException, NoSuchAlgorithmException, ServerException, ErrorResponseException, XmlParserException, InsufficientDataException, InternalException {
        if (!bucketExists(bucketName)) {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
        }
    }

    /**
     * 判断存储桶是否存在
     *
     * @param bucketName 存储桶名称
     * @return 是否存在
     */
    public boolean bucketExists(String bucketName) throws IOException, InvalidKeyException, InvalidResponseException, InsufficientDataException, NoSuchAlgorithmException, ServerException, InternalException, XmlParserException, ErrorResponseException {
        return minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
    }

    /**
     * 获取下载对象流
     *
     * @param bucketName 存储桶名称
     * @param objectName 对象名称
     * @return 对象流
     */
    public InputStream getObject(String bucketName, String objectName) throws IOException, InvalidKeyException, InvalidResponseException, InsufficientDataException, NoSuchAlgorithmException, ServerException, InternalException, XmlParserException, ErrorResponseException {
        return minioClient.getObject(GetObjectArgs.builder()
                .bucket(bucketName)
                .object(objectName).build());
    }

    /**
     * 从流上传对象
     *
     * @param bucketName  存储桶名称
     * @param objectName  对象名称
     * @param inputStream 上传流
     */
    public void putObject(String bucketName, String objectName, InputStream inputStream) throws IOException, ServerException, InsufficientDataException, InternalException, InvalidResponseException, InvalidKeyException, NoSuchAlgorithmException, XmlParserException, ErrorResponseException {
        makeBucket(bucketName);
        minioClient.putObject(PutObjectArgs.builder()
                .bucket(bucketName)
                .object(objectName)
                .stream(inputStream, inputStream.available(), -1).build());
    }

    /**
     * 批量删除对象
     *
     * @param bucketName  存储桶
     * @param objectNames 对象名称数组
     */
    public void removeObjects(String bucketName, String... objectNames) {
        removeObjects(bucketName, Arrays.asList(objectNames));
    }

    /**
     * 批量删除对象
     *
     * @param bucketName  存储桶
     * @param objectNames 删除对象名称链表
     */
    public void removeObjects(String bucketName, List<String> objectNames) {
        List<DeleteObject> objects = objectNames.stream().map(DeleteObject::new).collect(Collectors.toList());
        Iterable<Result<DeleteError>> results = minioClient.removeObjects(RemoveObjectsArgs.builder()
                .bucket(bucketName)
                .objects(objects).build());
        for (Result<DeleteError> result : results) {
            try {
                DeleteError error = result.get();
                System.out.println("Error in deleting object " + error.objectName() + "; " + error.message());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public StatObjectResponse statObject(String bucketName, String objectName) throws IOException, InvalidKeyException, InvalidResponseException, InsufficientDataException, NoSuchAlgorithmException, ServerException, InternalException, XmlParserException, ErrorResponseException {
        return minioClient.statObject(StatObjectArgs.builder().bucket(bucketName).object(objectName).build());
    }
}
