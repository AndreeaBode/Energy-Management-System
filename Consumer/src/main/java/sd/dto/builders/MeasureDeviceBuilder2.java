package sd.dto.builders;

import sd.dto.MeasureDeviceDTO;
import sd.dto.MeasureDeviceDTO2;
import sd.entitie.MeasureDevice;

public class MeasureDeviceBuilder2 {

    public MeasureDeviceBuilder2() {
    }

    public static MeasureDeviceDTO2 toMeasureDeviceDTO2(MeasureDevice measureDevice) {
        MeasureDeviceDTO2 measureDeviceDTO = new MeasureDeviceDTO2();
        measureDeviceDTO.setHourlyEnergyConsumption(measureDevice.getHourlyEnergyConsumption());
        measureDeviceDTO.setTime(measureDevice.getTime());
        return measureDeviceDTO;
    }

    public static MeasureDevice toMeasureDevice(MeasureDeviceDTO measureDeviceDTO) {
        return new MeasureDevice(
                measureDeviceDTO.getHourlyEnergyConsumption(),
                measureDeviceDTO.getTime()
        );
    }
}
