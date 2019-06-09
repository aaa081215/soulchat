package top.soul.cloudconsumer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/m")
public class MatchController {

    @Autowired
    private LoadBalancerClient loadBalancerClient;

    @GetMapping("/list")
    public String getlist(){
        ServiceInstance serviceInstance = loadBalancerClient.choose("MATCHPROVIDER");
        String url = String.format("http://%s:%s",serviceInstance.getHost(),serviceInstance.getPort())+"/product/list";
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(url,String.class);
        return response;
    }
}
