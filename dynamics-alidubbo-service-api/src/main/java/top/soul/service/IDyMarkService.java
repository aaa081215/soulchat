package top.soul.service;

import top.soul.dto.DyMarks;
import top.soul.dto.DynamicsDto;

import java.util.List;

public interface IDyMarkService {
    List<DyMarks> listDymarksPageByd(String dyid,int pageSize, int currentPage);
    boolean addDynamics(DyMarks dyMarks);
}
