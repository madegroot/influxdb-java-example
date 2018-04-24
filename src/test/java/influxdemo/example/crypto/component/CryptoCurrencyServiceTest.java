package influxdemo.example.crypto.component;

import influxdemo.example.crypto.InfluxCryptoApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = InfluxCryptoApplication.class)
public class CryptoCurrencyServiceTest {

    @Autowired
    private CryptoCurrencyService cryptoCurrencyService;

    @Test
    public void getPopularExchangeRates() {
        cryptoCurrencyService.getPopularExchangeRates();
    }
}