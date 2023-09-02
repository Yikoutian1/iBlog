package com.hang.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName ChangeUserStatusDto
 * @Description TODO
 * @Author QiuLiHang
 * @DATE 2023/9/2 10:15
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangeUserStatusDto {
    private Long userId;
    private String status;
}
