package org.liubility.typing.server.config.component;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Author: JDragon
 * @Data:2022/10/2 21:00
 * @Description:
 */
public interface CommonService<T extends CommonModel<T>> extends IService<T> {

    default T getByUserId(Long userId) {
        LambdaQueryWrapper<T> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(CommonModel::getUserId, userId);
        return this.getOne(lambdaQueryWrapper);
    }
}
