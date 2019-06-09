package top.soul.cloudprovider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class CloudProviderApplication {

	public static void main(String[] args) {
		SpringApplication.run(CloudProviderApplication.class, args);
	}

}
