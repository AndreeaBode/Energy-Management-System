package sd.dto;

import sd.entitie.Device;

public class DeviceDTO {


    private int idDevice;
    private String name;

    private Float  maximumHourlyEnergyConsumption;

    public DeviceDTO(int id, Float maximumHourlyEnergyConsumption) {
        this.idDevice = id;
        this.maximumHourlyEnergyConsumption = maximumHourlyEnergyConsumption;

    }

    public DeviceDTO(int id, String name, Float maximumHourlyEnergyConsumption) {
        this.idDevice = id;
        this.name = name;
        this.maximumHourlyEnergyConsumption = maximumHourlyEnergyConsumption;
    }

    public DeviceDTO(Device device) {
        this.idDevice = device.getId();
        this.name = device.getName();
        this.maximumHourlyEnergyConsumption = device.getMaximumHourlyEnergyConsumption();
    }

    public DeviceDTO() {
    }

    public int getIdDevice() {
        return idDevice;
    }

    public void setIdDevice(int idDevice) {
        this.idDevice = idDevice;
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
        return "DeviceDTO{" +
                "id=" + idDevice +
                ", name='" + name + '\'' +
                ", maximumHourlyEnergyConsumption=" + maximumHourlyEnergyConsumption +
                '}';
    }
}
