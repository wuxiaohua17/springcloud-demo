package cn.com.ut;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

@EnableEurekaServer
@SpringBootApplication
public class DiscoveryApplication {

	public static void main(String[] args) {

		new SpringApplicationBuilder(DiscoveryApplication.class).web(true).run(args);
	}
}
