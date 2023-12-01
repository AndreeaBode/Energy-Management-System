package sd.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
public class MeasureDeviceDTO {

    private int idDevice;

    private Float  hourlyEnergyConsumption;

    private LocalDate date;
    private LocalTime time;


    public MeasureDeviceDTO(int idDevice, Float hourlyEnergyConsumption, LocalDate date, LocalTime time) {
        this.idDevice = idDevice;
        this.hourlyEnergyConsumption = hourlyEnergyConsumption;
        this.date = date;
        this.time = time;
    }

    public int getIdDevice() {
        return idDevice;
    }

    public void setIdDevice(int idDevice) {
        this.idDevice = idDevice;
    }

    public Float getHourlyEnergyConsumption() {
        return hourlyEnergyConsumption;
    }

    public void setHourlyEnergyConsumption(Float hourlyEnergyConsumption) {
        this.hourlyEnergyConsumption = hourlyEnergyConsumption;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "MeasureDeviceDTO{" +
                "idDevice=" + idDevice +
                ", hourlyEnergyConsumption=" + hourlyEnergyConsumption +
                ", date=" + date +
                ", time=" + time +
                '}';
    }
}
