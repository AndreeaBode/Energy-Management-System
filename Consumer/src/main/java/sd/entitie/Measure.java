package sd.entitie;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Timestamp;


@Entity
@Data
public class Measure {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private Timestamp timestamp;

    private Float energyConsumption;

    private int idDevice;


    public Measure() {

    }

    public Measure(int id, Timestamp timestamp, Float energyConsumption, int deviceId) {
        this.id = id;
        this.timestamp = timestamp;
        this.energyConsumption = energyConsumption;
        this.idDevice = deviceId;
    }

    public Measure(Timestamp timestamp, Float energyConsumption, int deviceId) {
        this.timestamp = timestamp;
        this.energyConsumption = energyConsumption;
        this.idDevice = deviceId;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public void setIdDevice(int deviceId) {
        this.idDevice = deviceId;
    }


}