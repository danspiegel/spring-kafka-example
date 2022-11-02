package br.com.kafka.example.kafkaapp.messaging;

import avro.example.entity.PessoaAvro;
import br.com.kafka.example.kafkaapp.config.CallbackMessagingConfig;
import br.com.kafka.example.kafkaapp.dto.PessoaDTO;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

@Slf4j
@Component
public class KafkaProducer {

    @Autowired
    @Qualifier("kafkaTemplatePessoaTopicConsumer")
    private KafkaTemplate<String, String> kafkaTemplatePessoaTopicConsumer;

    @Autowired
    @Qualifier("kafkaTemplatePessoaTopicProducer")
    private KafkaTemplate<String, String> kafkaTemplatePessoaTopicProducer;

    @Async
    public void enviarMensagemPessoaTopicConsumer(PessoaAvro pessoa) {
        MessageBuilder<String> mb = MessageBuilder.withPayload(new Gson().toJson(pessoa));
        ListenableFuture<SendResult<String, String>> future = kafkaTemplatePessoaTopicConsumer.send(mb.build());
        future.addCallback(new CallbackMessagingConfig());
    }

    @Async
    public void enviarMensagemPessoaTopicProducer(PessoaAvro pessoa) {
        MessageBuilder<String> mb = MessageBuilder.withPayload(new Gson().toJson(pessoa));
        ListenableFuture<SendResult<String, String>> future = kafkaTemplatePessoaTopicProducer.send(mb.build());
        future.addCallback(new CallbackMessagingConfig());
    }

}
