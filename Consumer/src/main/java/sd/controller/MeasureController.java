package sd.controller;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import sd.dto.MeasureDeviceDTO;
import sd.dto.MeasureDeviceDTO2;
import sd.service.MeasureDeviceService;
import sd.service.MeasureService;
import sd.dto.MeasureDTO;

import javax.management.Notification;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/")
@CrossOrigin
@Slf4j

public class MeasureController {

    @Autowired
    MeasureService measureService;

    @Autowired
    MeasureDeviceService measureDeviceService;

    @Autowired
    SimpMessagingTemplate template;

    @GetMapping("/allMeasures")
    public ResponseEntity<List<MeasureDTO>> getAllMeasures() {
        return new ResponseEntity<>(measureService.findAllMeasures(), HttpStatus.OK);
    }

    @EventListener(Notification.class)
    public void handleEvent(Notification event) {
        template.convertAndSend("/my-devices", event);
    }

    @GetMapping("/charts/{idDevice}")
    public ResponseEntity<List<MeasureDeviceDTO2>> getChartsDataForAllDevices(
            @PathVariable int idDevice,
            @RequestParam("date") String date
    ) {
        try {
            System.out.println("Date" + date);

            LocalDate localDate = LocalDate.parse(date);
            List<MeasureDeviceDTO2> chartData = measureDeviceService.getCharts(idDevice, localDate);
            System.out.println(chartData);
            return ResponseEntity.ok(chartData);
        } catch (DateTimeParseException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(null);
        }
    }


}
