package br.com.kafka.example.kafkaapp.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Getter
@Component
public class KafkaConfig {

    @Autowired
    private KafkaProperties kafkaProperties;

    @Value(value = "${topic.name.producer}")
    private String topicProducerName;

    @Value(value = "${topic.name.consumer}")
    private String topicConsumerName;

    @Value("${deal.general.topic.name.dlq}")
    private String topicDlq;

    @Bean("kafkaTemplatePessoaTopicConsumer")
    public KafkaTemplate<String, String> kafkaTemplatePessoaTopicConsumer(){
        KafkaTemplate<String, String> modeloKafka = new KafkaTemplate<>(new DefaultKafkaProducerFactory<>(kafkaProperties.buildProducerProperties()));
        modeloKafka.setDefaultTopic(this.topicConsumerName);
        return modeloKafka;
    }

    @Bean("kafkaTemplatePessoaTopicProducer")
    public KafkaTemplate<String, String> kafkaTemplatePessoaTopicProducer(){
        KafkaTemplate<String, String> modeloKafka = new KafkaTemplate<>(new DefaultKafkaProducerFactory<>(kafkaProperties.buildProducerProperties()));
        modeloKafka.setDefaultTopic(this.topicProducerName);
        return modeloKafka;
    }

    @Bean("kafkaTemplateError")
    public KafkaTemplate<String, String> kafkaTemplateError(){
        KafkaTemplate<String, String> modeloKafka = new KafkaTemplate<>(new DefaultKafkaProducerFactory<>(kafkaProperties.buildProducerProperties()));
        modeloKafka.setDefaultTopic(this.topicDlq);
        return modeloKafka;
    }

}
