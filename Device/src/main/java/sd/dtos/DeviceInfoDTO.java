package sd.dtos;

public class DeviceInfoDTO {

    private int id;
    private String name;
    private Float  maximumHourlyEnergyConsumption;

    public DeviceInfoDTO() {
    }

    public DeviceInfoDTO(int id, String name, Float maximumHourlyEnergyConsumption) {
        this.id = id;
        this.name = name;
        this.maximumHourlyEnergyConsumption = maximumHourlyEnergyConsumption;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getMaximumHourlyEnergyConsumption() {
        return maximumHourlyEnergyConsumption;
    }

    public void setMaximumHourlyEnergyConsumption(Float maximumHourlyEnergyConsumption) {
        this.maximumHourlyEnergyConsumption = maximumHourlyEnergyConsumption;
    }

    @Override
    public String toString() {
        return "DeviceInfoDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", maximumHourlyEnergyConsumption=" + maximumHourlyEnergyConsumption +
                '}';
    }
}
