package sd;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Scanner;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.concurrent.TimeUnit;

@Configuration
public class MQConfig {

    public static final String QUEUE = "measure_queue";
    public static final String EXCHANGE = "message_exchange";
    public static final String ROUTING_KEY = "measure_routingKey";
    @Autowired
    private RabbitTemplate rabbitTemplate;

    private final File file = new File("/app/resources/sensor.csv");
    @Bean
    public Queue queue() {
        Queue queue = new Queue(QUEUE);
        System.out.println("Queue: " + QUEUE);
        return queue;
    }


    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE);
    }

    @Bean
    public Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder
                .bind(queue)
                .to(exchange)
                .with(ROUTING_KEY);
    }

    @Bean
    public MessageConverter messageConverter() {
        return  new Jackson2JsonMessageConverter();
    }

    public void startThreadForDevice(int deviceId) {
        Thread thread = new Thread(() -> send(deviceId));
        System.out.println("Thread for device " + deviceId + " started");
        thread.start();
    }
    public void send(int deviceId) {
        try {
            Scanner scan = new Scanner(file);

            while (scan.hasNextLine()) {
                LocalDateTime localDateTime = LocalDateTime.now();
                Timestamp date = Timestamp.valueOf(localDateTime);System.out.println("Ora actuala: " + localDateTime.getHour());
                System.out.println("Timestamp generat: " + date);
                Float value = scan.nextFloat();
                Measure measure = new Measure();

                measure.setIdDevice(deviceId);
                measure.setTimestamp(date);
                measure.setEnergyConsumption(value);

                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

                String data = objectMapper.writeValueAsString(measure);

                System.out.println("Sending message: " + data);
                rabbitTemplate.convertAndSend(EXCHANGE, ROUTING_KEY, data);

                date.setTime(date.getTime() + TimeUnit.MINUTES.toMillis(10));

                System.out.println(measure);

                //Thread.sleep(600000 );
                Thread.sleep(3000 );

            }
        } catch (FileNotFoundException | InterruptedException | JsonProcessingException e) {
            System.out.println(e.getMessage());
        }
    }



}