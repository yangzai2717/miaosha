package com.example.miaosha.dao;


import com.example.miaosha.domain.User;
import com.example.miaosha.vo.GoodsVo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface GoodsDao {

    @Select("select g.* , mg.miaosha_price , mg.stock_count , mg.start_date , mg.end_date from miaosha_goods mg left join goods g on mg.goods_id = g.id  ")
    public List<GoodsVo> listGetGoodsVo();

}
