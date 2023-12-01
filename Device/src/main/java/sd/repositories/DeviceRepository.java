package sd.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sd.entities.Device;

public interface DeviceRepository extends JpaRepository<Device, Integer> {
}
