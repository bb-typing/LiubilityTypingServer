package org.liubility.typing.server.mongo;

import org.liubility.typing.server.mongo.entity.TypeStatusCountMongo;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Set;

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

    List<TypeStatusCountMongo> findTypeStatusCountByUserIdAndWordIn(Long userId, Set<String> word);

}
