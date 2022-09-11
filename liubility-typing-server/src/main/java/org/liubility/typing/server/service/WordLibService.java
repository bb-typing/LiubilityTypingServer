package org.liubility.typing.server.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @Author: JDragon
 * @Data:2022/9/10 1:35
 * @Description:
 */
public interface WordLibService {
    void uploadWordLib(Long userId, MultipartFile file);
}
