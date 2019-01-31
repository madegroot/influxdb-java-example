package influxdemo.example.crypto.component;

import influxdemo.example.crypto.domain.Rate;
import okhttp3.OkHttpClient;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.BatchPoints;
import org.influxdb.dto.Pong;
import org.influxdb.dto.Query;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class InfluxClient {

    @Value("${influx.url}")
    private String url;
    @Value("${influx.username}")
    private String username;
    @Value("${influx.password}")
    private String password;
    @Value("${influx.loglevel}")
    private String logLevel = "BASIC";
    @Value("${influx.connectTimeout}")
    private int connectTimeout = 10;
    @Value("${influx.readTimeout}")
    private int readTimeout = 30;
    @Value("${influx.writeTimeout}")
    private int writeTimeout = 10;
    @Value("${influx.gzipEnabled}")
    private boolean gzipEnabled = false;
    @Value("${influx.query.resultLimit}")
    private int queryResultLimit;

    private static InfluxDB influxDB;

    @PostConstruct
    private void initialisation() {
        var client = new OkHttpClient.Builder()
                .connectTimeout(connectTimeout, TimeUnit.SECONDS)
                .writeTimeout(readTimeout, TimeUnit.SECONDS)
                .readTimeout(writeTimeout, TimeUnit.SECONDS);

        influxDB = InfluxDBFactory.connect(url, username, password, client);
        influxDB.setLogLevel(getLogLevel(logLevel));
        if (gzipEnabled) {
            influxDB.enableGzip();
        }

        Pong pong = influxDB.ping();
        System.out.println("Influx connection: " + (pong == null ? null : pong.getVersion()));
        influxDB.enableBatch(100, 200, TimeUnit.MILLISECONDS);
    }

    public void writeCurrencies(List<Rate> rates) {
        var batchPoints = BatchPoints
                .database("crypto")
                .build();

        rates.forEach(rate -> batchPoints.point(rate.toPoint()));
        influxDB.write(batchPoints);
    }

    private InfluxDB.LogLevel getLogLevel(String input) {
        for (InfluxDB.LogLevel level : InfluxDB.LogLevel.values()) {
            if (level.name().equals(input)) {
                return level;
            }
        }
        // Set as default level
        return InfluxDB.LogLevel.BASIC;
    }

    void createCryptoDatabase() {
        influxDB.query(new Query("CREATE DATABASE crypto", "crypto"));
        influxDB.query(new Query("CREATE RETENTION POLICY \"one_day_only\" ON \"crypto\" DURATION 23h60m REPLICATION 1 DEFAULT", "crypto"));
    }
}
