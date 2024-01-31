package sd.rabbitMq;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import sd.dtos.DeviceDTO;
import sd.services.DeviceService;

@Configuration
public class DeviceConfig {
    public static final String DEVICE_QUEUE = "device_queue";
    public static final String ID_QUEUE = "raspuns_device_queue";
    public static final String DEVICE_ROUTING_KEY = "device_routingKey";

    public static final String ID_KEY = "raspuns_device_queue";

    public static final String MAX_QUEUE = "max_hourly_energy_queue";
    public static final String MAX_KEY = "max_routingKey";

    public static final String MAXX_QUEUE = "maxx_hourly_energy_queue";
    public static final String MAXX_KEY = "maxx_routingKey";

    private RabbitTemplate rabbitTemplate;
    private DeviceService deviceService;

    @Autowired
    public void setRabbitTemplate(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Autowired
    public void setDeviceService(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    public static final String DEVICE_EXCHANGE = "device_exchange";

    @Bean
    public TopicExchange deviceExchange() {
        return new TopicExchange(DEVICE_EXCHANGE);
    }

    @Bean
    public Queue queue() {
        return new Queue(DEVICE_QUEUE);
    }

    @Bean
    public Binding deviceBinding(Queue queue, TopicExchange deviceExchange) {
        return BindingBuilder
                .bind(queue)
                .to(deviceExchange)
                .with(DEVICE_ROUTING_KEY);
    }

    @Bean
    public Queue raspunsMaxHourlyEnergyQueue() {
        return new Queue("raspuns_max_hourly_energy_queue", false);
    }

    @Bean
    public Queue queueId() {
        return new Queue(ID_QUEUE);
    }

    @Bean
    public Queue maxQueue() {
        return new Queue(MAX_QUEUE);
    }

    @Bean
    public Queue maxxQueue() {
        return QueueBuilder.durable(MAXX_QUEUE).build();
    }





    @Bean
    public Queue maxDeviceQueue() {
        return new Queue("max_hourly_energy_queue");
    }

    @Bean
    public Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder
                .bind(queue)
                .to(exchange)
                .with(DEVICE_ROUTING_KEY);
    }

    @Bean
    public Binding bindingID(Queue queueId, TopicExchange exchange) {
        return BindingBuilder
                .bind(queueId)
                .to(exchange)
                .with(ID_KEY);
    }

    @Bean
    public Binding MaxBinding(Queue maxQueue, TopicExchange exchange) {
        return BindingBuilder
                .bind(maxQueue)
                .to(exchange)
                .with(MAX_KEY);
    }

    @Bean
    public Binding MaxxBinding(Queue maxxQueue, TopicExchange exchange) {
        return BindingBuilder
                .bind(maxxQueue)
                .to(exchange)
                .with(MAXX_KEY);
    }

    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }


    @RabbitListener(queues = DEVICE_QUEUE)
    public void handleDeviceIdResponse(String response) {
        System.out.println("Am ajuns");
        try {

            if(response == null){
                System.out.println("Null");
            }else{
            System.out.println("Received response for deviceId: " + response);
            DeviceDTO deviceDTO = deviceService.getDeviceById(Integer.parseInt(response));

            if (deviceDTO != null) {
                System.out.println("Found device with id: " + deviceDTO.getId());

                rabbitTemplate.convertAndSend(DEVICE_EXCHANGE, ID_KEY, deviceDTO.getId());
            } else {
                System.out.println("Device not found for id: " + response);
            }}
        } catch (Exception e) {
            System.err.println("Error processing device ID response: " + e.getMessage());
        }
    }


    @RabbitListener(queues = MAX_QUEUE)
    public void handleMaxHourlyEnergyRequest(float deviceId) {
        System.out.println("IDD" + deviceId);
        if (deviceId == 0.0f) {
            System.out.println("Device null!");
        } else {
            DeviceDTO deviceDTO = deviceService.getDeviceById((int) deviceId);
            float maxHourlyEnergyConsumption = deviceDTO.getMaximumHourlyEnergyConsumption();
            System.out.println("maxHourlyEnergyConsumption" + "" + maxHourlyEnergyConsumption);
            rabbitTemplate.convertAndSend(DEVICE_EXCHANGE, MAXX_KEY, maxHourlyEnergyConsumption);
        }
    }

}