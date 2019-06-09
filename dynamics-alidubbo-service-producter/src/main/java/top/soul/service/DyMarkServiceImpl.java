package top.soul.service;

import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import top.soul.dto.DyMarks;
import top.soul.mapper.DyMarksMapper;

import java.util.List;
@Service
public class DyMarkServiceImpl implements IDyMarkService {
    @Autowired
    private DyMarksMapper dyMarksMapper;
    @Override
    public List<DyMarks> listDymarksPageByd(String dyid, int currentPage, int pageSize) {
        int index = (currentPage-1)*pageSize;
        return dyMarksMapper.listDymarksPageByd(dyid,index,pageSize);
    }

    @Override
    public boolean addDynamics(DyMarks dyMarks) {
        return dyMarksMapper.addDynamics(dyMarks);
    }
}
