package cn.com.ut.demo.controller;

import cn.com.ut.demo.entity.Goods;
import cn.com.ut.demo.repository.GoodsRepository;
import cn.com.ut.demo.service.GoodsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

/**
 * @author wuxiaohua
 * @since 2018/10/22
 */
@RestController
@RequestMapping("/goods")
@Slf4j
public class GoodsController {

	@Autowired
	private GoodsRepository goodsRepository;

	@Autowired
	private GoodsService goodsService;

	@ExceptionHandler
	@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
	public void exceptionHandler(RuntimeException ex) {

	}

	@ExceptionHandler
	@ResponseStatus(value = HttpStatus.OK)
	public void exceptionHandler(NullPointerException ex) {

	}

	/**
	 * 添加商品
	 * 
	 * @param goodsId
	 * @param goodsName
	 */
	@RequestMapping("/addGoods")
	public void addGoods(@RequestParam(name = "goodsId") String goodsId,
			@RequestParam(name = "goodsName") String goodsName) {

		Goods goods = new Goods();
		goods.setGoodsId(goodsId);
		goods.setGoodsName(goodsName);
		goods.setGoodsPrice(new BigDecimal("55.50"));
		goods.setGoodsStock(1000);
		goodsRepository.save(goods);
	}

	/**
	 * 查询商品
	 *
	 * @param goodsId
	 * @return
	 */
	@GetMapping("/getById")
	public Goods getById(@RequestParam(name = "goodsId") String goodsId) {

		return goodsService.getById(goodsId);
	}

	/**
	 * 查询商品
	 *
	 * @param goodsId
	 * @return
	 */
	@GetMapping("/getGoodsByIdCase")
	public Goods getGoodsByIdCase(@RequestParam(name = "goodsId") String goodsId,
			@RequestParam(name = "exception", required = false) String exception,
			@RequestParam(name = "skipException", required = false) boolean skipException,
			@RequestParam(name = "sleep", required = false) Long sleep,
			@RequestParam(name = "statusCode", required = false) Integer statusCode,
			HttpServletResponse response) {

		// statusCode
		if (statusCode != null && statusCode != HttpServletResponse.SC_OK) {
			// response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			try {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		Goods goods = null;

		try {
			goods = goodsService.getGoodsByIdCase(goodsId, exception, skipException, sleep,
					statusCode);
		} catch (Exception ex) {
			if (!skipException)
				throw ex;
		}

		return goods;
	}

}
