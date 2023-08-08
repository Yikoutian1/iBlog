package com.calyee;

import com.google.gson.Gson;
import com.hang.CalyeeBlogApplication;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;

import java.io.FileInputStream;
import java.io.InputStream;

/**
 * @ClassName test
 * @Description TODO
 * @Author QiuLiHang
 * @DATE 2023/8/7 16:46
 * @Version 1.0
 */

@SpringBootTest(classes = CalyeeBlogApplication.class)
@TestPropertySource(locations = "classpath:application.yml")
public class OSSTest {

//oss:
//accessKey: cuwMLlcQi3v83Q9WmNeqn2FSq2TCvE8C5ok7McfH
//secretKey: V-LBcGJAuptis_hXNsWLuuJg-wioAEBsN9zFiMKT
//bucket: calyee-blog
    @Value("${oss.accessKey}")
    private String accessKey;// = "cuwMLlcQi3v83Q9WmNeqn2FSq2TCvE8C5ok7McfH";
    @Value("${oss.secretKey}")
    private String secretKey;// = "V-LBcGJAuptis_hXNsWLuuJg-wioAEBsN9zFiMKT";
    @Value("${oss.bucket}")
    private String bucket;// = "calyee-blog";

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    @Test
    public void testOss(){
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.autoRegion());
        //...其他参数参考类注释

        UploadManager uploadManager = new UploadManager(cfg);
        //...生成上传凭证，然后准备上传
//        String accessKey = "your access key";
//        String secretKey = "your secret key";
//        String bucket = "sg-blog";

        //默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = "2023/sg.png";

        try {
//            byte[] uploadBytes = "hello qiniu cloud".getBytes("utf-8");
//            ByteArrayInputStream byteInputStream=new ByteArrayInputStream(uploadBytes);


            InputStream inputStream = new FileInputStream("D:\\Data\\reggiePhoto\\cache\\c99e0aab-3cb7-4eaa-80fd-f47d4ffea694.png");
            Auth auth = Auth.create(accessKey, secretKey);
            String upToken = auth.uploadToken(bucket);

            try {
                Response response = uploadManager.put(inputStream,key,upToken,null, null);
                //解析上传成功的结果
                DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
                System.out.println(putRet.key);
                System.out.println(putRet.hash);
            } catch (QiniuException ex) {
                Response r = ex.response;
                System.err.println(r.toString());
                try {
                    System.err.println(r.bodyString());
                } catch (QiniuException ex2) {
                    //ignore
                }
            }
        } catch (Exception ex) {
            //ignore
        }

    }
}
