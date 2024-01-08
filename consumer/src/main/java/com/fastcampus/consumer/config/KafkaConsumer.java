package com.fastcampus.consumer.config;

import com.fastcampus.consumer.dto.CheckOutDto;
import com.fastcampus.consumer.service.SaveService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class KafkaConsumer {

    private static final String TOPIC_NAME = "checkout.complete.v1";
    private static final String GROUP_ID = "shipment.group.v1";

    private final SaveService saveService;

    /**
     * 예상 못한 필드값이 들어와도 JsonIgnore 처리를 하도록 ObjectMapper 설정
     */
    private final ObjectMapper objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    @KafkaListener(topics = TOPIC_NAME, groupId = GROUP_ID)
    public void recordListener(String jsonMessage) {
        try {
            CheckOutDto checkOutDto = objectMapper.readValue(jsonMessage, CheckOutDto.class);
            log.info(checkOutDto.toString());
            saveService.saveCheckOutData(checkOutDto);
        } catch (Exception e) {
            log.error("recordListener ERROR message = {}", jsonMessage, e);
        }
    }
}
