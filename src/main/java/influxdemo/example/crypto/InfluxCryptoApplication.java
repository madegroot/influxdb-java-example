package influxdemo.example.crypto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class InfluxCryptoApplication {

    public static void main(String[] args) {
        SpringApplication.run(InfluxCryptoApplication.class, args);
    }
}
