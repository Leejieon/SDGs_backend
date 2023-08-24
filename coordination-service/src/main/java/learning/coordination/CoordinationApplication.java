package learning.coordination;

import jdk.jfr.Enabled;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class CoordinationApplication {
    public static void main(String[] args) {
        SpringApplication.run(learning.coordination.CoordinationApplication.class, args);
    }
}
