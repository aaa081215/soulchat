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
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sun.security.util.KeyUtil;
import top.soul.config.ConstantQiniu;
import top.soul.dto.DynamicsDto;
import top.soul.service.IDynamicsService;

import javax.servlet.http.HttpServletRequest;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;


/**
 * Created by Michael on 2017/11/4.
 */
@Controller
@RequestMapping("/dynamics")
@CrossOrigin(origins = "*", maxAge = 3600)
public class DyController {

    @Reference
    private IDynamicsService dynamicsService;
    @Autowired
    private ConstantQiniu constantQiniu;

///dynamics//dylist?pindex=1&psize=10
    //动态列表
    @RequestMapping("/dylist")
    @ResponseBody
    public List<DynamicsDto> list(@RequestParam ("pindex") int index ,
                     @RequestParam ("psize") int psize
    ) throws IOException {

        return dynamicsService.listDynamicsPage(psize,index);
    }

    //视频动态列表
    @RequestMapping("/dylistvideo")
    @ResponseBody
    public List<String> listVideo(@RequestParam ("pindex") int index ,
                                  @RequestParam ("psize") int psize
    ) throws IOException {
        System.out.println(index+":"+psize);
        return dynamicsService.listDynamicsPageVideo(psize,index);
    }

    //保存动态列表
    @RequestMapping("/dysave")
    @ResponseBody
    public String dy(@RequestParam ("desc") String desc ,
                     @RequestParam ("userid") String userid ,
                     @RequestParam ("imgurl") String imgurl ,
                     @RequestParam (value = "parentid" , required = false) String parentid
    ) throws IOException {

        DynamicsDto dynamicsDto = new DynamicsDto();
        //缩略图
        dynamicsDto.setParentid(parentid);
        //用户名
        dynamicsDto.setUserid(userid);
        //描述
        dynamicsDto.setDescription(desc);
        //视频地址
        dynamicsDto.setImgs(imgurl);

        dynamicsDto.setId(String.valueOf(new Date().getTime())+String.valueOf(Math.random()*100000));
        //发布时间
        dynamicsDto.setUptime(new Date().getTime()+"");



        return dynamicsService.addDynamics(dynamicsDto)==true?"success":"false";
    }






    //上传视频
    @RequestMapping("/upvideo")
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


//    @RequestMapping(value = "/list", method = RequestMethod.GET)
//    @ResponseBody
//    public List<DynamicsDto> courseList(HttpServletRequest request,
//                                        @RequestParam(value = "userid", required = false) String userid) {
//
//        if(StringUtils.isNotBlank(userid)){
//            return dynamicsService.listDynamicsByUser(userid);
//        }
//        return dynamicsService.listDynamics();
//    }
}
