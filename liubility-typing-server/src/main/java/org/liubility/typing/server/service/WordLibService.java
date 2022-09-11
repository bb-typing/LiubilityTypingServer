package org.liubility.typing.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.liubility.typing.server.domain.dto.WordLibDTO;
import org.liubility.typing.server.domain.entity.WordLibInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author: JDragon
 * @Data:2022/9/10 1:35
 * @Description:
 */
public interface WordLibService extends IService<WordLibInfo> {
    void uploadWordLib(Long userId, WordLibDTO wordLibDTO);
}
