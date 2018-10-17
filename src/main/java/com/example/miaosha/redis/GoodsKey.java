package com.example.miaosha.redis;

/**
 * @Auther: 庞洋洋
 * @Date: 2018/9/30 15:00
 * @Description:
 */
public class GoodsKey extends BasePrefix{

    public GoodsKey(int expireSeconds, String prefix) {
        super(expireSeconds,prefix);
    }

    public static GoodsKey getGoodsList = new GoodsKey(60,"gl");
    public static GoodsKey getGoodsDetail = new GoodsKey(60,"gd");
    public static GoodsKey getMiaoshaGoodsStock = new GoodsKey(0,"gs");
}
