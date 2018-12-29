package cn.com.ut.demo.controller;

import cn.com.ut.demo.client.GoodsClient;
import cn.com.ut.demo.client.GoodsFeign;
import cn.com.ut.demo.entity.Goods;
import cn.com.ut.demo.entity.Order;
import cn.com.ut.demo.repository.OrderRepository;
import cn.com.ut.demo.service.OrderService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.command.HystrixCommandBuilder;
import feign.Client;
import feign.Feign;
import feign.Request;
import feign.Retryer;
import feign.hystrix.HystrixFeign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.feign.support.ResponseEntityDecoder;
import org.springframework.cloud.netflix.feign.support.SpringMvcContract;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wuxiaohua
 * @since 2018/8/24
 */
@RestController
@RequestMapping(value = "/order")
@Slf4j
public class OrderController {

	@Autowired
	private OrderService orderService;
	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private GoodsClient goodsClient;
	/**
	 * LoadBalancerFeignClient
	 */
	@Autowired
	private Client client;

	@RequestMapping("/getGoodsByIdRibbon")
	public Goods getGoodsByIdRibbon(@RequestParam("goodsId") String goodsId) {

		ResponseEntity<Goods> entity = restTemplate.getForEntity(
				"http://mycloud-goods/goods/getById?goodsId={1}", Goods.class, goodsId);
		return entity.getBody();
	}

	// @HystrixCommand(fallbackMethod = "getGoodsByIdCaseFallback")
	@RequestMapping("/getGoodsByIdCase")
	public Goods getGoodsByIdCase(@RequestParam(name = "goodsId") String goodsId,
			@RequestParam(name = "exception", required = false) String exception,
			@RequestParam(name = "skipException", required = false) boolean skipException,
			@RequestParam(name = "sleep", required = false) Long sleep,
			@RequestParam(name = "statusCode", required = false) Integer statusCode,
			@RequestParam(name = "rest", required = false) String rest) {

		Goods goods = null;
		if (rest != null && "feign".equals(rest)) {
			goods = goodsClient.getGoodsByIdCase(goodsId, exception, skipException, sleep,
					statusCode);
			log.info("==goods==" + goods);
			return goods;
		}

		if (rest != null && "builder".equals(rest)) {

			HystrixCommandBuilder.builder();
			GoodsFeign goodsFeign = Feign.builder()
					// HystrixFeign.builder()
					.contract(new SpringMvcContract()).decoder(new JacksonDecoder())
					.encoder(new JacksonEncoder()).client(client)
					.options(new Request.Options(6000, 6000)).retryer(Retryer.NEVER_RETRY)
					// .target(GoodsFeign.class, "http://localhost:9030")
					.target(GoodsFeign.class, "http://mycloud-goods");
			goods = goodsFeign.getGoodsByIdCase(goodsId, exception, skipException, sleep,
					statusCode);
			return goods;
		}

		Map<String, Object> parameter = new HashMap<>();
		parameter.put("goodsId", goodsId);
		parameter.put("exception", exception);
		parameter.put("sleep", sleep);
		parameter.put("statusCode", statusCode);

		ResponseEntity<Goods> entity = restTemplate.getForEntity(
				"http://mycloud-goods/goods/getGoodsByIdCase?goodsId={goodsId}&exception={exception}&sleep={sleep}&statusCode={statusCode}",
				Goods.class, parameter);
		goods = entity.getBody();

		log.info("==goods==" + goods);
		return goods;

	}

	public Goods getGoodsByIdCaseFallback(String goodsId, String exception, boolean skipException,
			Long sleep, Integer statusCode, String rest) {

		Goods goods = new Goods();
		goods.setGoodsName("fallbackMethod");
		return goods;
	}

	/**
	 * 下单
	 * 
	 * @param orderId
	 */
	@RequestMapping("/submitOrder")
	public void submitOrder(@RequestParam String orderId) {

		orderService.submitOrder(orderId);
	}

	/**
	 * 供消息子系统RPC回查订单状态
	 * 
	 * @param orderId
	 * @return
	 */
	@GetMapping(value = "/getByOrderId")
	public Order getByOrderId(@RequestParam String orderId) {

		return orderRepository.findOne(orderId);
	}

	/**
	 * 事务消息发送消费:下单
	 * 
	 * @param orderId
	 * @return
	 */
	@RequestMapping("/addOrderBySendTxMsg")
	public Order addOrderBySendTxMsg(@RequestParam("orderId") String orderId) {

		return orderService.addOrderBySendTxMsg(orderId);
	}

	/**
	 * 普通消息发送消费：下单
	 * 
	 * @param orderId
	 * @return
	 */
	@RequestMapping("/addOrderBySendMsg")
	public Order addOrderBySendMsg(@RequestParam("orderId") String orderId) {

		return orderService.addOrderBySendMsg(orderId);
	}
}
