package org.liubility.typing.server.service;

import com.baomidou.mybatisplus.extension.activerecord.Model;

/**
 * @Author: JDragon
 * @Data:2022/10/2 21:02
 * @Description:
 */
public abstract class CommonModel<T extends Model<T>> extends Model<T> {
    Long userId;

    public abstract Long getUserId();
}
