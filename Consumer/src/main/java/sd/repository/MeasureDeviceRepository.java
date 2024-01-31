package sd.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sd.dto.MeasureDeviceDTO;
import sd.dto.MeasureDeviceDTO2;
import sd.entitie.Measure;
import sd.entitie.MeasureDevice;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MeasureDeviceRepository extends JpaRepository<MeasureDevice, Integer> {
    List<MeasureDevice> findByIdDeviceAndDate(int idDevice, LocalDate date);
}

