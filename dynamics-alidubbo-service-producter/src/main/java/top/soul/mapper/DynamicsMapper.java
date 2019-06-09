package top.soul.mapper;

import org.apache.ibatis.annotations.*;
import top.soul.dto.DynamicsDto;

import java.util.List;

/**
 * Created by Michael on 2017/11/3.
 */
@Mapper
public interface DynamicsMapper {

    @Select("select userid,parentid,id,description,imgs,uptime from dy order by uptime desc limit #{currentIndex},#{pageSize}")
    List<DynamicsDto> listDynamicsPage(@Param("currentIndex")  int currentIndex,@Param("pageSize")  int pageSize);

    @Select("select urls from urlss order by id desc limit #{currentIndex},#{pageSize}")
    List<String> listDynamicsPageVideo(@Param("currentIndex")  int currentIndex,@Param("pageSize")  int pageSize);

    @Select("select userid,parentid,id,description,imgs,uptime from dy where id = #{UserId}")
    List<DynamicsDto> listDynamicsByUser(@Param("UserId")  int UserId);

    @Insert("insert into dy (id,userid,parentid,description,imgs,uptime) values (" +
              "#{d.id}," +
              "#{d.userid}," +
              "#{d.parentid}," +
              "#{d.description}," +
              "#{d.imgs}," +
              "#{d.uptime})")
    boolean addDynamics(@Param("d")DynamicsDto dynamicsDto);

    @Delete("delete from dy where id = #{d.id}")
    boolean delDynamics(@Param("d")DynamicsDto dynamicsDto);


    @Insert("insert into urlss (urls) values (#{urls})")
    boolean addDynamicsVideo(@Param("urls")   String urls);

    @Update("update pe_user set intro = #{icimg} where id =#{userid}")
    boolean updateicon(@Param("userid") String userid,@Param("icimg")  String icimg);

    @Insert("insert into aidate (id,name,valai,res)values( #{id}, #{name}, #{valai}, #{res})")
    boolean updateai(@Param("id") String id,@Param("name") String name,@Param("valai") String valai,@Param("res")  String res);
}
