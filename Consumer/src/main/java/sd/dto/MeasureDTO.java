package sd.dto;

import lombok.*;
import sd.entitie.Measure;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MeasureDTO {

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

    public MeasureDTO(Measure measure) {
        this.idDevice = measure.getIdDevice();
        this.timestamp = measure.getTimestamp();
        this.energyConsumption = measure.getEnergyConsumption();
    }

    @Override
    public String toString() {
        return "Measure: " + this.idDevice + ", " + this.energyConsumption + ", " + this.timestamp;
    }

}