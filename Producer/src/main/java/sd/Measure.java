package sd;

        import org.springframework.stereotype.Component;
        import java.sql.Timestamp;

@Component
public class Measure {

    private Timestamp timestamp;

    private Float energyConsumption;

    private int idDevice;

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public void setEnergyConsumption(Float energyConsumption) {
        this.energyConsumption = energyConsumption;
    }

    public void setIdDevice(int idDevice) {
        this.idDevice = idDevice;
    }

    @Override
    public String toString() {
        return "Measure: " + this.idDevice + ", " + this.energyConsumption + ", " + this.timestamp;
    }
}
