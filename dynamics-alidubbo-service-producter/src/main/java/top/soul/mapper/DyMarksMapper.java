package top.soul.mapper;

import org.apache.ibatis.annotations.*;
import top.soul.dto.DyMarks;

import java.util.List;

/**
 * Created by Michael on 2017/11/3.
 */
@Mapper
public interface DyMarksMapper {

    @Select("select id,videoid,videoreserve,markidreserve,markdescript,updatetime,markpeopleid,markpeoplename,markimages from dymarks where videoid = #{dyid} order by id desc limit #{currentIndex},#{pageSize}")
    List<DyMarks> listDymarksPageByd(@Param("dyid") String dyid , @Param("currentIndex") int currentIndex, @Param("pageSize") int pageSize);


    @Insert("insert into dymarks(id,videoid,videoreserve,markidreserve,markdescript,updatetime,markpeopleid,markpeoplename,markimages) values (" +
              "#{d.id}," +
              "#{d.videoid}," +
              "#{d.videoreserve}," +
              "#{d.markidreserve}," +
              "#{d.markdescript}," +
              "#{d.updatetime}," +
              "#{d.markpeopleid}," +
              "#{d.markpeoplename}," +
              "#{d.markimages})")
    boolean addDynamics(@Param("d") DyMarks dynamicsDto);

}
