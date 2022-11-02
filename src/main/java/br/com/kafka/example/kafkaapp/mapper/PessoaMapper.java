package br.com.kafka.example.kafkaapp.mapper;

import avro.example.entity.PessoaAvro;
import br.com.kafka.example.kafkaapp.provider.representation.PessoaRequestRepresentation;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValueMappingStrategy;

@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL,
        componentModel = "spring")
public interface PessoaMapper {

    PessoaAvro toPessoaAvro(PessoaRequestRepresentation representation);

}
