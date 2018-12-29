package cn.com.ut;

import feign.Contract;
import feign.Feign;
import feign.Retryer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * @author wuxiaohua
 * @version 1.0
 * @date 2018/12/28 17:04
 */
@Configuration
public class FeignConfig {

	@Bean
	@Scope("prototype")
	public Feign.Builder feignBuilder() {

		return Feign.builder();
	}

	@Bean
	Retryer feignRetry() {

		return Retryer.NEVER_RETRY;
	}

	// @Bean
	public Contract feignContract() {

		return new feign.Contract.Default();
	}
}
