package sd.dto.builders;

import sd.dto.DeviceDTO;
import sd.dto.MeasureDeviceDTO;
import sd.entitie.Device;
import sd.entitie.MeasureDevice;

public class MeasureDeviceBuilder {

        public MeasureDeviceBuilder() {
        }

        public static MeasureDeviceDTO toMeasureDeviceDTO(MeasureDevice measureDevice) {
            MeasureDeviceDTO measureDeviceDTO = new MeasureDeviceDTO();
            measureDeviceDTO.setIdDevice(measureDevice.getIdDevice());
            measureDeviceDTO.setHourlyEnergyConsumption(measureDevice.getHourlyEnergyConsumption());
           measureDeviceDTO.setDate(measureDevice.getDate());
           measureDeviceDTO.setTime(measureDevice.getTime());
            return measureDeviceDTO;
        }

    public static MeasureDevice toMeasureDevice(MeasureDeviceDTO measureDeviceDTO) {
        return new MeasureDevice(
                measureDeviceDTO.getIdDevice(),
                measureDeviceDTO.getHourlyEnergyConsumption(),
                measureDeviceDTO.getDate(),
                measureDeviceDTO.getTime()
        );
    }


}
