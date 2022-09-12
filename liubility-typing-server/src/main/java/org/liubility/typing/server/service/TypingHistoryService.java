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
    /**
     * 根据用户id获取跟打历史分页
     *
     * @param iPage 分页参数
     * @param userId       用户id
     * @return 历史跟打记录分页
     */
    IPage<TypeHistoryDto> getTypeHistoryByUserId(IPage<TypingHistory> iPage, Long userId);

    /**
     * 上传跟打历史
     *
     * @param historyArticleDto 历史与文章详情
     * @return message
     */
    String uploadHistoryAndArticle(HistoryArticleDto historyArticleDto);
}
