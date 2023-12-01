package sd.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sd.dtos.DeviceDTO;
import sd.services.DeviceService;
import sd.services.UserDeviceMappingService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping(value = "/device")
public class DeviceController {

    private final DeviceService deviceService;
    @Autowired
    private UserDeviceMappingService mappingService;

    @Autowired
    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @GetMapping("/getAllDevices")
    public ResponseEntity<List<DeviceDTO>> getDevices() {
        List<DeviceDTO> dtos = deviceService.getDevices();
        return new ResponseEntity<>(dtos,HttpStatus.OK);
    }


    @PostMapping("/addDevices")
    public ResponseEntity<DeviceDTO> addDevices(@Valid @RequestBody DeviceDTO deviceDTO) {
        System.out.println(deviceDTO.toString());
        DeviceDTO addedDevice = deviceService.addDevice(deviceDTO);
        return new ResponseEntity<>(addedDevice, HttpStatus.OK);
    }


    @GetMapping("/getAllUsers/{id}")
    public ResponseEntity<DeviceDTO> getUserById(@PathVariable("id") int deviceId) {
        DeviceDTO  device = deviceService.getDeviceById(deviceId);
        return new ResponseEntity<>(device, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<DeviceDTO> updateUser(@PathVariable("id") int id, @Valid @RequestBody  DeviceDTO deviceDTO){
        System.out.println("ID"+id);
        DeviceDTO device = deviceService.updateDevice(id, deviceDTO);
        return new ResponseEntity<>(device, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<DeviceDTO> deleteUser(@PathVariable("id") int id){
        DeviceDTO device = deviceService.deleteDevice(id);
        return new ResponseEntity<>(device, HttpStatus.OK);
    }

    @PostMapping("/addDeviceToUser")
    public ResponseEntity<String> createUserDeviceMapping(@RequestBody Map<String, Integer> requestBody) {
        int userId = requestBody.get("userId");
        int deviceId = requestBody.get("deviceId");

        mappingService.createUserDeviceMapping(userId, deviceId);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @GetMapping("/mapping/{userId}")
    public ResponseEntity<List<DeviceDTO>> getDevicesForUser(@PathVariable int userId) {
        System.out.println("Am ajuns!");
        List<Integer> deviceIds = mappingService.getDeviceIdsByUserId(userId);

        List<DeviceDTO> devices = new ArrayList<>();
        for (int deviceId : deviceIds) {
            DeviceDTO device = deviceService.getDeviceById(deviceId);
            if (device != null) {
                devices.add(device);
            }
        }
        return ResponseEntity.ok(devices);
    }

    @GetMapping("/notification/{devieceId}")
    public ResponseEntity<List<Integer>> getUsersByDevice(@PathVariable int deviceId) {
        List<Integer> usersIds = mappingService.getUsersByDevice(deviceId);
        return ResponseEntity.ok(usersIds);
    }
}
