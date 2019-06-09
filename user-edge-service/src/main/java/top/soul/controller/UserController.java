package top.soul.controller;

import org.apache.commons.lang.StringUtils;
import org.apache.thrift.TException;
import org.apache.tomcat.util.buf.HexUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import top.soul.dto.UserDTO;
import top.soul.mq.MessageMq;
import top.soul.redis.RedisClient;
import top.soul.response.LoginResponse;
import top.soul.response.Response;
import top.soul.thrift.ServiceProvider;
import top.soul.thrift.user.UserInfo;

import java.security.MessageDigest;
import java.util.Random;

/**
 * Created by Michael on 2017/10/30.
 */
@Controller
@RequestMapping("/user")
@CrossOrigin(origins = "*",maxAge = 3600)
public class UserController {

    @Autowired
    private ServiceProvider serviceProvider;

    @Autowired
    private RedisClient redisClient;

    @Autowired
    private MessageMq messageMq;

    //http://localhost:8082/user/user
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    @ResponseBody
    public String login() {
        return "stat:0 ok";
    }

    //登陆
    //http://localhost:8082/user/login?username=123456&password=123456
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    @ResponseBody
    public Response login(@RequestParam("username") String username,
                          @RequestParam("password") String password) {

        //1. 验证用户名密码
        UserInfo userInfo = null;
        try {
            userInfo = serviceProvider.getUserService().getUserByName(username);
        } catch (TException e) {
            e.printStackTrace();
            return Response.USERNAME_PASSWORD_INVALID;
        }
        if (userInfo == null) {
            return Response.USERNAME_PASSWORD_INVALID;
        }
        if (!userInfo.getPassword().equalsIgnoreCase(md5(password))) {
            return Response.USERNAME_PASSWORD_INVALID;
        }

        //2. 生成token
        String token = genToken();

        //3. 缓存用户
        redisClient.set(token, toDTO(userInfo), 36000);

        return new LoginResponse(token);
    }
    //发送验证码
    //http://localhost:8082/user/sendVerifyCode?email=1203269511@qq.com

    @RequestMapping(value = "/sendVerifyCode", method = RequestMethod.GET)
    @ResponseBody
    public Response sendVerifyCode(@RequestParam(value = "mobile", required = false) String mobile,
                                   @RequestParam(value = "email", required = false) String email) {

        String message = "";
        String code = randomCode("0123456789", 6);
        try {

            boolean result = false;
            if (StringUtils.isNotBlank(mobile)) {
                // TODO: 2019/3/6 0006 短信验证
                redisClient.set(mobile, code);
            } else if (StringUtils.isNotBlank(email)) {
                result =messageMq.sendMq(email,message + code);
                redisClient.set(email, code);
            } else {
                return Response.MOBILE_OR_EMAIL_REQUIRED;
            }

            if (!result) {
                return Response.SEND_VERIFYCODE_FAILED;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.exception(e);
        }

        return Response.SUCCESS;

    }
    //注册
    //http://localhost:8082/user/register?username=t&password=t&email=1203269511@qq.com&verifyCode=
    @RequestMapping(value = "/register", method = RequestMethod.GET)
    @ResponseBody
    public Response register(@RequestParam("username") String username,
                             @RequestParam("password") String password,
                             @RequestParam(value = "mobile", required = false) String mobile,
                             @RequestParam(value = "email", required = false) String email,
                             @RequestParam("verifyCode") String verifyCode) {

        if (StringUtils.isBlank(mobile) && StringUtils.isBlank(email)) {
            return Response.MOBILE_OR_EMAIL_REQUIRED;
        }

        if (StringUtils.isNotBlank(mobile)) {
            String redisCode = redisClient.get(mobile);
            if (!verifyCode.equals(redisCode)) {
                return Response.VERIFY_CODE_INVALID;
            }
        } else {
            String redisCode = redisClient.get(email);
            if (!verifyCode.equals(redisCode)) {
                return Response.VERIFY_CODE_INVALID;
            }
        }
        UserInfo userInfo = new UserInfo();
        userInfo.setUsername(username);
        userInfo.setPassword(md5(password));
        userInfo.setMobile(mobile);
        userInfo.setEmail(email);

        try {
            serviceProvider.getUserService().regiserUser(userInfo);
        } catch (TException e) {
            e.printStackTrace();
            return Response.exception(e);
        }

        return Response.SUCCESS;
    }
    //查看用户信息
    //http://localhost:8082/user/authentication?token=
    @RequestMapping(value = "/authentication", method = RequestMethod.GET)
    @ResponseBody
    public UserDTO authentication(@RequestHeader("token") String token) {
        UserDTO userDTO = redisClient.get(token);
       String icon =  redisClient.get("user_"+userDTO.getId());
       if(icon!=null){
           userDTO.setIntro(icon);
       }
        return userDTO;
    }

    private UserDTO toDTO(UserInfo userInfo) {
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(userInfo, userDTO);
        return userDTO;
    }

    private String genToken() {
        return randomCode("0123456789abcdefghijklmnopqrstuvwxyz", 32);
    }

    private String randomCode(String s, int size) {
        StringBuilder result = new StringBuilder(size);

        Random random = new Random();
        for (int i = 0; i < size; i++) {
            int loc = random.nextInt(s.length());
            result.append(s.charAt(loc));
        }
        return result.toString();
    }

    private String md5(String password) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] md5Bytes = md5.digest(password.getBytes("utf-8"));
            return HexUtils.toHexString(md5Bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
