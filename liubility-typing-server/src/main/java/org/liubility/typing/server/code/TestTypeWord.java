package org.liubility.typing.server.code;

import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSONObject;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.Document;
import org.liubility.typing.server.handler.TypingWordsDetailHandler;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @Author: JDragon
 * @Data:2022/11/8 2:13
 * @Description:
 */
public class TestTypeWord {
    public static void main(String[] args) {
        String uri = "mongodb://root:example@192.168.1.150:27017/?socketKeepAlive=true&heartbeatFrequency=1000&maxConnectionIdleTime=600000";
        MongoClient mongoClient = MongoClients.create(uri);
        MongoDatabase database = mongoClient.getDatabase("bb-typing");
        MongoCollection<Document> collection = database.getCollection("all_data_test");
        Map<String, String> dictToMap = Test.wordLib.dictToMap();

        List<Document> cache = new LinkedList<>();
        int index = 0;
        for (int i = 1; i <= 10000; i++) {
            for (String word : dictToMap.keySet()) {
                int randomInt = RandomUtil.randomInt(0, 10000 * 50000);
                TypeStatusCount typeStatusCount = new TypeStatusCount(i, word, randomInt, randomInt, randomInt, 0, 0, randomInt);
                Document document = Document.parse(JSONObject.toJSONString(typeStatusCount));
                cache.add(document);
                if (cache.size() >= 1024) {
                    index += cache.size();
                    System.out.println(i + " 下标：" + index);
                    collection.insertMany(cache);
                    cache.clear();
                }
            }
        }
        if (!cache.isEmpty()) {
            index += cache.size();
            System.out.println("下标：" + index);
            collection.insertMany(cache);
            cache.clear();
        }
    }


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TypeStatusCount {

        private int userId;

        private String word;

        private int count;

        private int codeRight;

        private int wordRight;

        private int codeWrong;

        private int wordWrong;

        private int perfect;
    }
}
