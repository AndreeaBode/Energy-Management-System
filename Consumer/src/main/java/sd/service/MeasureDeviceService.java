package sd.service;

import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sd.dto.DeviceDTO;
import sd.dto.MeasureDTO;
import sd.dto.MeasureDeviceDTO;
import sd.dto.MeasureDeviceDTO2;
import sd.dto.builders.DeviceBuilder;
import sd.dto.builders.MeasureDeviceBuilder;
import sd.dto.builders.MeasureDeviceBuilder2;
import sd.entitie.Device;
import sd.entitie.Measure;
import sd.entitie.MeasureDevice;
import sd.repository.MeasureDeviceRepository;
import sd.repository.MeasureRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MeasureDeviceService {

    @Autowired
    private MeasureDeviceRepository measureDeviceRepository;


    public MeasureDeviceDTO save(MeasureDeviceDTO measureDeviceDTO) {

            MeasureDevice measureDevice = MeasureDeviceBuilder.toMeasureDevice(measureDeviceDTO);
            MeasureDevice savedDevice = measureDeviceRepository.save(measureDevice);
            return MeasureDeviceBuilder.toMeasureDeviceDTO(savedDevice);
    }

    public List<MeasureDeviceDTO2> getCharts(int idDevice, LocalDate date) {
        List<MeasureDevice> measureDevices = measureDeviceRepository.findByIdDeviceAndDate(idDevice, date);

        List<MeasureDeviceDTO2> measureDeviceDTO2List = new ArrayList<>();
        for (MeasureDevice measureDevice : measureDevices) {
            MeasureDeviceDTO2 measureDeviceDTO2 = MeasureDeviceBuilder2.toMeasureDeviceDTO2(measureDevice);
            measureDeviceDTO2List.add(measureDeviceDTO2);
        }

        return measureDeviceDTO2List;
    }
}
