package top.soul.cloudprovider.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
@RestController
@RequestMapping("/product")
public class MatchController {
    @GetMapping("/list")
    public List<String> getlist(){
        List<String> list = new ArrayList<>();
        list.add("成功");
        return list;
    }
}
