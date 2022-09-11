package org.liubility.typing.server.scheduling;

import lombok.extern.slf4j.Slf4j;

/**
 * @Author JDragon
 * @Date 2022.01.12 上午 10:39
 * @Email 1061917196@qq.com
 * @Des:
 */

@Slf4j
public abstract class ToggleScheduler {

    protected Boolean flag = true;

    /**
     * 定时开关
     *
     * @return 返回修改后状态
     */
    public Boolean toggle() {
        flag = !flag;
        if (log.isDebugEnabled()) {
            log.debug(this.getClass().getName() + "定时器状态修改，当前开关状态[{}]", flag);
        }
        return flag;
    }

    public abstract void start();
}
