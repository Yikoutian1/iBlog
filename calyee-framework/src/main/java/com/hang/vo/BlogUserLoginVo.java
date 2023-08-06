package com.hang.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName BlogUserLoginVo
 * @Description TODO
 * @Author QiuLiHang
 * @DATE 2023/8/6 14:05
 * @Version 1.0
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlogUserLoginVo {

    private String token;
    private UserInfoVo userInfo;
}