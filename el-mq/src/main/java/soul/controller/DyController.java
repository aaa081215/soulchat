package soul.controller;


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
import top.soul.dto.DynamicsDto;
import top.soul.service.IDynamicsService;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;


/**
 * Created by Michael on 2017/11/4.
 */
@Controller
@RequestMapping("/es")
@CrossOrigin(origins = "*", maxAge = 3600)
public class DyController {

    @RequestMapping("/lit")
    @ResponseBody
    public String list(
    ) throws IOException {

        return null;
    }

}
