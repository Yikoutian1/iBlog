package com.hang.enums;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName OptionEnum
 * @Description TODO
 * @Author QiuLiHang
 * @DATE 2023/9/21 021 22:48
 * @Version 1.0
 */
public enum OptionEnum {
    /**
     * linux
     */
    Linux("linux"),
    /**
     * mac os
     */
    Mac_OS("mac os"),
    /**
     * windows
     */
    Windows("windows"),
    /**
     * Others
     */
    Others("Others");
    private String description;

    OptionEnum(String desc) {
        this.description = desc;
    }

    @Override
    public String toString() {
        return description.toLowerCase();
    }
}
