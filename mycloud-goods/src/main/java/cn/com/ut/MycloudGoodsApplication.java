package cn.com.ut;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

@EnableDiscoveryClient
@EnableFeignClients
@SpringBootApplication
@EnableCaching
@EnableSpringDataWebSupport
// @ComponentScan(excludeFilters = {
// @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {
// GoodsService.class }) })
public class MycloudGoodsApplication {

	public static void main(String[] args) {

		new SpringApplicationBuilder(MycloudGoodsApplication.class).web(true).run(args);
	}
}
