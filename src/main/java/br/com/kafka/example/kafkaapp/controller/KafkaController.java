package br.com.kafka.example.kafkaapp.controller;

import avro.example.entity.PessoaAvro;
import br.com.kafka.example.kafkaapp.dto.PessoaDTO;
import br.com.kafka.example.kafkaapp.mapper.PessoaMapper;
import br.com.kafka.example.kafkaapp.messaging.KafkaProducer;
import br.com.kafka.example.kafkaapp.provider.api.KafkaApi;
import br.com.kafka.example.kafkaapp.provider.representation.StandardSuccessRepresentation;
import br.com.kafka.example.kafkaapp.provider.representation.PessoaRequestRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/v1")
public class KafkaController extends BaseController implements KafkaApi {

    @Autowired
    private KafkaProducer kafkaProducer;

    @Autowired
    private PessoaMapper pessoaMapper;

    @Override
    public ResponseEntity<StandardSuccessRepresentation> postMessage(PessoaRequestRepresentation request) {
        PessoaAvro pessoa = pessoaMapper.toPessoaAvro(request);
        kafkaProducer.enviarMensagemPessoaTopicConsumer(pessoa);
        return createResponseSuccess(HttpStatus.CREATED, "Mensagem postada no tópico com sucesso.");
    }

}
