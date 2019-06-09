package top.soul.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import top.soul.config.ConstantQiniu;
import top.soul.dto.DynamicsDto;
import top.soul.redis.RedisClient;
import top.soul.service.IDynamicsService;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;


/**
 * Created by Michael on 2017/11/4.
 */
@Controller
@RequestMapping("/user")
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserController {

    @Reference
    private IDynamicsService dynamicsService;
    @Autowired
    private ConstantQiniu constantQiniu;

    @Autowired
    private RedisClient redisClient;



    @RequestMapping("/aiup")
    @ResponseBody
    public boolean aiuo(@RequestParam ("userid") String userid ,
                             @RequestParam ("icon") String icon
    ) throws IOException {
        return dynamicsService.updateai(userid,icon);
    }
    //更新头像
    @RequestMapping("/up")
    @ResponseBody
    public boolean listVideo(@RequestParam ("userid") String userid ,
                                  @RequestParam ("icon") String icon
    ) throws IOException {
        redisClient.set("user_"+userid, icon);
        return dynamicsService.updateicon(userid,icon);
    }

    //上传头像
    @RequestMapping("/upicon")
    @ResponseBody
    public String uploadImgQiniup(@RequestParam("file") MultipartFile multipartFile) throws IOException {

        final FileInputStream inputStream = (FileInputStream) multipartFile.getInputStream();
        final String nameName=new Date().getTime()+""+(int)(Math.random()*10000);
        Thread thread = new Thread(){
            public void run(){
                uploadQNImg(inputStream, nameName);
            }
        };
        thread.start();
        String imgurl="http://img.9421.top/"+nameName;
        return imgurl;
    }

    private void uploadQNImg(FileInputStream file, String key) {
        // 构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(Zone.zone0());
        // 其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        // 生成上传凭证，然后准备上传

        try {
            Auth auth = Auth.create(constantQiniu.getAccessKey(), constantQiniu.getSecretKey());
            String upToken = auth.uploadToken(constantQiniu.getBucket());
            try {
                Response response = uploadManager.put(file, key, upToken, null, null);
                // 解析上传成功的结果
                DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
                String returnPath =  putRet.key;
                System.out.println("七牛云异步执行成功结果："+returnPath);
            } catch (QiniuException ex) {
                Response r = ex.response;
                System.err.println(r.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
