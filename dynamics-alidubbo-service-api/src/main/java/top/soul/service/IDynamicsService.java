package top.soul.service;

import top.soul.dto.DynamicsDto;

import java.util.List;

public interface IDynamicsService {
    List<DynamicsDto> listDynamicsPage(int pageSize, int currentPage);

    List<String> listDynamicsPageVideo(int pageSize, int currentPage);

    List<DynamicsDto> listDynamicsByUser(int UserId);

    boolean addDynamics(DynamicsDto dynamicsDto);

    boolean delDynamics(DynamicsDto dynamicsDto);

    boolean addDynamicsVideo(String urls);

    boolean updateicon(String userid,String icimg);

    boolean updateai(String userid,String icimg);
}
