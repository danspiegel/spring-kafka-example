package br.com.kafka.example.kafkaapp.config;

import br.com.kafka.example.kafkaapp.dto.LogMessageDTO;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.generic.GenericRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.util.backoff.FixedBackOff;

@Slf4j
@Component
public class KafkaDefaultErrorHandlerConfig {

    @Autowired
    private KafkaProperties kafkaProperties;

    @Autowired
    @Qualifier("kafkaTemplateError")
    private KafkaTemplate<String, String> kafkaTemplate;

    @Value("${deal.topic.retry.interval}")
    private Long interval;

    @Value("${deal.topic.max.attempts}")
    private Long maxAttempts;

    public DefaultErrorHandler defaultErrorHandler() {
        return new DefaultErrorHandler(
                (object, exception) -> {
                    log.error(
                            LogMessageDTO.builder()
                                    .withStep("Enviando para DLQ")
                                    .withMsg("Mensagem em formato errado")
                                    .withCustomRecord(object.value())
                                    .build()
                                    .toString());
                    MessageBuilder<String> mb = MessageBuilder.withPayload(new Gson().toJson(object));
                    kafkaTemplate.send(mb.build());
                },
                new FixedBackOff(interval, maxAttempts));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Object> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Object> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setCommonErrorHandler(this.defaultErrorHandler());
        factory.setConsumerFactory(
                new DefaultKafkaConsumerFactory<>(kafkaProperties.buildConsumerProperties()));
        return factory;
    }

}
