package com.example.miaosha.service;

import com.example.miaosha.domain.MiaoshaOrder;
import com.example.miaosha.domain.MiaoshaUser;
import com.example.miaosha.domain.OrderInfo;
import com.example.miaosha.redis.MiaoshaKey;
import com.example.miaosha.redis.RedisService;
import com.example.miaosha.util.MD5Util;
import com.example.miaosha.util.UUIDUtil;
import com.example.miaosha.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;


/**
 * @Auther: 庞洋洋
 * @Date: 2018/9/28 15:09
 * @Description:
 */
@Service
public class MiaoshaService {

    @Autowired
    GoodsService goodsService;

    @Autowired
    OrderService orderService;

    @Autowired
    RedisService redisService;

    @Transactional
    public OrderInfo miaosha(MiaoshaUser user, GoodsVo goods) {
        //减少库存
        boolean success = goodsService.reduceStock(goods);

        if (success){
            //下订单 order_info miaosha_order
            return orderService.createOrder(user, goods);
        }else {
            setGoodsOver(goods.getId());
            return null;
        }

    }

    public long getMiaoshaResult(Long userId, long goodsId) {
        MiaoshaOrder order = orderService.getMiaoshaOrderByUserIdGoodsId(userId, goodsId);
        if(order != null) {//秒杀成功
            return order.getOrderId();
        }else {
            boolean isOver = getGoodsOver(goodsId);
            if(isOver) {
                return -1;
            }else {
                return 0;
            }
        }
    }


    private void setGoodsOver(Long goodsId) {
        redisService.set(MiaoshaKey.isGoodsOver, ""+goodsId, true);
    }

    private boolean getGoodsOver(long goodsId) {
        return redisService.exists(MiaoshaKey.isGoodsOver, ""+goodsId);
    }

    public boolean checkPath(MiaoshaUser user, long goodsId, String path) {
        if (user == null || StringUtils.isEmpty(path)){
            return false;
        }
        String pathOld = redisService.get(MiaoshaKey.getMiaoshaPath, ""+user.getId()+"_"+goodsId, String.class);
        return path.equals(pathOld);
    }


    public String createMiaoshaPath(MiaoshaUser user, long goodsId) {
        String str = MD5Util.md5(UUIDUtil.uuid()+"12345");
        redisService.set(MiaoshaKey.getMiaoshaPath, ""+user.getId()+"_"+goodsId, str);
        return str;
    }
}
