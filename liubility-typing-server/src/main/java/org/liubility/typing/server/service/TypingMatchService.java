package org.liubility.typing.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.liubility.commons.exception.LBException;
import org.liubility.commons.http.response.table.PageTable;
import org.liubility.typing.server.domain.entity.TypingHistory;
import org.liubility.typing.server.domain.entity.TypingMatch;
import org.liubility.typing.server.domain.vo.TypingHistoryVO;
import org.liubility.typing.server.domain.vo.TypingMatchVO;

import java.util.Date;

/**
 * @Author JDragon
 * @Date 2021.08.29 下午 10:12
 * @Email 1061917196@qq.com
 * @Des:
 */
public interface TypingMatchService extends IService<TypingMatch> {
    /**
     * 获取今日生稿赛文
     *
     * @param userId 用户id
     * @param mobile 手机端或客户端
     * @return TypingMatchVO
     */
    TypingMatchVO getTodayMatch(Long userId, Boolean mobile);

    /**
     * 上传生稿赛文成绩
     *
     * @param userId        用户id
     * @param typingHistory 成绩历史
     * @return message
     */
    String uploadMatch(Long userId, TypingHistory typingHistory);

    /**
     * 获取某日比赛成绩
     *
     * @param date      日期
     * @param matchType 比赛类型
     * @param mobile    手机端或客户端
     * @return 成绩分页
     */
    PageTable<TypingHistoryVO> getMatchAch(Date date, Integer matchType, Boolean mobile);
}
