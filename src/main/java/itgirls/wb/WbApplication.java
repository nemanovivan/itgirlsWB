package itgirls.wb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class WbApplication {

    public static void main(String[] args) {
        SpringApplication.run(WbApplication.class, args);
    }

}
