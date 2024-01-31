package sd.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;


@Getter
@Setter
@NoArgsConstructor
public class MeasureDeviceDTO2 {

    private Float  hourlyEnergyConsumption;

    private LocalTime time;

    public MeasureDeviceDTO2(Float hourlyEnergyConsumption, LocalTime time) {
        this.hourlyEnergyConsumption = hourlyEnergyConsumption;
        this.time = time;
    }

    public Float getHourlyEnergyConsumption() {
        return hourlyEnergyConsumption;
    }

    public void setHourlyEnergyConsumption(Float hourlyEnergyConsumption) {
        this.hourlyEnergyConsumption = hourlyEnergyConsumption;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "MeasureDeviceDTO2{" +
                "hourlyEnergyConsumption=" + hourlyEnergyConsumption +
                ", time=" + time +
                '}';
    }
}
