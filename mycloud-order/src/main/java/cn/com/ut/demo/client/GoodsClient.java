package cn.com.ut.demo.client;

import cn.com.ut.FeignConfig;
import cn.com.ut.demo.entity.Goods;
import cn.com.ut.demo.entity.Order;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author wuxiaohua
 * @version 1.0
 * @date 2018/12/26 14:21
 */
@FeignClient(value = "mycloud-goods", configuration = {
		FeignConfig.class }, fallback = GoodsClientFallback.class)
public interface GoodsClient {

	@GetMapping(value = "/goods/getGoodsByIdCase")
	public Goods getGoodsByIdCase(@RequestParam(name = "goodsId") String goodsId,
			@RequestParam(name = "exception", required = false) String exception,
			@RequestParam(name = "skipException", required = false) boolean skipException,
			@RequestParam(name = "sleep", required = false) Long sleep,
			@RequestParam(name = "statusCode", required = false) Integer statusCode);
}
