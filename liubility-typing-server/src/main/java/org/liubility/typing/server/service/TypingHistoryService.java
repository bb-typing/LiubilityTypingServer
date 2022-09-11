package org.liubility.typing.server.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.liubility.typing.server.domain.entity.TypingHistory;
import org.liubility.commons.dto.account.HistoryArticleDto;
import org.liubility.commons.dto.account.TypeHistoryDto;
import org.liubility.commons.exception.LBRuntimeException;

/**
 * @Author JDragon
 * @Date 2021.02.19 下午 11:52
 * @Email 1061917196@qq.com
 * @Des:
 */
public interface TypingHistoryService extends IService<TypingHistory> {
    IPage<TypeHistoryDto> getTypeHistoryByUserId(IPage<TypingHistory> historyIPage, Long userId);

    String uploadHistoryAndArticle(HistoryArticleDto historyArticleDto) throws LBRuntimeException;
}
