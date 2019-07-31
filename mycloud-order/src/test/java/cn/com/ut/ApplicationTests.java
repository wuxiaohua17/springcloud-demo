package cn.com.ut;

import java.math.BigDecimal;

import javax.persistence.EntityManager;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.netflix.feign.FeignClientsConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit4.SpringRunner;

import cn.com.ut.demo.entity.Order;
import cn.com.ut.demo.feign.GoodsClientA;
import cn.com.ut.demo.feign.GoodsClientB;
import cn.com.ut.demo.feign.GoodsClientC;
import cn.com.ut.demo.feign.feignd.ClientDConfig;
import cn.com.ut.demo.feign.feignd.GoodsClientD;
import cn.com.ut.demo.feign.feigne.ClientEConfig;
import cn.com.ut.demo.feign.feigne.GoodsClientE;
import cn.com.ut.demo.repository.OrderRepository;
import feign.Client;
import feign.Contract;
import feign.Feign;
import feign.codec.Decoder;
import feign.codec.Encoder;
import lombok.extern.slf4j.Slf4j;

//import org.hibernate.query.NativeQuery;

@RunWith(SpringRunner.class)
@SpringBootTest
@Import({ ClientDConfig.class, ClientEConfig.class })
@Slf4j
public class ApplicationTests {

	@Autowired
	private Environment environment;
	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private EntityManager entityManager;

	private GoodsClientA goodsClientA;
	private GoodsClientB goodsClientB;
	@Autowired
	private GoodsClientC goodsClientC;
	@Autowired
	private GoodsClientD goodsClientD;
	@Autowired
	private GoodsClientE goodsClientE;

	@Autowired
	private Client client;
	@Autowired
	private FeignClientsConfiguration feignClientsConfiguration;
	@Autowired
	private Decoder decoder;
	@Autowired
	private Encoder encoder;
	@Autowired
	private Contract contract;

	@Test
	public void testFeignCnf2() {

		goodsClientD.getById("1");
		goodsClientE.getById("2");
	}

	@Test
	public void testFeignCnf() {

		goodsClientA = Feign.builder().client(client).encoder(encoder).decoder(decoder)
				.contract(contract).requestInterceptor((template) -> {
					log.debug("==goodsClientA==");
					template.header("Authorization", "bearer 123");
				}).target(GoodsClientA.class, "http://mycloud-goods");

		goodsClientB = Feign.builder().client(client).encoder(encoder).decoder(decoder)
				.contract(contract).requestInterceptor((template) -> {
					log.debug("==goodsClientB==");
					template.header("Authorization", "bearer 456");
				}).target(GoodsClientB.class, "http://mycloud-goods");

		goodsClientA.getById("1");
		goodsClientB.getById("2");
		goodsClientC.getById("3");
	}

	@Test
	public void testEnv() {

		// String s = environment.getProperty("server.port");
		// System.out.println("==" + s);

		System.out.println(new BigDecimal(0.01).multiply(new BigDecimal(0.8)));
	}

	@Test
	public void testJpa() {

		Order order1 = orderRepository.getOne("31");
		order1.setGoodsNum(2);

		Order order2 = orderRepository.getByOrderId("68");
		order2.setGoodsNum(3);

		System.out.println("=======");

	}
}
