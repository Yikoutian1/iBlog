package com.hang.dto;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @ClassName UserInfoDTO
 * @Description TODO
 * @Author QiuLiHang
 * @DATE 2023/8/7 21:22
 * @Version 1.0
 */
@Data
@Accessors(chain = true)
public class UserInfoDTO {
    private Long id;

    private String nickName;

    private String avatar;

    private String sex;

    private String email;
}
