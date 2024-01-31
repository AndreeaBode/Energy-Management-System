package sd.dtos.builders;

import sd.dtos.DeviceDTO;
import sd.entities.Device;

public class DeviceBuilder {

    public DeviceBuilder() {
    }

    public static DeviceDTO toDeviceDTO(Device device) {
        DeviceDTO deviceDTO = new DeviceDTO();
        deviceDTO.setId(device.getId());
        deviceDTO.setName(device.getName());
        deviceDTO.setDescription(device.getDescription());
        deviceDTO.setAddress(device.getAddress());
        deviceDTO.setMaximumHourlyEnergyConsumption(device.getMaximumHourlyEnergyConsumption());
        return deviceDTO;
    }

    public static Device toDevice(DeviceDTO deviceDTO) {
        return new Device(deviceDTO.getName(),deviceDTO.getDescription(),deviceDTO.getAddress(),deviceDTO.getMaximumHourlyEnergyConsumption());
    }
}
