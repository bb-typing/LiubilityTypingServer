package org.liubility.typing.server.service;

import org.liubility.commons.exception.LBException;
import org.liubility.typing.server.domain.entity.TypingHistory;
import org.liubility.typing.server.domain.vo.TypingMatchVO;

/**
 * @Author JDragon
 * @Date 2021.08.29 下午 10:12
 * @Email 1061917196@qq.com
 * @Des:
 */
public interface TypingMatchService {
    TypingMatchVO getTodayMatch(Integer userId, Boolean mobile) throws LBException;

    String uploadMatch(Integer userId, TypingHistory typingHistory);
}
