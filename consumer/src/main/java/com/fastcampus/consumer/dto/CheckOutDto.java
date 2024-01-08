package com.fastcampus.consumer.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class CheckOutDto {

    private Long checkOutId;
    private Long memberId;
    private Long productId;
    private Long amount;
    private String shippingAddress;
    private Date createdAt;
}
