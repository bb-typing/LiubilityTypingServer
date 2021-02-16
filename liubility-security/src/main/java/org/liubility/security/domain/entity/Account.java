package org.liubility.security.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @Author JDragon
 * @Date 2021.02.11 上午 12:55
 * @Email 1061917196@qq.com
 * @Des:
 */

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("base_account")
public class Account extends Model<Account> {

    private String username;

    private String password;

}
