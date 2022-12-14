package org.liubility.typing.server.scheduling;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.liubility.commons.json.JsonUtils;
import org.liubility.typing.server.domain.entity.*;
import org.liubility.typing.server.service.*;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.*;

/**
 * @Author: JDragon
 * @Data:2022/9/10 11:47
 * @Description:
 */

@Slf4j
@Component
public class SynEarlierVersionData extends ToggleScheduler {

    private static final String SYN_DATA_REDIS_PREFIX = "syn_data:";

    public enum SynSqlEnum {
        Account(Account.class, "select id,username,`password` from tlj_user"),
        TypingUser(TypingUser.class, "select id,num,rightNum,misNum,dateNum,regDate,lastLoginDate from tlj_user"),
        TypingHistory(TypingHistory.class, "select id,userId,articleId,typeDate,speed,keySpeed,keyLength,number,deleteText,deleteNum,mistake,repeatNum,keyAccuracy,keyMethod,wordRate,time,paragraph,isMobile mobile,case paragraph when 9999 then 2 else null end as matchType from tlj_history"),
        TypingMatch(TypingMatch.class, "select holdDate,articleId,author,2 matchType from tlj_match"),
        Article(Article.class, "select * from all_article"),
        ;

        @Getter
        private final Class<?> aClass;

        @Getter
        private final String sql;

        SynSqlEnum(Class<?> aClass, String sql) {
            this.aClass = aClass;
            this.sql = sql;
        }

        public static String getSqlByClass(Class<?> aClass, Object idMin) {
            for (SynSqlEnum synSqlEnum : SynSqlEnum.values()) {
                if (synSqlEnum.getAClass().equals(aClass)) {
                    if (idMin == null) {
                        return synSqlEnum.getSql();
                    } else {
                        return synSqlEnum.getSql() + " where id>" + idMin;
                    }
                }
            }
            return null;
        }
    }

    private final CLDBProperties cldbProperties;

    private final AccountService accountService;

    private final TypingUserService typingUserService;

    private final TypingHistoryService typingHistoryService;

    private final TypingMatchService typingMatchService;

    private final ArticleService articleService;

    private final RedisTemplate<String, Object> redisTemplate;

    public SynEarlierVersionData(CLDBProperties cldbProperties, AccountService accountService, TypingUserService typingUserService, TypingHistoryService typingHistoryService, TypingMatchService typingMatchService, ArticleService articleService, RedisTemplate<String, Object> redisTemplate) {
        this.cldbProperties = cldbProperties;
        this.accountService = accountService;
        this.typingUserService = typingUserService;
        this.typingHistoryService = typingHistoryService;
        this.typingMatchService = typingMatchService;
        this.articleService = articleService;
        this.redisTemplate = redisTemplate;
    }

    @Override
    @Scheduled(cron = "0 0/10 * * * ?")
    public void start() {
        if (!cldbProperties.isSynOpen()) {
            log.info("???????????????????????????");
            return;
        }

        log.info("????????????????????????");

        Connection connection = getConnection();

        convert(connection, Account.class, accountService::saveOrUpdateBatch);

        convert(connection, TypingUser.class, typingUserService::saveOrUpdateBatch);

        convert(connection, TypingHistory.class, typingHistoryService::saveOrUpdateBatch);

//        convert(connection, TypingMatch.class, typingMatchService::saveBatch);

        convert(connection, Article.class, articleService::saveOrUpdateBatch);

        closeResource(connection);

        log.info("????????????????????????");
    }

    public Connection getConnection() {
        try {
            log.info("????????????????????????");
            Class.forName("com.mysql.cj.jdbc.Driver");
            Properties prop = new Properties();
            prop.put("user", cldbProperties.getUser());
            prop.put("password", cldbProperties.getPassword());
            Connection connection = DriverManager.getConnection("jdbc:mysql://" + cldbProperties.getHost() + ":" + cldbProperties.getPort() +
                    "/tljrobot?serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=UTF-8", prop);
            log.info("??????????????????????????????");
            return connection;
        } catch (ClassNotFoundException | SQLException e) {
            log.error("??????????????????", e);
            return null;
        }
    }

    public void closeResource(Connection connection) {
        try {
            log.info("????????????????????????");
            connection.close();
            log.info("??????????????????????????????");
        } catch (SQLException e) {
            log.error("??????????????????", e);
        }
    }

    public <T> void convert(Connection connection, Class<T> tClass, Consumer<T> consumer) {
        try {
            String className = tClass.getName();

            Object redisCache = redisTemplate.opsForValue().get(SYN_DATA_REDIS_PREFIX + className);
            String sql = SynSqlEnum.getSqlByClass(tClass, redisCache);
            log.info("?????????????????????????????????{}", sql);

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            ResultSetMetaData metaData = resultSet.getMetaData();

            log.info("????????????{}", className);
            int columnCount = metaData.getColumnCount();
            List<T> cache = new LinkedList<>();
            int cacheSize = 200;
            long batch = 1;
            Long id = null;
            while (resultSet.next()) {
                Map<String, Object> map = new HashMap<>();
                for (int i = 1; i <= columnCount; i++) {
                    String columnLabel = metaData.getColumnLabel(i);
                    Object object;
                    if ("id".equals(columnLabel)) {
                        id = resultSet.getLong(i);
                        object = id;
                    } else {
                        object = resultSet.getObject(i);
                    }
                    map.put(columnLabel, object);
                }
                T t = JsonUtils.object2Object(map, tClass);
                cache.add(t);
                if (cache.size() >= cacheSize) {
                    log.info("????????????{}??????{}???{}???", className, batch, cacheSize);
                    consumer.accept(cache);
                    redisTemplate.opsForValue().set(SYN_DATA_REDIS_PREFIX + className, String.valueOf(id));
                    log.info("????????????{}??????{}???", className, batch);
                    cache.clear();
                    batch++;
                }
            }
            if (cache.size() > 0) {
                log.info("????????????{}??????{}???{}???", className, batch, cache.size());
                consumer.accept(cache);
                redisTemplate.opsForValue().set(SYN_DATA_REDIS_PREFIX + className, String.valueOf(id));
                log.info("????????????{}??????{}???", className, batch);
                cache.clear();
            }
            log.info("????????????{}", className);
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            log.error("??????????????????", e);
        }
    }

    @FunctionalInterface
    public interface Consumer<T> {

        /**
         * Performs this operation on the given argument.
         *
         * @param t the input argument
         */
        void accept(List<T> t);
    }
}
