package br.com.kafka.example.kafkaapp.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Slf4j
public class CallbackMessagingConfig implements ListenableFutureCallback<SendResult<String, ?>> {

    @Override
    public void onFailure(Throwable e) {
        log.error("Nao foi possivel enviar a mensagem para o Kafka. Erro: {}", e.getCause().getMessage(), e);
    }

    @Override
    public void onSuccess(SendResult<String, ?> result) {
        log.info("Mensagem enviada com sucesso ao Kafka - {} ", result.getProducerRecord().topic());
    }

}
