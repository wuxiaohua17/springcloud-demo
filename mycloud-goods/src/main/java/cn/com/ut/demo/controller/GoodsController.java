package cn.com.ut.demo.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.web.bind.annotation.*;

import cn.com.ut.demo.dto.GoodsDTO;
import cn.com.ut.demo.dto.GoodsUpdateDTO;
import cn.com.ut.demo.entity.Goods;
import cn.com.ut.demo.repository.GoodsRepository;
import cn.com.ut.demo.service.GoodsService;
import lombok.extern.slf4j.Slf4j;

/**
 * @author wuxiaohua
 * @since 2018/10/22
 */
@RestController
@RequestMapping("/goods")
@Slf4j
public class GoodsController implements ApplicationEventPublisherAware {

	private AtomicInteger atomicInteger = new AtomicInteger(1);

	@Autowired
	private GoodsRepository goodsRepository;

	@Autowired
	private GoodsService goodsService;

	private ApplicationEventPublisher applicationEventPublisher;

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
		goods.setUpdateTime(new Date());
		goodsRepository.save(goods);
	}

	/**
	 * 添加商品
	 *
	 * @param goodsId
	 * @param goodsName
	 */
	@RequestMapping("/insertSelect")
	public void insertSelect(@RequestParam(name = "goodsId") String goodsId,
			@RequestParam(name = "goodsName") String goodsName) {

		Goods goods = new Goods();
		goods.setGoodsId(goodsId);
		goods.setGoodsName(goodsName);
		goods.setGoodsPrice(new BigDecimal("55.50"));
		goods.setGoodsStock(1000);
		goodsRepository.insertSelect(goods.getGoodsName(), goods.getGoodsId());

	}

	/**
	 * 修改商品
	 *
	 * @param goodsId
	 * @param goodsName
	 */
	@RequestMapping("/updateGoodsVersion3")
	@Transactional
	public void updateGoodsVersion3(@RequestParam(name = "goodsId") String goodsId,
			@RequestParam(name = "goodsName") String goodsName) {

		Goods goods = goodsRepository.findByGoodsId(goodsId);
		goods.setGoodsName(goodsName);

		int count1 = goodsRepository.updateGoodsById(goodsName + "2", goodsId, goods.getVersion());
		log.warn("====count1====" + count1);

		try {
			// 等待前一个事务提交，版本号增加
			Thread.sleep(50);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 修改商品
	 *
	 * @param goodsId
	 * @param goodsName
	 */
	@RequestMapping("/updateGoodsVersion2")
	@Transactional
	public void updateGoodsVersion2(@RequestParam(name = "goodsId") String goodsId,
			@RequestParam(name = "goodsName") String goodsName) {

		Goods goods = goodsRepository.findByGoodsId(goodsId);
		// goodsRepository.updateGoodsById(goodsName, goodsId);
		// int count = goodsRepository.updateGoodsById(goodsName, goodsId,
		// goods.getVersion());
		goods.setGoodsName(goodsName);

		// 模拟第二个线程查询完毕，这个不能直接用Goods或者根据findByGoodsId进行查询得到
		Goods goods2 = new Goods();
		BeanUtils.copyProperties(goods, goods2);

		// 即使使用saveAndFlush，事务不会立即提交
		goodsRepository.saveAndFlush(goods);
		new Thread(() -> {

			try {
				// 等待前一个事务提交，版本号增加
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			// 期望结果 更新记录为0
			int count2 = goodsRepository.updateGoodsById(goodsName + " 2", goodsId,
					goods2.getVersion());
			log.warn("====count2====" + count2);

		}).start();
	}

	/**
	 * 修改商品
	 *
	 * @param goodsId
	 * @param goodsName
	 */
	@RequestMapping("/updateGoodsVersion")
	@Transactional
	public void updateGoodsVersion(@RequestParam(name = "goodsId") String goodsId,
			@RequestParam(name = "goodsName") String goodsName) {

		Goods goods = goodsRepository.findByGoodsId(goodsId);
		goods.setGoodsName(goodsName);
		goodsRepository.save(goods);

		new Thread(() -> {
			Goods goods2 = goodsRepository.findByGoodsId(goodsId);
			goods2.setGoodsName(goodsName + " 2");

			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			goodsRepository.save(goods2);
		}).start();
	}

	/**
	 * 修改商品
	 *
	 * @param goodsId
	 * @param goodsName
	 */
	@RequestMapping("/updateGoods")
	@Transactional
	public void updateGoods(@RequestParam(name = "goodsId") String goodsId,
			@RequestParam(name = "goodsName") String goodsName) {

		Goods goods = goodsRepository.findByGoodsId(goodsId);
		log.info("====" + goods);
		goods.setGoodsName(goodsName);
		goodsRepository.saveAndFlush(goods);

		applicationEventPublisher.publishEvent(new ApplicationEvent(goodsId) {
		});

		// 不能读取更新数据
		new Thread(() -> {
			Goods goods3 = goodsRepository.findByGoodsId(goodsId);
			log.info("====" + goods3);
		}).start();

		// 能读取更新数据
		Goods goods2 = goodsRepository.findByGoodsId(goodsId);
		// log.info("====" + goods2);

		// 能读取更新数据
		TransactionSynchronizationManager
				.registerSynchronization(new TransactionSynchronizationAdapter() {
					@Override
					public void afterCommit() {

						new Thread(() -> {
							Goods goods3 = goodsRepository.findByGoodsId(goodsId);
							// log.info("====" + goods3);
						}).start();
					}
				});
	}

	/**
	 * 修改商品
	 *
	 * @param goodsId
	 * @param goodsDTO
	 */
	@RequestMapping("/updateGoodsPut")
	@Transactional
	public Goods updateGoodsPut(String goodsId, GoodsDTO goodsDTO) {

		log.info("==goodsId==" + goodsId);
		log.info("==goodsDTO==" + goodsDTO);
		Goods goods = goodsRepository.findByGoodsId(goodsId);
		goods.setGoodsName(goodsDTO.getGoodsName());
		goodsRepository.saveAndFlush(goods);
		return goods;
	}

	/**
	 * 修改商品
	 *
	 * @param goodsUpdateDTO
	 */
	@RequestMapping("/updateGoodsName")
	// @Transactional
	public Goods updateGoodsName(GoodsUpdateDTO goodsUpdateDTO) {

		log.info("==goodsUpdateDTO==" + goodsUpdateDTO);
		Goods goods = goodsRepository.findByGoodsId(goodsUpdateDTO.getGoodsId());
		// goods.setGoodsName(goodsUpdateDTO.getGoodsName());
		// goodsRepository.saveAndFlush(goods);
		return goods;
	}

	// 能读取更新数据
	@TransactionalEventListener
	public void afterUpdateGoods(ApplicationEvent applicationEvent) {

		new Thread(() -> {
			Goods goods3 = goodsRepository.findByGoodsId(applicationEvent.getSource().toString());
			// log.info("====" + goods3);
		}).start();
	}

	/**
	 * 查询商品
	 *
	 * @param goodsId
	 * @return
	 */
	// @Cacheable(cacheNames = "orderdemo", key = "'getById/'+#root.args[0]",
	// condition = "#root.args[0] == '1'", sync = true)
	// @Cacheable(cacheNames = "orderdemo", key = "'getById/'+#goodsId")
	@GetMapping("/getById")
	public Goods getById(@RequestParam(name = "goodsId") String goodsId) {

		log.debug("==getById from cache miss==" + atomicInteger.getAndAdd(1));
		try {
			TimeUnit.MILLISECONDS.sleep(200);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return goodsService.getById(goodsId);
	}

	@Cacheable(cacheNames = "orderdemo", key = "'find/'+#pageable.pageNumber")
	@GetMapping("/find")
	public List<Goods> find(Pageable pageable) {

		log.debug("==page==" + pageable.toString());
		return goodsRepository.findAll(pageable).getContent();
	}

	/**
	 * 查询商品
	 *
	 * @param goodsId
	 * @return
	 */
	@Caching(evict = { @CacheEvict(cacheNames = "orderdemo", key = "'getById/'+#goodsId"),
			@CacheEvict(cacheNames = "orderdemo", key = "'find/0'") })
	@GetMapping("/deleteById")
	public void deleteById(@RequestParam(name = "goodsId") String goodsId) {

		log.debug("==deleteById from cache==");
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
			if (!skipException) {
				throw ex;
			}
		}

		return goods;
	}

	@Override
	public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {

		this.applicationEventPublisher = applicationEventPublisher;
	}
}
