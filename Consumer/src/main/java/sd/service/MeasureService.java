package sd.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import sd.dto.MeasureDTO;
import sd.entitie.Device;
import sd.entitie.Measure;
import sd.repository.DeviceRepository;
import sd.repository.MeasureRepository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MeasureService {

    @Autowired
    private MeasureRepository measureRepository;
    @Autowired
    private DeviceRepository deviceRepository;


    public List<MeasureDTO> findAllMeasures() {
        List<MeasureDTO> measureDTOList = new ArrayList<>();
        List<Measure> measures = measureRepository.findAll();

        measures.stream().forEach(measure -> measureDTOList.add(new MeasureDTO(measure)));

        return measureDTOList;
    }

}
