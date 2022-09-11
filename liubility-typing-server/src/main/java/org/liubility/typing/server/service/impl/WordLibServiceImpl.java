package org.liubility.typing.server.service.impl;

import org.liubility.typing.server.minio.BucketConstant;
import org.liubility.typing.server.minio.service.MinioServiceImpl;
import org.liubility.typing.server.service.WordLibService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author: JDragon
 * @Data:2022/9/10 1:36
 * @Description:
 */
@Service
public class WordLibServiceImpl implements WordLibService {


    private final MinioServiceImpl minioService;

    public WordLibServiceImpl(MinioServiceImpl minioService) {
        this.minioService = minioService;
    }

    @Override
    public void uploadWordLib(Long userId, MultipartFile file) {
        minioService.upload(file, BucketConstant.WORD_LIB_BUCKET);
    }
}
