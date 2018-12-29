package cn.com.ut.demo.client;

import cn.com.ut.demo.entity.Goods;
import org.springframework.stereotype.Component;

/**
 * @author wuxiaohua
 * @version 1.0
 * @date 2018/12/28 18:20
 */
@Component
public class GoodsClientFallback implements GoodsClient {

	@Override
	public Goods getGoodsByIdCase(String goodsId, String exception, boolean skipException,
			Long sleep, Integer statusCode) {

		Goods goods = new Goods();
		goods.setGoodsName("goodsFallback");
		return goods;
	}
}
