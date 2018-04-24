package influxdemo.example.crypto.config;

import influxdemo.example.crypto.component.CryptoCurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class ScheduledTasks {

    @Autowired
    private CryptoCurrencyService cryptoCurrencyService;

    @Scheduled(fixedRateString = "20000")
    public void getCryptoCurrencyData() {
        try {
            cryptoCurrencyService.getPopularExchangeRates();
        } catch (Exception e) {
            System.out.println("Problem during retrieval of data: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
