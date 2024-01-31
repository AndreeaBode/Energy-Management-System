package sd.services;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import sd.dtos.DeviceDTO;
import sd.dtos.builders.DeviceBuilder;
import sd.entities.Device;
import sd.repositories.DeviceRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DeviceService {
    private final DeviceRepository deviceRepository;

    public DeviceService(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    public List<DeviceDTO> getDevices () {
        List<Device> devices = deviceRepository.findAll();
        return devices.stream()
                .map(DeviceBuilder::toDeviceDTO)
                .collect(Collectors.toList());
    }

    public DeviceDTO getDeviceById(int deviceId) {
        Optional<Device> device = deviceRepository.findById(deviceId);
        if(!device.isPresent()) {
            return null;
        }

        return new DeviceDTO(device.get());
    }

    public DeviceDTO updateDevice(int id, DeviceDTO deviceDTO) {
        Optional<Device> deviceOptional = deviceRepository.findById(id);

        if (deviceOptional.isPresent()) {
            Device device = deviceOptional.get();
            device.setName(deviceDTO.getName());
            device.setAddress(deviceDTO.getAddress());
            device.setDescription(deviceDTO.getDescription());
            device.setMaximumHourlyEnergyConsumption(deviceDTO.getMaximumHourlyEnergyConsumption());
            deviceRepository.save(device);
           /* try {
                deviceConfig.update(device);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }*/
            System.out.println(id);
            System.out.println("Am ajuns");
            return DeviceBuilder.toDeviceDTO(device);
        } else {

            System.out.println("Utilizatorul cu ID-ul " + id + " nu a fost găsit.");
            return null;
        }
    }

    public DeviceDTO deleteDevice(int id)  {
        Device device = deviceRepository.findById(id).orElse(null);


        if (device != null) {
            deviceRepository.delete(device);
           /* try {
                deviceConfig.delete(device);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }*/
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
}
