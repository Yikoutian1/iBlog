package com.hang;

import org.junit.jupiter.api.Test;
import org.springframework.boot.system.ApplicationHome;

import java.io.File;

/**
 * @ClassName upload
 * @Description TODO
 * @Author QiuLiHang
 * @DATE 2023/9/21 021 21:49
 * @Version 1.0
 */

public class upload {
    @Test
    public void test1() {
        ApplicationHome applicationHome = new ApplicationHome(this.getClass());
        System.out.println(applicationHome);
        String pre = applicationHome.getDir().getParentFile().getAbsolutePath()
                + "\\src\\main\\resources\\images\\";
        System.out.println(pre);
        File fileP = new File(pre);
        System.out.println("===start===");
        if (!fileP.isDirectory()) {
            //递归生成文件夹
//            fileP.mkdirs();
            System.out.println("生成文件夹成功!" + fileP.getAbsolutePath());
        } else {
            System.out.println("生成文件夹失败!");
        }
        System.out.println("===end===");
    }
}
