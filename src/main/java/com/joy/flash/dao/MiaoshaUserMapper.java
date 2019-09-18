package com.joy.flash.dao;

import com.joy.flash.model.MiaoshaUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * Created by SongLiang on 2019-09-10
 */
@Mapper
public interface MiaoshaUserMapper {

    @Select("select * from miaosha_user where id = #{id}")
    MiaoshaUser getById(long id);

    @Update("update miaosha_user set password = #{password} where id = #{id}")
    void update(MiaoshaUser toBeUpdate);
}
