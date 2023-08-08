package com.hang.runner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @ClassName TestRunner
 * @Description 在Springboot启动前就调用（比如在端口启动完之后）
 * @Author QiuLiHang
 * @DATE 2023/8/8 19:43
 * @Version 1.0
 */
@Component
public class TestRunner implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        // 读取浏览量数据，写入redis
        System.out.println("程序初始化");
    }
}
