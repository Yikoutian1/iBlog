package com.hang.vo;

import com.hang.entity.Menu;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @ClassName RoutersVo
 * @Description TODO
 * @Author QiuLiHang
 * @DATE 2023/8/10 16:13
 * @Version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoutersVo {
    private List<Menu> menus;
}
