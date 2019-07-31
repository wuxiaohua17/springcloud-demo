package cn.com.ut.demo.feign;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cn.com.ut.demo.entity.Goods;

/**
 * @author wuxiaohua
 * @version 1.0
 * @date 2018/12/26 14:21
 */
// @FeignClient(value = "mycloud-goods", configuration = ClientAConfig.class)
public interface GoodsClientA {

	@GetMapping(value = "/goods/getById")
	public Goods getById(@RequestParam(name = "goodsId") String goodsId);
}
