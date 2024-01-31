package sd;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import javassist.compiler.ast.Pair;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import sd.dto.MeasureDTO;
import sd.dto.MeasureDeviceDTO;
import sd.entitie.Measure;
import sd.entitie.Notification;
import sd.repository.MeasureRepository;
import sd.service.DeviceService;
import sd.service.MeasureDeviceService;
import sd.service.MeasureService;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

import static sd.ConsumerConfig.*;

@Component
public class RabbitController implements RabbitListenerConfigurer {

    private ObjectMapper objectMapper;
    @Autowired
    private MeasureRepository measureRepository;


    @Autowired
    private DeviceService deviceService;
    @Autowired
    private MeasureService measureService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private MeasureDeviceService measureDeviceService;
    private Measure measure;
    @Autowired
private WebSocketService webSocketService;
    private int messageCounter = 0;
    private float firstValue = 0;
    private float endValue = 0;

    private final ApplicationEventPublisher eventPublisher;

    private LocalDateTime localDateTime = LocalDateTime.now();

    public RabbitController(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @Override
    public void configureRabbitListeners(RabbitListenerEndpointRegistrar rabbitListenerEndpointRegistrar) {

    }

    @Autowired
    private AmqpAdmin amqpAdmin;

    private void clearMeasureQueue() {
        // Clear all content from the measure_queue
        amqpAdmin.purgeQueue("measure_queue", false);
    }

    private Map<Integer, MeasureInfo> deviceMeasureInfoMap = new HashMap<>();

    @RabbitListener(queues = MEASURE_QUEUE)
    public void receivedMessage(String measureData) {
        clearMeasureQueue();
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addDeserializer(LocalDateTime.class, new MillisOrLocalDateTimeDeserializer());

        objectMapper = new ObjectMapper();
        objectMapper.registerModule(javaTimeModule);

        try {
            System.out.println("Received data: " + measureData);
            MeasureDTO measureReceived = objectMapper.readValue(measureData, MeasureDTO.class);
            System.out.println(measureReceived);

            int idDevice = measureReceived.getIdDevice();

            MeasureInfo measureInfo = deviceMeasureInfoMap.getOrDefault(idDevice, new MeasureInfo(0.0f, 0));
            float currentSum = measureInfo.getSum();
            int currentCount = measureInfo.getCount();
            System.out.println("Measure "+ "" + currentCount);
            Timestamp timestamp = measureReceived.getTimestamp();
            LocalDateTime localDateTime = timestamp.toLocalDateTime();
            LocalDate localDate = localDateTime.toLocalDate();
            LocalTime localTime = localDateTime.toLocalTime();
            System.out.println("Timeeee" + timestamp);
            System.out.println("Oraaa" + localDateTime);

            boolean existId = checkDeviceExistence(idDevice);

            if (!existId) {
                System.out.println("Not found!");
            }

            if (currentCount == 0) {
                currentSum = measureReceived.getEnergyConsumption();
            }

            if (currentCount >= 5) {
                System.out.println("Messe" + currentCount);
                float sum = measureReceived.getEnergyConsumption() - currentSum;
                System.out.println("Suma actuala este " + sum);

                MeasureDeviceDTO measureDeviceDTO = new MeasureDeviceDTO(idDevice, sum, localDate, localTime);
                measureDeviceService.save(measureDeviceDTO);
                deviceMeasureInfoMap.put(idDevice, new MeasureInfo(measureReceived.getEnergyConsumption(), 0));

                float maxHourlyEnergyConsumption = getMaxHourlyEnergyConsumption(Float.valueOf(idDevice));
                System.out.println(maxHourlyEnergyConsumption);
                if (sum > maxHourlyEnergyConsumption) {
                    System.out.println("Max hourly energy consumption exceeded for device with id "+ ""+ idDevice + " with " + sum);
                    eventPublisher.publishEvent(new Notification(idDevice, maxHourlyEnergyConsumption, sum, "Max hourly energy consumption exceeded"));
                    webSocketService.sendNotification(new Notification(idDevice, maxHourlyEnergyConsumption, sum, "Max hourly energy consumption exceeded"));
                }
            } else {
                deviceMeasureInfoMap.put(idDevice, new MeasureInfo(currentSum, currentCount + 1));
            }

        } catch (JsonProcessingException e) {
            System.out.println(e.getMessage());
        }
    }
    private boolean checkDeviceExistence(int deviceId) {
       if (deviceId == 0) {
            System.out.println("Null");
            return false;
        } else {
            rabbitTemplate.convertAndSend(DEVICE_EXCHANGE, DEVICE_ROUTING_KEY, deviceId);

            String responseQueue = ID_QUEUE;
            Object response = rabbitTemplate.receiveAndConvert(responseQueue);

            if (response == null) {
                return false;
            }
            return true;
        }
    }


    private float getMaxHourlyEnergyConsumption(float deviceId) {
        if (deviceId == 0.0f) {
            System.out.println("Device null");
            return 0.0f;
        }
        rabbitTemplate.convertAndSend(DEVICE_EXCHANGE, MAX_KEY, deviceId);

        String responseQueue = MAXX_QUEUE;
        Object response = rabbitTemplate.receiveAndConvert(responseQueue);
        System.out.println("MaxHourlyEnergyConsumption" + "" + response);
        if (response != null) {
            return (float) response;
        } else {
            return 0.0f;
        }
    }


}