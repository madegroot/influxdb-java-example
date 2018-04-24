package influxdemo.example.basic;

import org.influxdb.annotation.Column;
import org.influxdb.annotation.Measurement;

import java.time.Instant;

@Measurement(name = "memory")
public class MemoryPoint {

    @Column(name = "time")
    private Instant time;

    @Column(name = "application", tag = true)
    private String application;

    @Column(name = "environment", tag = true)
    private String environment;

    @Column(name = "free")
    private Long free;

    @Column(name = "used")
    private Long used;

    @Column(name = "buffer")
    private Long buffer;

    public Instant getTime() {
        return time;
    }

    public void setTime(Instant time) {
        this.time = time;
    }

    public String getApplication() {
        return application;
    }

    public void setApplication(String application) {
        this.application = application;
    }

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public Long getFree() {
        return free;
    }

    public void setFree(Long free) {
        this.free = free;
    }

    public Long getUsed() {
        return used;
    }

    public void setUsed(Long used) {
        this.used = used;
    }

    public Long getBuffer() {
        return buffer;
    }

    public void setBuffer(Long buffer) {
        this.buffer = buffer;
    }

    @Override
    public String toString() {
        return "MemoryPoint{" +
                "time=" + time +
                ", application='" + application + '\'' +
                ", environment='" + environment + '\'' +
                ", free=" + free +
                ", used=" + used +
                ", buffer=" + buffer +
                '}';
    }
}
