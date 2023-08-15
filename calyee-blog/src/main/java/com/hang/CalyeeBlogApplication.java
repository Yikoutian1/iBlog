package com.hang;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @ClassName CalyeeBlogApplication
 * @Description TODO
 * @Author QiuLiHang
 * @DATE 2023/8/4 15:14
 * @Version 1.0
 */
@EnableSwagger2          // 启动swagger2
@EnableScheduling        // 开启定时任务 （在JOB包）
@SpringBootApplication   // 我们还在启动类里面添加了，在启动前，先对浏览量的数量进行查询，然后保存到redis里面
public class CalyeeBlogApplication {
    public static void main(String[] args) {
        SpringApplication.run(CalyeeBlogApplication.class, args);
    }
}
