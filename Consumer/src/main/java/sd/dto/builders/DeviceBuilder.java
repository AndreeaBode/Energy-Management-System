package sd.dto.builders;

import sd.dto.DeviceDTO;
import sd.entitie.Device;

public class DeviceBuilder {
        public DeviceBuilder() {
        }

        public static DeviceDTO toDeviceDTO(Device device) {
            DeviceDTO deviceDTO = new DeviceDTO();
            deviceDTO.setIdDevice(device.getId());
            deviceDTO.setName(device.getName());
            deviceDTO.setMaximumHourlyEnergyConsumption(device.getMaximumHourlyEnergyConsumption());
            return deviceDTO;
        }

        public static Device toDevice(DeviceDTO deviceDTO) {
            return new Device(deviceDTO.getIdDevice(), deviceDTO.getName(),deviceDTO.getMaximumHourlyEnergyConsumption());
        }
    }

