package org.liubility.typing.server.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.liubility.commons.exception.LBException;
import org.liubility.commons.exception.LBRuntimeException;
import org.liubility.commons.http.response.normal.Result;
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
import org.liubility.typing.server.mappers.ArticleMapper;
import org.liubility.typing.server.mappers.TypingHistoryMapper;
import org.liubility.typing.server.mappers.TypingMatchMapper;
import org.liubility.typing.server.service.ArticleService;
import org.liubility.typing.server.service.TypingMatchService;
import org.springframework.beans.factory.annotation.Autowired;
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

    private static final TimingMap<Long, Long> openTljMatchUserList = new TimingMap<>();

    @Autowired
    private TypingHistoryMapper typingHistoryMapper;

    @Autowired
    private ArticleMapper articleMapper;


    @Override
    public TypingMatchVO getTodayMatch(Long userId, Boolean mobile) throws LBException {
        TypingHistory typingMatchHistory = typingHistoryMapper.getTypingMatchHistory(userId, mobile, DateUtil.today());
        if (typingMatchHistory != null) {
            throw new LBException("你今日已获取过赛文");
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
                openTljMatchUserList.put(userId, emptyHistory.getId());
                return typingMatchVO;
            }
        }
        throw new LBException("获取失败");
    }

    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
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
        if (openTljMatchUserList.containsKey(userId)) {
            TypingMatchVO todayMatch = baseMapper.getTodayMatch(DateUtil.today());
            typingHistory.setId(openTljMatchUserList.get(userId));
            typingHistory.setArticleId(todayMatch.getArticleId());
            typingHistory.setUserId(userId);
            if (typingHistory.updateById()) {
                openTljMatchUserList.remove(userId);
                return "上传成功";
            } else {
                throw new LBRuntimeException("上传失败");
            }
        } else {
            throw new LBRuntimeException("赛文已过期");
        }
    }

    @Override
    public PageTable<TypingHistoryVO> getMatchAch(Date date, Integer matchType, Boolean mobile) {
        List<TypingHistoryVO> typingMatchHistoryWithName = typingHistoryMapper.getTypingMatchHistoryWithName(date, matchType, mobile);
        return TableFactory.buildPageTable(new TableRef<TypingHistoryVO>(typingMatchHistoryWithName){});
    }
}
