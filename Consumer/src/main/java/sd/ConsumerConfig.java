package sd;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConsumerConfig {

    public static final String MEASURE_QUEUE = "measure_queue";
    public static final String DEVICE_QUEUE = "device_queue";
    public static final String EXCHANGE = "message_exchange";
    public static final String ROUTING_KEY = "message_routingKey";
    public static final String DEVICE_ROUTING_KEY = "device_routingKey";

    public static final String MEASURE_ROUTING_KEY = "measure_routingKey";


    public static final String MAX_QUEUE = "max_hourly_energy_queue";
    public static final String ID_QUEUE = "raspuns_device_queue";
    // Modificați numele cozii pentru deviceId
    public static final String ID_KEY = "raspuns_routingKey";


    public static final String MAX_KEY = "max_routingKey";

    public static final String MAXX_QUEUE = "maxx_hourly_energy_queue";
    public static final String MAXX_KEY = "maxx_routingKey";

    public static final String DEVICE_EXCHANGE = "device_exchange";

    @Bean
    public TopicExchange deviceExchange() {
        return new TopicExchange(DEVICE_EXCHANGE);
    }

    @Bean
    public Binding deviceBinding(Queue deviceQueue, TopicExchange deviceExchange) {
        return BindingBuilder
                .bind(deviceQueue)
                .to(deviceExchange)
                .with(DEVICE_ROUTING_KEY);
    }

    @Bean
    public Queue measureQueue() {
        return QueueBuilder.durable(MEASURE_QUEUE).build();
    }

    @Bean
    public Queue idQueue() {
        return QueueBuilder.durable(ID_QUEUE).build();
    }
    @Bean
    public Queue maxQueue() {
        return QueueBuilder.durable(MAX_QUEUE).build();
    }
    @Bean
    public Queue maxxQueue() {
        return QueueBuilder.durable(MAXX_QUEUE).build();
    }

    @Bean
    public Queue deviceQueue() {
        return QueueBuilder.durable(DEVICE_QUEUE).build();
    }

    @Bean
    public Queue devQueue() {
        return QueueBuilder.durable("raspuns_device_queue").build();
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE);
    }

    @Bean
    public Binding measureBinding(Queue measureQueue, TopicExchange exchange) {
        return BindingBuilder
                .bind(measureQueue)
                .to(exchange)
                .with(MEASURE_ROUTING_KEY);
    }
    @Bean
    public Binding IdBinding(Queue idQueue, TopicExchange exchange) {
        return BindingBuilder
                .bind(idQueue)
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
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate template(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter());
        return template;
    }
}