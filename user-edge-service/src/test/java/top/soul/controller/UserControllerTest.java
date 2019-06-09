package top.soul.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.soul.redis.RedisClient;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest


public class UserControllerTest {

    @Autowired
    private RedisClient redisClient;

    @Test
    public void authentication() {
       Object s =  redisClient.get("3lee4m8l2j8duslw0rjmik58i1octbib");
    }
}