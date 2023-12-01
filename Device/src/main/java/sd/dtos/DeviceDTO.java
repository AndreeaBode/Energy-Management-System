package sd.dtos;

import sd.entities.Device;

public class DeviceDTO {

        private int id;
        private String name;

        private String description;

        private String address;

        private Float  maximumHourlyEnergyConsumption;

    public DeviceDTO(int id, String description, String address, Float maximumHourlyEnergyConsumption) {
        this.id = id;
        this.description = description;
        this.address = address;
        this.maximumHourlyEnergyConsumption = maximumHourlyEnergyConsumption;

    }

    public DeviceDTO(int id, String name, Float maximumHourlyEnergyConsumption) {
        this.id = id;
        this.name = name;
        this.maximumHourlyEnergyConsumption = maximumHourlyEnergyConsumption;
    }

    public DeviceDTO(Device device) {
        this.id = device.getId();
        this.name = device.getName();
        this.description = device.getDescription();
        this.address = device.getAddress();
        this.maximumHourlyEnergyConsumption = device.getMaximumHourlyEnergyConsumption();
    }

    public DeviceDTO() {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", address='" + address + '\'' +
                ", maximumHourlyEnergyConsumption=" + maximumHourlyEnergyConsumption +
                '}';
    }
}
