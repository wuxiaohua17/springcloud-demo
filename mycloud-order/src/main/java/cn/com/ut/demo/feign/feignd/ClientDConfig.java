package cn.com.ut.demo.feign.feignd;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

import feign.Contract;
import feign.Feign;
import feign.RequestInterceptor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author wuxiaohua
 * @version 1.0
 * @date 2019/05/09 09:22
 */
@Slf4j
// @Configuration
public class ClientDConfig {

	// @Bean
	public RequestInterceptor requestInterceptorD() {

		return template -> {
			log.debug("==ClientDConfig==");
			template.header("Authorization", "bearer 123");
		};

	}

	// @Bean
	public Contract feignContract() {

		return new feign.Contract.Default();
	}

	@Bean
	@Scope("prototype")
	public Feign.Builder feignBuilder() {

		log.debug("==feignBuilder D");
		return Feign.builder().requestInterceptor(requestInterceptorD());
	}
}
