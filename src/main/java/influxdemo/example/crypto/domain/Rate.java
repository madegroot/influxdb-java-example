package influxdemo.example.crypto.domain;

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
}
