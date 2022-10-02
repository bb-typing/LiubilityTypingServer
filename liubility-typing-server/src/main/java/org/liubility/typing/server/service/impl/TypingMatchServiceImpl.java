package org.liubility.typing.server.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.liubility.commons.exception.LBRuntimeException;
import org.liubility.commons.http.response.table.PageTable;
import org.liubility.commons.http.response.table.TableFactory;
import org.liubility.commons.http.response.table.TableRef;
import org.liubility.commons.util.ArticleUtil;
import org.liubility.commons.util.TimingMap;
import org.liubility.typing.server.domain.entity.Article;
import org.liubility.typing.server.domain.entity.TypingHistory;
import org.liubility.typing.server.domain.entity.TypingMatch;
import org.liubility.typing.server.domain.vo.TypingHistoryVO;
import org.liubility.typing.server.domain.vo.TypingMatchVO;
import org.liubility.typing.server.enums.exception.Code204TypingMatch;
import org.liubility.typing.server.mappers.ArticleMapper;
import org.liubility.typing.server.mappers.TypingHistoryMapper;
import org.liubility.typing.server.mappers.TypingMatchMapper;
import org.liubility.typing.server.service.TypingMatchService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @Author JDragon
 * @Date 2021.08.29 下午 10:12
 * @Email 1061917196@qq.com
 * @Des:
 */

@Service
public class TypingMatchServiceImpl extends ServiceImpl<TypingMatchMapper, TypingMatch> implements TypingMatchService {

    private static final TimingMap<Long, Long> OPEN_TLJ_MATCH_USER_LIST = new TimingMap<>();

    private final TypingHistoryMapper typingHistoryMapper;

    private final ArticleMapper articleMapper;

    public TypingMatchServiceImpl(TypingHistoryMapper typingHistoryMapper, ArticleMapper articleMapper) {
        this.typingHistoryMapper = typingHistoryMapper;
        this.articleMapper = articleMapper;
    }

    @Override
    public TypingMatchVO getTodayMatch(Long userId, Boolean mobile) {
        TypingHistory typingMatchHistory = typingHistoryMapper.getTypingMatchHistory(userId, mobile, DateUtil.today());
        if (typingMatchHistory != null) {
            throw new LBRuntimeException(Code204TypingMatch.GET_AGAIN);
        }

        insertTodayMatch();
        TypingMatchVO typingMatchVO = baseMapper.getTodayMatch(DateUtil.today());
        if (typingMatchVO != null) {
            TypingHistory emptyHistory = new TypingHistory();
            emptyHistory.setTypeDate(DateUtil.date().toJdkDate());
            emptyHistory.setUserId(userId);
            emptyHistory.setArticleId(typingMatchVO.getArticle().getId());
            emptyHistory.setMatchType(1);
            emptyHistory.setMobile(mobile);
            if (emptyHistory.insert()) {
                OPEN_TLJ_MATCH_USER_LIST.put(userId, emptyHistory.getId());
                return typingMatchVO;
            }
        }
        throw new LBRuntimeException("获取失败");
    }

    @Transactional(isolation = Isolation.READ_UNCOMMITTED, rollbackFor = Exception.class)
    protected void insertTodayMatch() {
        if (!baseMapper.existMatch(DateUtil.today())) {
            String random = ArticleUtil.getRandomContent();
            String title = DateUtil.now() + "日生稿赛";
            Article article = articleMapper.getArticleByContent(title, random);
            if (article == null) {
                article = new Article(title, random);
                article.insert();
            }
            TypingMatch typingMatch = new TypingMatch(article.getId(), DateUtil.date().toJdkDate(), "随机生成", 1);
            typingMatch.insert();
        }
    }

    @Override
    public String uploadMatch(Long userId, TypingHistory typingHistory) {
        if (OPEN_TLJ_MATCH_USER_LIST.containsKey(userId)) {
            TypingMatchVO todayMatch = baseMapper.getTodayMatch(DateUtil.today());
            typingHistory.setId(OPEN_TLJ_MATCH_USER_LIST.get(userId));
            typingHistory.setArticleId(todayMatch.getArticleId());
            typingHistory.setUserId(userId);
            if (typingHistory.updateById()) {
                OPEN_TLJ_MATCH_USER_LIST.remove(userId);
                return "上传成功";
            } else {
                throw new LBRuntimeException(Code204TypingMatch.UPLOAD_FAIL);
            }
        } else {
            throw new LBRuntimeException(Code204TypingMatch.EXPIRED);
        }
    }

    @Override
    public PageTable<TypingHistoryVO> getMatchAch(Date date, Integer matchType, Boolean mobile) {
        List<TypingHistoryVO> typingMatchHistoryWithName = typingHistoryMapper.getTypingMatchHistoryWithName(date, matchType, mobile);
        return TableFactory.buildPageTable(new TableRef<TypingHistoryVO>(typingMatchHistoryWithName) {
        });
    }
}
