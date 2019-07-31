package cn.com.ut.demo.service;

import cn.com.ut.demo.entity.Goods;
import cn.com.ut.demo.entity.Order;
import cn.com.ut.demo.repository.GoodsRepository;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

;

/**
 * @author wuxiaohua
 * @since 2018/8/24
 */
@Service
@Slf4j
public class GoodsService {

	@Autowired
	private GoodsRepository goodsRepository;

	public Goods getOne(String goodsId) {

		return getById(goodsId);
	}

	@Transactional
	public Goods getById(String goodsId) {

		return goodsRepository.findByGoodsId(goodsId);
	}

	/**
	 * 根据RocketMQ发送消息，更新商品销量
	 * 
	 * @param order
	 */
	@Transactional
	public void updateGoodsSale(Order order) {

		Goods goods = goodsRepository.findOne(order.getGoodsId());
		goods.setUpdateTime(new Date());
		goods.setGoodsSale(goods.getGoodsSale() + order.getGoodsNum());
		goodsRepository.save(goods);
	}

	public Goods getGoodsByIdCase(String goodsId, String exception, boolean skipException,
			Long sleep, Integer statusCode) {

		// 异常
		if (exception != null && !exception.trim().equals("")) {
			if ("npe".equals(exception))
				throw new NullPointerException("NullPointerException");
			else
				throw new RuntimeException("RuntimeException");
		}

		// sleep
		if (sleep != null && sleep > 0L) {

			try {
				TimeUnit.MILLISECONDS.sleep(sleep);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		return getById(goodsId);
	}
}
