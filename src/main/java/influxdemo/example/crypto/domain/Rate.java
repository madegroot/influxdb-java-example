package influxdemo.example.crypto.domain;

import org.influxdb.dto.Point;
import org.springframework.util.StringUtils;

import java.util.concurrent.TimeUnit;

public class Rate {

    private Ticker ticker;
    private Long timestamp;
    private boolean success;
    private String error;

    public Ticker getTicker() {
        return ticker;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getError() {
        return error;
    }

    @Override
    public String toString() {
        return "Rate{" +
                "ticker=" + ticker +
                ", timestamp=" + timestamp +
                ", success=" + success +
                ", error='" + error + '\'' +
                '}';
    }

    public static Point toPoint(Rate rate) {
        return rate.toPoint();
    }

    public Point toPoint() {
        return Point.measurement("currency")
                .time(timestamp, TimeUnit.SECONDS)
                .tag("coin", ticker.getBase())
                .tag("target", ticker.getTarget())
                .addField("price", ticker.getPrice())
                .addField("price", valueOf(ticker.getPrice()))
                .addField("volume", valueOf(ticker.getVolume()))
                .addField("change", valueOf(ticker.getChange()))
                .build();
    }

    private Float valueOf(String input) {
        return StringUtils.isEmpty(input) ? null : Float.valueOf(input);
    }
}
