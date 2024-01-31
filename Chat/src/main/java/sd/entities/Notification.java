package sd.entitie;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class Notification {

    private int idDevice;
    private Float maximumHourlyEnergyConsumption;
    private Float sum;
    private String message;

    public Notification() {}

    public Notification(int idDevice, Float maximumHourlyEnergyConsumption, Float sum, String message) {
        this.idDevice = idDevice;
        this.maximumHourlyEnergyConsumption = maximumHourlyEnergyConsumption;
        this.sum = sum;
        this.message = message;
    }

    public int getIdDevice() {
        return idDevice;
    }

    public void setIdDevice(int idDevice) {
        this.idDevice = idDevice;
    }


}