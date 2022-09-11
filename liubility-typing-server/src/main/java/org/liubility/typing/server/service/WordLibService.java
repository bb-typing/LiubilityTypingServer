package org.liubility.typing.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.liubility.typing.server.code.parse.TrieWordParser;
import org.liubility.typing.server.domain.dto.WordLibDTO;
import org.liubility.typing.server.domain.entity.WordLibInfo;
import org.liubility.typing.server.domain.vo.TypingTips;

/**
 * @Author: JDragon
 * @Data:2022/9/10 1:35
 * @Description:
 */
public interface WordLibService extends IService<WordLibInfo> {
    TrieWordParser uploadWordLib(Long userId, WordLibDTO wordLibDTO);

    TrieWordParser loadWordLib(Long userId);

    TypingTips typingTips(Long userId, String article);
}
