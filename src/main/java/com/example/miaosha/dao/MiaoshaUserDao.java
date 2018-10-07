package com.example.miaosha.dao;


import com.example.miaosha.domain.MiaoshaUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface MiaoshaUserDao {

    @Select("select * from miaosha_user where id = #{id} ")
    public MiaoshaUser getById(@Param("id") Long id);
}
