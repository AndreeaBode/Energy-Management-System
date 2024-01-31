package sd.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sd.entitie.Device;

public interface DeviceRepository extends JpaRepository<Device, Integer> {
}

