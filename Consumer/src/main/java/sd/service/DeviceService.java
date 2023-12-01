package sd.service;

import org.springframework.stereotype.Service;
import sd.dto.builders.DeviceBuilder;
import sd.entitie.Measure;
import sd.repository.DeviceRepository;
import sd.dto.DeviceDTO;
import sd.entitie.Device;
import sd.repository.MeasureRepository;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DeviceService {

    private final DeviceRepository deviceRepository;
    private final MeasureRepository measureRepository;

    public DeviceService(DeviceRepository deviceRepository, MeasureRepository measureRepository) {
        this.deviceRepository = deviceRepository;
        this.measureRepository = measureRepository;
    }

    public DeviceDTO findById(int deviceId) {
        Optional<Device> device = deviceRepository.findById(deviceId);
        if(!device.isPresent()) {
            return null;
        }

        return new DeviceDTO(device.get());
    }

    public DeviceDTO deleteDevice(int id) {
        Device device = deviceRepository.findById(id).orElse(null);
        if (device != null) {
            deviceRepository.delete(device);
            return new DeviceDTO(device);
        } else {
            throw new EntityNotFoundException("User not found with id: " + id);
        }
    }

    public DeviceDTO addDevice(DeviceDTO deviceDTO) {
        Device device = DeviceBuilder.toDevice(deviceDTO);
        Device savedDevice = deviceRepository.save(device);
        return DeviceBuilder.toDeviceDTO(savedDevice);

    }

        public DeviceDTO updateDevice(int id, DeviceDTO deviceDTO) {
            Optional<Device> deviceOptional = deviceRepository.findById(id);

            if (deviceOptional.isPresent()) {
                Device device = deviceOptional.get();
                device.setName(deviceDTO.getName());
                device.setMaximumHourlyEnergyConsumption(deviceDTO.getMaximumHourlyEnergyConsumption());
                deviceRepository.save(device);
                System.out.println(id);
                System.out.println("Am ajuns");
                return DeviceBuilder.toDeviceDTO(device);
            } else {

                System.out.println("Utilizatorul cu ID-ul " + id + " nu a fost găsit.");
                return null;
            }
        }

}
