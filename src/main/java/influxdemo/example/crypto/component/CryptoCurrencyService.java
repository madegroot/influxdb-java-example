package influxdemo.example.crypto.component;

import influxdemo.example.crypto.domain.Rate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class CryptoCurrencyService {

    @Value("${crypto.url}")
    private String url;
    @Value("${crypto.currencies}")
    private String[] currencies;
    @Value("${crypto.target}")
    private String target;

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private InfluxClient influxClient;

    public void getPopularExchangeRates() {
        var rates = Stream.of(currencies)
                .map(currency -> getExchangeRate(currency, target))
                .collect(Collectors.toList());

        rates.forEach(System.out::println);
        influxClient.writeCurrencies(rates);
    }

    private Rate getExchangeRate(String cryptoCurrency, String target) {
        var endpoint = String.format(url, cryptoCurrency, target);
        System.out.println("Get rate from: " + endpoint);
        var response = restTemplate.exchange(
                endpoint,
                HttpMethod.GET,
                null,
                Rate.class);
        return response == null ? null : response.getBody();
    }
}
