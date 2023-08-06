package com.hang.exception;

/**
 * @ClassName SystemException
 * @Description TODO
 * @Author QiuLiHang
 * @DATE 2023/8/6 14:46
 * @Version 1.0
 */

import com.hang.enums.AppHttpCodeEnum;

/**
 * @Author 三更  B站： https://space.bilibili.com/663528522
 */
public class SystemException extends RuntimeException{

    private int code;

    private String msg;

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public SystemException(AppHttpCodeEnum httpCodeEnum) {
        super(httpCodeEnum.getMsg());
        this.code = httpCodeEnum.getCode();
        this.msg = httpCodeEnum.getMsg();
    }

}
