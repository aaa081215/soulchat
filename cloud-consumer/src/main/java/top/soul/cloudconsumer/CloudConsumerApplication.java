package top.soul.cloudconsumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class CloudConsumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CloudConsumerApplication.class, args);
	}

}
