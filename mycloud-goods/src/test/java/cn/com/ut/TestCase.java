package cn.com.ut;

import java.util.Arrays;
import java.util.Date;
import java.util.Optional;
import java.util.Random;

import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import cn.com.ut.demo.entity.Goods;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author wuxiaohua
 * @since 2018/7/10
 */
@Slf4j
public class TestCase {

	@Getter
	@Setter
	static class QueryInfo<T> {

	}

	@Test
	public void test3() {

		Date date = null;
		System.out.println(Optional.ofNullable(date).orElse(null));
	}

	@Test
	public void test2() {

		int l = 1000 * 60 * 60 * 24 * 90;
		for (int i = 0; i < 100; i++) {
			System.out.println(new Random().nextInt(60 * 60 * 24 * 90));
		}
	}

	@Test
	public void test1() {

		RestTemplate restTemplate = new RestTemplate();
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		params.put("goodsId", Arrays.asList("3"));
		params.put("goodsName", Arrays.asList("小米9"));
		params.put("keywords", Arrays.asList("小米", "红米"));
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded");
		HttpEntity httpEntity = new HttpEntity(params, httpHeaders);
		ResponseEntity<Goods> responseEntity = restTemplate.exchange(
				"http://192.168.75.233:9030/goods/updateGoodsPut", HttpMethod.PUT, httpEntity,
				Goods.class);
		log.info("==goods==" + responseEntity.getBody());

	}
}
