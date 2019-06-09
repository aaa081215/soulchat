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
import top.soul.dto.DyMarks;
import top.soul.dto.DynamicsDto;
import top.soul.redis.RedisClient;
import top.soul.service.IDyMarkService;
import top.soul.service.IDynamicsService;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;


/**
 * Created by Michael on 2017/11/4.
 */
@Controller
@RequestMapping("/mark")
@CrossOrigin(origins = "*", maxAge = 3600)
public class MarkController {

    @Reference
    private IDyMarkService markService;
    @Autowired
    private ConstantQiniu constantQiniu;
    @Autowired
    private RedisClient redisClient;

    //评论列表
    @RequestMapping("/list")
    @ResponseBody
    public List<DyMarks> list(
            @RequestParam ("dyid") String dyid ,
            @RequestParam ("pindex") int index ,
                     @RequestParam ("psize") int psize
    ) throws IOException {

        return markService.listDymarksPageByd(dyid,index,psize);
    }

    //视频动态列表
    @RequestMapping("/add")
    @ResponseBody
    public boolean listVideo(@RequestParam ("usrrid") String usrrid ,
                                  @RequestParam ("texts") String texts,
                                  @RequestParam ("videourl") String videourl
    ) throws IOException {
        String inon="http://img.9421.top/15580875108772787";
        if(redisClient.get("user_"+ usrrid)!=null){
            inon=redisClient.get("user_"+ usrrid);
        }

        DyMarks dyMarks = new DyMarks();
        dyMarks.setId(String.valueOf(new Date().getTime())+String.valueOf(Math.random()*100000));
        dyMarks.setVideoid(videourl);

        dyMarks.setMarkdescript(texts);

        dyMarks.setMarkpeopleid(usrrid);
        dyMarks.setMarkpeoplename(inon);
        markService.addDynamics(dyMarks);
        return  true;
    }

}
