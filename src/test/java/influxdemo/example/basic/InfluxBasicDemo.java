package influxdemo.example.basic;

import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.BatchPoints;
import org.influxdb.dto.Point;
import org.influxdb.dto.Pong;
import org.influxdb.dto.Query;
import org.influxdb.impl.InfluxDBResultMapper;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class InfluxBasicDemo {

    InfluxDB influxDB;

    public InfluxBasicDemo() {
        this.influxDB = InfluxDBFactory.connect("http://localhost:8086");
        influxDB.setLogLevel(InfluxDB.LogLevel.BASIC);
    }

    @Test
    public void contactDatabase() {
        Pong response = this.influxDB.ping();
        assertTrue(!"unknown".equalsIgnoreCase(response.getVersion()));
        System.out.println("Ping response: " + response.getVersion());
    }

    @Test
    public void createDatabase() {
        influxDB.query(new Query("CREATE DATABASE javademo", "mydb"));
        influxDB.query(new Query("CREATE RETENTION POLICY \"one_day_only\" ON \"javademo\" DURATION 23h60m REPLICATION 1 DEFAULT", "javademo"));

    }

    @Test
    public void insertASinglePoint() throws InterruptedException {
        influxDB.setRetentionPolicy("one_day_only");
        influxDB.setDatabase("javademo");

        var point = Point
                .measurement("memory")
                .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
                .tag("application", "spring_boot")
                .tag("environment", "ACC")
                .addField("free", 4743656L)
                .addField("used", 1015096L)
                .addField("buffer", 1010467L)
                .build();

        influxDB.write(point);
    }

    @Test
    public void insertABatchesOfPoints() {
        influxDB.enableBatch(100, 200, TimeUnit.MILLISECONDS);

        var batchPoints = BatchPoints
                .database("javademo")
                .build();

        var point1 = Point.measurement("memory")
                .time(System.currentTimeMillis() - 100, TimeUnit.MILLISECONDS)
                .tag("application", "spring_boot")
                .tag("environment", "production")
                .addField("free", 4642455L)
                .addField("used", 1116297L)
                .addField("buffer", 1020563L)
                .build();

        var point2 = Point.measurement("memory")
                .time(System.currentTimeMillis() - 200, TimeUnit.MILLISECONDS)
                .tag("application", "spring_boot")
                .tag("environment", "production")
                .addField("free", 4523693L)
                .addField("used", 12461001L)
                .addField("buffer", 1008467L)
                .build();

        batchPoints.point(point1);
        batchPoints.point(point2);

        influxDB.write(batchPoints);
        influxDB.disableBatch();
    }

    @Test
    public void readData() {
        var queryResult  = influxDB.query(new Query("Select * from memory", "javademo"));
        var resultMapper = new InfluxDBResultMapper();
        var memoryPoints = resultMapper.toPOJO(queryResult, MemoryPoint.class);
        assertEquals(3, memoryPoints.size());

        for (var point : memoryPoints) {
            System.out.println(point);
        }
    }

    @Test
    public void resetState() {
        this.influxDB.query(new Query("DROP RETENTION POLICY \"one_day_only\" ON \"javademo\"", "javademo"));
        this.influxDB.query(new Query("DROP DATABASE javademo", "javademo"));
    }
}
