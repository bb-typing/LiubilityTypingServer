package org.liubility.typing.server.mongo;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * @Author: JDragon
 * @Data:2022/11/9 20:53
 * @Description:
 */
public interface TypeStatusCountRepository extends MongoRepository<TypeStatusCountMongo, String> {

    @Aggregation(value = {
            "{ '$match': { 'userId': ?0 } }",
            "{ '$sort': { 'codeRight': ?1 } }",
            "{ '$limit': ?2 }"})
    List<TypeStatusCountMongo> findTypeStatusByUserIdDescTopNLimitN(Long userId, Integer codeRight, Integer limit);

    TypeStatusCountMongo findTypeStatusCountByUserIdAndWord(Long userId, String word);

}
