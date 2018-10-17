package com.example.miaosha.service;

import com.example.miaosha.dao.GoodsDao;
import com.example.miaosha.dao.UserDao;
import com.example.miaosha.domain.Goods;
import com.example.miaosha.domain.MiaoshaGoods;
import com.example.miaosha.domain.User;
import com.example.miaosha.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Auther: 庞洋洋
 * @Date: 2018/9/28 15:09
 * @Description:
 */
@Service
public class GoodsService {

    @Autowired
    GoodsDao goodsDao;

    public List<GoodsVo> listGetGoodsVo(){
        return goodsDao.listGetGoodsVo();
    }

    public GoodsVo getGoodsVoByGoodsId(Long goodsId){
        return goodsDao.getGoodsVoByGoodsId(goodsId);
    }


    public boolean reduceStock(GoodsVo goods) {
        MiaoshaGoods g = new MiaoshaGoods();
        g.setGoodsId(goods.getId());
        int ret = goodsDao.reduceStock(g);
        return ret > 0;
    }
}
