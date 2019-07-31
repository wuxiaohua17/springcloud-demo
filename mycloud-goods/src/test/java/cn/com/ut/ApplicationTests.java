package cn.com.ut;

import java.util.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import cn.com.ut.demo.entity.Goods;
import cn.com.ut.demo.entity.IndexExt;
import cn.com.ut.demo.entity.IndexPerf;
import cn.com.ut.demo.entity.ShopManage;
import cn.com.ut.demo.repository.EmpRepository;
import cn.com.ut.demo.repository.IndexExtRepository;
import cn.com.ut.demo.repository.IndexPerfRepository;
import cn.com.ut.demo.repository.ShopManageRepository;
import cn.com.ut.demo.service.EmpService;
import cn.com.ut.demo.service.GoodsService;
import lombok.extern.slf4j.Slf4j;

/**
 * 地方
 * 
 * @author
 * @date
 * @since
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ApplicationTests {

	@Autowired
	private GoodsService goodsService;
	@Autowired
	private IndexPerfRepository indexPerfRepository;
	@Autowired
	private IndexExtRepository indexExtRepository;
	@Autowired
	private ShopManageRepository shopManageRepository;
	@Autowired
	private EmpRepository empRepository;
	@Autowired
	private EmpService empService;

	@Test
	public void test9() {

		// System.out.println(shopManageRepository.findByManagerIdOrSubStoreId("1",
		// "1"));
		// empService.add();

		// shopManageRepository.removeByIdIn("1", "2");

		// System.out.println(shopManageRepository.existsByManagerId("1"));

		List<ShopManage> shopManages = shopManageRepository.findByManagerIdContains("sdfsfdf");
		ShopManage shopManage = new ShopManage();
		shopManage.setId("111");
		shopManages.add(shopManage);
		System.out.println("===============" + shopManages);
	}

	@Test
	public void test8() {

		System.out.println("");
		List<String> list = new ArrayList<>();
		list.add("11111");
		list.add("22222");
		System.out.println(shopManageRepository.deleteByIdIn(list));

	}

	@Test
	public void test7() {

		System.out.println(shopManageRepository.query());

		System.out.println(shopManageRepository.findByManagerIdLike("sa"));

		System.out.println(shopManageRepository.findByManagerIdContains("sa"));

		System.out.println(shopManageRepository.findByManagerIdStartsWith("sa"));

		System.out.println(shopManageRepository.findByManagerIdEndsWith("sa"));

		System.out.println(shopManageRepository.findByManagerIdIsEndingWith("sa"));
	}

	@Test
	public void test6() {

		System.out.println("");

		// ArrayBlockingQueue arrayBlockingQueue = new ArrayBlockingQueue(10);
		new Thread(() -> test5()).start();
		new Thread(() -> test5()).start();

	}

	@Test
	public void test5() {

		try {
			int count = shopManageRepository.removeByIdIn("777", "888", "999");
			System.out.println("===" + count);
		} catch (Exception ex) {
			System.out.println("===" + ex);
			throw ex;
		}

	}

	@Test
	public void test4() {

		IndexPerf indexPerf = indexPerfRepository.findOne("1");
		// indexPerf.setFirstName("珠海优特科技股份有限公司-珠海优特科技股份有限公司-珠海优特科技股份有限公司-珠海优特科技股份有限公司-珠海优特科技股份有限公司");
		indexPerf.setTimes(2147483647L);
		indexPerfRepository.save(indexPerf);
	}

	@Test
	public void test3() {

		int start = 1;
		int count = start + 20000;
		long time = System.currentTimeMillis();
		List<IndexExt> batch = new ArrayList<>();
		for (int i = start; i < count; i++) {
			IndexExt indexExt = new IndexExt();
			indexExt.setId("" + i);
			indexExt.setPerfId("" + i);
			indexExt.setExtName("ext" + i % 500);
			indexExt.setExtAmount(new Random().nextInt(Integer.MAX_VALUE));

			batch.add(indexExt);
			if (batch.size() >= 1000) {
				indexExtRepository.save(batch);
				batch.clear();
			}
		}
	}

	@Test
	public void test2() {

		int start = 10001;
		int count = start + 90000;
		long time = System.currentTimeMillis();
		List<IndexPerf> batch = new ArrayList<>();
		for (int i = start; i < count; i++) {
			Date date = new Date();
			IndexPerf indexPerf = new IndexPerf();
			indexPerf.setId("" + i);
			indexPerf.setFirstName("fn" + i % 1000);
			indexPerf.setSecondName("ln" + i % 2000);
			indexPerf
					.setUpdateTime(new Date(time - 1000 * new Random().nextInt(60 * 60 * 24 * 90)));
			indexPerf.setTotal(Long.valueOf(new Random().nextInt(Integer.MAX_VALUE)));

			batch.add(indexPerf);
			if (batch.size() >= 1000) {
				indexPerfRepository.save(batch);
				batch.clear();
			}
		}
	}

	@Test
	public void test1() {

		RestTemplate restTemplate = new RestTemplate();
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		params.put("goodsId", Arrays.asList("3"));
		params.put("goodsName", Arrays.asList("小米9"));
		params.put("keywords", Arrays.asList("小米", "红米"));
		HttpEntity httpEntity = new HttpEntity(params);

		ResponseEntity<Goods> responseEntity = restTemplate.exchange(
				"http://192.168.75.233:9030/goods/updateGoodsPut", HttpMethod.PUT, httpEntity,
				Goods.class);
		log.info("==goods==" + responseEntity.getBody());

	}
}
