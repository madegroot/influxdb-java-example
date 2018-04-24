package influxdemo.example.crypto.domain;

public class Ticker {

    private String base;
    private String target;
    private String price;
    private String volume;
    private String change;

    public String getBase() {
        return base;
    }

    public String getTarget() {
        return target;
    }

    public String getPrice() {
        return price;
    }

    public String getVolume() {
        return volume;
    }

    public String getChange() {
        return change;
    }

    @Override
    public String toString() {
        return "Ticker{" +
                "base='" + base + '\'' +
                ", target='" + target + '\'' +
                ", price='" + price + '\'' +
                ", volume='" + volume + '\'' +
                ", change='" + change + '\'' +
                '}';
    }
}
