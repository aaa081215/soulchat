package top.soul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * Created by Michael on 2017/10/28.
 */
@SpringBootApplication
@EnableZuulProxy
public class ZuulServiceApplication {

    public static void main(String args[]) {
        SpringApplication.run(ZuulServiceApplication.class, args);
    }
}
