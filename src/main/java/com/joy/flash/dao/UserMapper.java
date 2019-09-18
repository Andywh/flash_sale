package com.joy.flash.dao;

import com.joy.flash.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * Created by SongLiang on 2019-09-09
 */
@Mapper
public interface UserMapper {

    @Select("select * from user where id = #{id}")
    User getById(@Param("id") int id);

    @Insert("insert into user(id, name) values(#{id}, #{name})")
    public int insert(User user);

}
