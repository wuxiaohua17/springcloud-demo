package cn.com.ut;

import de.codecentric.boot.admin.config.EnableAdminServer;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@EnableAdminServer
@SpringBootApplication
public class AdminApplication {

	public static void main(String[] args) {

		new SpringApplicationBuilder(AdminApplication.class).web(true).run(args);
	}
}
