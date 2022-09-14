package com.revature.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
public class CreateOrderRequest {
    private int userId;
    private String paymentId;
    private String shipmentAddress;
}
