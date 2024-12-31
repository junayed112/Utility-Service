package com.reddot.utilityservice.requestmoney.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestResponse {
    List<RequestMoneyResponse> outgoingRequest;
    List<RequestMoneyResponse> incomingRequest;
}
