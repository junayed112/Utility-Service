package com.reddot.utilityservice.requestmoney.service;

import com.reddot.utilityservice.requestmoney.dto.*;

import java.util.List;

public interface RequestMoneyService {
    RequestActionResponse saveRequest(RequestMoneyDto requestMoneyDto, String headerSecretKey);
    List<RequestMoneyResponse> getOutgoingRequests(String requestFrom, Integer pageNo, String headerSecretKey);
    List<RequestMoneyResponse> getIncomingRequests(String requestTo, Integer pageNo, String headerSecretKey);
    List<RequestMoneyResponse> getOutgoingRequests(String requestFrom, String headerSecretKey);
    List<RequestMoneyResponse> getIncomingRequests(String requestTo, String headerSecretKey);
    RequestResponse getRequests(String number, String headerSecretKey);
    RequestActionResponse payMoney(PayMoneyDto payMoneyDto, String headerSecretKey);
    RequestActionResponse declineRequest(String requestId, String headerSecretKey);
}
