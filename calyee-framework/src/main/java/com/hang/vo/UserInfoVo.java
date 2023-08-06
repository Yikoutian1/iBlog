package com.hang.vo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @ClassName UserinfoVo
 * @Description TODO
 * @Author QiuLiHang
 * @DATE 2023/8/6 12:00
 * @Version 1.0
 */

@Data
@Accessors(chain = true)
public class UserInfoVo {
    /**
     * 主键
     */
    private Long id;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 头像
     */
    private String avatar;

    private String sex;

    private String email;


}
