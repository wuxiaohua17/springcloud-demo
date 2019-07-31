package cn.com.ut.demo.feign.feigne;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

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
public class ClientEConfig {

	// @Bean
	public RequestInterceptor requestInterceptorE() {

		return template -> {
			log.debug("==ClientEConfig==");
			template.header("Authorization", "bearer 456");
		};
	}

	@Bean
	@Scope("prototype")
	public Feign.Builder feignBuilder() {

		log.debug("==feignBuilderE");
		return Feign.builder().requestInterceptor(requestInterceptorE());
	}
}
