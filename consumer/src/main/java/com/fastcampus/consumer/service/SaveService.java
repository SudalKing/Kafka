package com.fastcampus.consumer.service;

import com.fastcampus.consumer.dto.CheckOutDto;
import com.fastcampus.consumer.entity.ShipmentCheckOutEntity;
import com.fastcampus.consumer.repository.ShipmentCheckOutRepository;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Date;

@Slf4j
@RequiredArgsConstructor
@Service
public class SaveService {

    private final ShipmentCheckOutRepository shipmentCheckOutRepository;
    private final ModelMapper modelMapper;

    private static final String CHECKOUT_COMPLETE_TOPIC_NAME = "checkout.complete.v1";

    public Long saveCheckOutData(CheckOutDto checkOutDto) {
        ShipmentCheckOutEntity shipmentCheckOutEntity = modelMapper.map(checkOutDto, ShipmentCheckOutEntity.class);
        ShipmentCheckOutEntity savedEntity = shipmentCheckOutRepository.save(shipmentCheckOutEntity);

        return savedEntity.getShipmentId();
    }

}
