package com.hang.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName ChangeRoleStatusDto
 * @Description TODO
 * @Author QiuLiHang
 * @DATE 2023/9/1 10:05
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangeRoleStatusDto {

    private Long roleId;
    private String status;

}
