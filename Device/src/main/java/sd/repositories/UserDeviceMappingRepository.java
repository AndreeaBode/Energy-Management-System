package sd.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sd.entities.UserDeviceMapping;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserDeviceMappingRepository extends JpaRepository<UserDeviceMapping, Long> {
    List<UserDeviceMapping> findByUserId(int userId);

    List<UserDeviceMapping> findByDeviceId(int deviceId);
}

