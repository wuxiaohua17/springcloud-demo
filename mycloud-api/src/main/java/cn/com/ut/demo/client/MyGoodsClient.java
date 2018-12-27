package cn.com.ut.demo.client;

import cn.com.ut.demo.entity.Order;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author wuxiaohua
 * @version 1.0
 * @date 2018/12/26 14:21
 */
@FeignClient(value = "mycloud-goods")
public interface MyGoodsClient {

	@GetMapping(value = "/goods/getById")
	public Order getByOrderId(@RequestParam(value = "goodsId") String goodsId);
}
