package br.com.kafka.example.kafkaapp.messaging;

import avro.example.entity.PessoaAvro;
import br.com.kafka.example.kafkaapp.config.KafkaConfig;
import br.com.kafka.example.kafkaapp.dto.PessoaDTO;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class KafkaConsumer {

    @Autowired
    private KafkaConfig kafkaConfig;

    @Autowired
    private KafkaProducer kafkaProducer;

    @KafkaListener(topics = "${topic.name.consumer}", groupId = "${spring.kafka.consumer.group-id}")
    public void consumer(@Payload String message){
        PessoaAvro pessoa = new Gson().fromJson(message, PessoaAvro.class);
        kafkaProducer.enviarMensagemPessoaTopicProducer(pessoa);
    }

}
