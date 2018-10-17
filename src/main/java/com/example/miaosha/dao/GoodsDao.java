package com.example.miaosha.dao;


import com.example.miaosha.domain.Goods;
import com.example.miaosha.domain.MiaoshaGoods;
import com.example.miaosha.domain.User;
import com.example.miaosha.vo.GoodsVo;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface GoodsDao {

    @Select("select g.* , mg.miaosha_price , mg.stock_count , mg.start_date , mg.end_date from miaosha_goods mg left join goods g on mg.goods_id = g.id  ")
    List<GoodsVo> listGetGoodsVo();

    @Select("select g.* , mg.miaosha_price , mg.stock_count , mg.start_date , mg.end_date from miaosha_goods mg left join goods g on mg.goods_id = g.id where g.id = #{goodsId} ")
    GoodsVo getGoodsVoByGoodsId(@Param("goodsId")Long goodsId);

    @Update(" update miaosha_goods set stock_count = stock_count-1 where goods_id = #{goodsId} and stock_count > 0")
    Integer reduceStock(MiaoshaGoods goods);
}
