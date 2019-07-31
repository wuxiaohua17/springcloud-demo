package cn.com.ut;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.feign.FeignClientsConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import cn.com.ut.demo.feign.feignd.ClientDConfig;
import cn.com.ut.demo.feign.feigne.ClientEConfig;
import feign.RequestInterceptor;
import lombok.extern.slf4j.Slf4j;

@EnableCircuitBreaker
@EnableFeignClients(basePackages = { "cn.com.ut" })
@Import(FeignClientsConfiguration.class)
@EnableDiscoveryClient
@SpringBootApplication
@ComponentScan(includeFilters = {
		@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = { ClientDConfig.class,
				ClientEConfig.class }) })
@Slf4j
public class MycloudOrderApplication {

	@Autowired
	private Environment env;

	@Bean
	public RequestInterceptor requestInterceptor() {

		return (template) -> {
			log.debug("==requestInterceptor==");
			template.header("Authorization", "bearer 789");
		};
	}

	@Bean
	@LoadBalanced
	public RestTemplate restTemplate() {

		SimpleClientHttpRequestFactory simpleClientHttpRequestFactory = new SimpleClientHttpRequestFactory();
		simpleClientHttpRequestFactory
				.setConnectTimeout(env.getProperty("resttemplate.ConnectTimeout", int.class));
		simpleClientHttpRequestFactory
				.setReadTimeout(env.getProperty("resttemplate.ReadTimeout", int.class));
		return new RestTemplate(simpleClientHttpRequestFactory);
	}

	public static void main(String[] args) {

		new SpringApplicationBuilder(MycloudOrderApplication.class).web(true).run(args);
	}
}
