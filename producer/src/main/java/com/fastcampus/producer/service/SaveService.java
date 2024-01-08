package com.fastcampus.producer.service;

import com.fastcampus.producer.dto.CheckOutDto;
import com.fastcampus.producer.entity.CheckOutEntity;
import com.fastcampus.producer.repository.CheckOutRepository;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;

@Slf4j
@RequiredArgsConstructor
@Service
public class SaveService {

    private final CheckOutRepository checkOutRepository;
    private final ObjectMapper objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    private final ModelMapper modelMapper;
    private final KafkaTemplate<String, String> kafkaTemplate;

    private static final String CHECKOUT_COMPLETE_TOPIC_NAME = "checkout.complete.v1";

    public Long saveCheckOutData(CheckOutDto checkOutDto) {
        CheckOutEntity checkOutEntity = saveDataBase(checkOutDto);

        checkOutDto.setCheckOutId(checkOutEntity.getCheckOutId());
        checkOutDto.setCreatedAt(new Date(checkOutEntity.getCreatedAt().getTime()));
        sendToKafka(checkOutDto);

        return checkOutEntity.getCheckOutId();
    }

    private CheckOutEntity saveDataBase(CheckOutDto checkOutDto) {
        CheckOutEntity checkOutEntity = modelMapper.map(checkOutDto, CheckOutEntity.class);

        return checkOutRepository.save(checkOutEntity);
    }

    private void sendToKafka(CheckOutDto checkOutDto) {
        try {
            String jsonInString = objectMapper.writeValueAsString(checkOutDto);
            kafkaTemplate.send(CHECKOUT_COMPLETE_TOPIC_NAME, jsonInString);
            log.info("success send to kafka....");
        } catch (Exception e) {
            log.error("send to kafka....", e);
        }
    }
}
