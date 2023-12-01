package sd.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sd.dto.MeasureDTO;
import sd.entitie.Measure;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MeasureRepository extends JpaRepository<Measure, Integer> {

}