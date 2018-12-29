package cn.com.ut;

import feign.Feign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@EnableCircuitBreaker
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class MycloudOrderApplication {

	@Autowired
	private Environment env;

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
