package top.soul.service;

import com.alibaba.dubbo.config.annotation.Service;

import org.springframework.beans.factory.annotation.Autowired;
import top.soul.dto.DynamicsDto;
import top.soul.mapper.DynamicsMapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Michael on 2017/11/3.
 */
@Service
public class DynamicsServiceImpl implements IDynamicsService {

    @Autowired
    private DynamicsMapper dynamicsMapper;


    @Override
    public List<DynamicsDto> listDynamicsPage(int pageSize ,int currentPage) {
        int index = (currentPage-1)*pageSize;
        return dynamicsMapper.listDynamicsPage(index,pageSize);
    }

    @Override
    public List<String> listDynamicsPageVideo(int pageSize, int currentPage) {
        int index = (currentPage-1)*pageSize;
        return dynamicsMapper.listDynamicsPageVideo(index,pageSize);
    }

    @Override
    public List<DynamicsDto> listDynamicsByUser(int UserId) {
        return dynamicsMapper.listDynamicsByUser(UserId);
    }

    @Override
    public boolean addDynamics(DynamicsDto dynamicsDto) {

        return dynamicsMapper.addDynamics(dynamicsDto);
    }

    @Override
    public boolean delDynamics(DynamicsDto dynamicsDto) {
        return dynamicsMapper.delDynamics(dynamicsDto);
    }

    @Override
    public boolean addDynamicsVideo(String urls) {
        return dynamicsMapper.addDynamicsVideo(urls);
    }

    @Override
    public boolean updateicon(String userid, String icimg) {
        return dynamicsMapper.updateicon(userid, icimg);
    }

    @Override
    public boolean updateai(String userid, String icimg) {
        return dynamicsMapper.updateai(String.valueOf(Math.random()*100)+String.valueOf(new Date().getTime()),userid, icimg,"0");
    }
}
