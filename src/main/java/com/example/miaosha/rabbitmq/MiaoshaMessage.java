package com.example.miaosha.rabbitmq;


import com.alibaba.fastjson.JSON;
import com.example.miaosha.domain.MiaoshaUser;

import java.io.Serializable;

public class MiaoshaMessage implements Serializable{
	private MiaoshaUser user;
	private long goodsId;
	public MiaoshaUser getUser() {
		return user;
	}
	public void setUser(MiaoshaUser user) {
		this.user = user;
	}
	public long getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(long goodsId) {
		this.goodsId = goodsId;
	}

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}
}
