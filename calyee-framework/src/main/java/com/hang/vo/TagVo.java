package com.hang.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName TagVo
 * @Description TODO
 * @Author QiuLiHang
 * @DATE 2023/8/11 15:13
 * @Version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TagVo {
    private Long id;
    private String name;
    private String remark;
}
