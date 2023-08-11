package com.hang.dto;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @ClassName TagListDto
 * @Description TODO
 * @Author QiuLiHang
 * @DATE 2023/8/11 14:24
 * @Version 1.0
 */
@Data
@Accessors(chain = true)
public class TagListDto {
    private Long id;
    private String name;
    private String remark;
}
