// Measure.java
package sd.rabbitMq;

import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Component
public class Measure {

    private Timestamp timestamp;
    private Float energyConsumption;
    private int deviceId;
    private String deviceName;  // Adăugat pentru a trimite numele dispozitivului
    private Float maxHourlyEnergyConsumed;  // Adăugat pentru a trimite consumul maxim orar de energie

    // Alte metode getter și setter

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public void setMaxHourlyEnergyConsumed(Float maxHourlyEnergyConsumed) {
        this.maxHourlyEnergyConsumed = maxHourlyEnergyConsumed;
    }
}
