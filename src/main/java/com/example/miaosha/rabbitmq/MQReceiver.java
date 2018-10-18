//package com.example.miaosha.rabbitmq;
//
//import com.example.miaosha.domain.MiaoshaOrder;
//import com.example.miaosha.domain.MiaoshaUser;
//import com.example.miaosha.redis.RedisService;
//import com.example.miaosha.result.CodeMsg;
//import com.example.miaosha.result.Result;
//import com.example.miaosha.service.GoodsService;
//import com.example.miaosha.service.MiaoshaService;
//import com.example.miaosha.service.OrderService;
//import com.example.miaosha.vo.GoodsVo;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//
//@Service
//public class MQReceiver {
//
//		private static Logger log = LoggerFactory.getLogger(MQReceiver.class);
//
//		@Autowired
//		RedisService redisService;
//
//		@Autowired
//		GoodsService goodsService;
//
//		@Autowired
//		OrderService orderService;
//
//		@Autowired
//		MiaoshaService miaoshaService;
//
//		@RabbitListener(queues=MQConfig.MIAOSHA_QUEUE)
//		public void receive(String message) {
//			log.info("receive message:"+message);
//			MiaoshaMessage mm  = RedisService.stringToBean(message, MiaoshaMessage.class);
//			MiaoshaUser user = mm.getUser();
//			long goodsId = mm.getGoodsId();
//
//			GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
//	    	int stock = goods.getStockCount();
//	    	if(stock <= 0) {
//	    		return;
//	    	}
//	    	//判断是否已经秒杀到了
//	    	MiaoshaOrder order = orderService.getMiaoshaOrderByUserIdGoodsId(user.getId(), goodsId);
//	    	if(order != null) {
//	    		return;
//	    	}
//	    	//减库存 下订单 写入秒杀订单
//	    	miaoshaService.miaosha(user, goods);
//		}
//
//	/**
//	 * orderId：成功
//	 * -1：秒杀失败
//	 * 0： 排队中
//	 * */
//	@RequestMapping(value="/result", method= RequestMethod.GET)
//	@ResponseBody
//	public Result<Long> miaoshaResult(Model model, MiaoshaUser user,
//									  @RequestParam("goodsId")long goodsId) {
//		model.addAttribute("user", user);
//		if(user == null) {
//			return Result.error(CodeMsg.SESSION_ERROR);
//		}
//		long result  =miaoshaService.getMiaoshaResult(user.getId(), goodsId);
//		return Result.success(result);
//	}
	
//		@RabbitListener(queues=MQConfig.QUEUE)
//		public void receive(String message) {
//			log.info("receive message:"+message);
//		}
//		
//		@RabbitListener(queues=MQConfig.TOPIC_QUEUE1)
//		public void receiveTopic1(String message) {
//			log.info(" topic  queue1 message:"+message);
//		}
//		
//		@RabbitListener(queues=MQConfig.TOPIC_QUEUE2)
//		public void receiveTopic2(String message) {
//			log.info(" topic  queue2 message:"+message);
//		}
//		
//		@RabbitListener(queues=MQConfig.HEADER_QUEUE)
//		public void receiveHeaderQueue(byte[] message) {
//			log.info(" header  queue message:"+new String(message));
//		}
//


		
//}
