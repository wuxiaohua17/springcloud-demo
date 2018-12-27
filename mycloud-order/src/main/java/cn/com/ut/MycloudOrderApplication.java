package cn.com.ut;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@EnableCircuitBreaker
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class MycloudOrderApplication {

	@Bean
	@LoadBalanced
	public RestTemplate restTemplate() {

		return new RestTemplate();
	}

	public static void main(String[] args) {

		new SpringApplicationBuilder(MycloudOrderApplication.class).web(true).run(args);
	}
}
