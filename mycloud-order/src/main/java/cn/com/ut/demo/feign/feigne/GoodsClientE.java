package cn.com.ut.demo.feign.feigne;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cn.com.ut.demo.entity.Goods;

/**
 * @author wuxiaohua
 * @version 1.0
 * @date 2018/12/26 14:21
 */
@FeignClient(value = "mycloud-goods", configuration = ClientEConfig.class)
public interface GoodsClientE {

	@GetMapping(value = "/goods/getById")
	public Goods getById(@RequestParam(name = "goodsId") String goodsId);
}
