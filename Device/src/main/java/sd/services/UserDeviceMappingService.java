package sd.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sd.entities.UserDeviceMapping;
import sd.repositories.UserDeviceMappingRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDeviceMappingService {

    @Autowired
    private UserDeviceMappingRepository mappingRepository;

    public void createUserDeviceMapping(int userId, int deviceId) {
        UserDeviceMapping mapping = new UserDeviceMapping();
        mapping.setUserId(userId);
        mapping.setDeviceId(deviceId);
        mappingRepository.save(mapping);
    }


    public List<Integer> getDeviceIdsByUserId(int userId) {

        List<UserDeviceMapping> userMappings = mappingRepository.findByUserId(userId);
        System.out.println("User mapping" + userMappings);

        List<Integer> deviceIds = new ArrayList<>();

        for (UserDeviceMapping mapping : userMappings) {
            deviceIds.add(mapping.getDeviceId());
            System.out.println(mapping.getDeviceId());
        }

        return deviceIds;
    }

    public List<Integer> getUsersByDevice(int deviceId) {
        List<UserDeviceMapping> userMappings = mappingRepository.findByDeviceId(deviceId);
        List<Integer> usersIds = new ArrayList<>();

        for (UserDeviceMapping mapping : userMappings) {
           usersIds.add(mapping.getDeviceId());
            System.out.println(mapping.getDeviceId());
        }
        System.out.println("UsersIds" + usersIds);
        return usersIds;
    }
}

