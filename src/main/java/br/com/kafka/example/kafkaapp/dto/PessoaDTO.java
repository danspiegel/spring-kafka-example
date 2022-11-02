package br.com.kafka.example.kafkaapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PessoaDTO {

    private String nome;
    private String sobrenome;
    private Integer idade;

}
