package com.hang;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * @ClassName Password
 * @Description TODO
 * @Author QiuLiHang
 * @DATE 2023/9/2 9:46
 * @Version 1.0
 */
@Component
@SpringBootTest
public class Password {
    @Autowired
    private PasswordEncoder passwordEncoder;
    // 需要加密的密码
    public static final String password = "";
    @Test
    public void 生成加密的密码(){
        String encode = passwordEncoder.encode(password);
        System.out.println("encode = " + encode);
    }
}
