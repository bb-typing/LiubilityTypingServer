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
    /**
     * 上传用户词库
     * @param userId    用户id
     * @param wordLibDTO    词库信息
     */
    void uploadWordLib(Long userId, WordLibDTO wordLibDTO);

    /**
     * 加载用户默认词库
     * @param userId    用户id
     * @return  词库解析器
     */
    TrieWordParser loadWordLib(Long userId);

    /**
     * 获取词语提示
     * @param userId    用户id
     * @param article   需要词提的文章
     * @return  TypingTips
     */
    TypingTips typingTips(Long userId, String article);
}
