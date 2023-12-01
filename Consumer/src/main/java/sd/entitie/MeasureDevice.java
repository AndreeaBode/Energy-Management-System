package sd.entitie;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Data
public class MeasureDevice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int idDevice;

    private Float  hourlyEnergyConsumption;

    private LocalDate date;
    private LocalTime time;


    public MeasureDevice(int id, int idDevice, Float hourlyEnergyConsumption, LocalDate date, LocalTime time) {
        this.id = id;
        this.idDevice = idDevice;
        this.hourlyEnergyConsumption = hourlyEnergyConsumption;
        this.date = date;
        this.time = time;
    }

    public MeasureDevice(Float hourlyEnergyConsumption, LocalTime time) {
        this.hourlyEnergyConsumption = hourlyEnergyConsumption;
        this.time = time;
    }

    public MeasureDevice(int idDevice, Float hourlyEnergyConsumption, LocalDate date, LocalTime time) {
        this.idDevice = idDevice;
        this.hourlyEnergyConsumption = hourlyEnergyConsumption;
        this.date = date;
        this.time = time;
    }

    public MeasureDevice() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
}
