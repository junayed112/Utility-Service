package com.reddot.utilityservice.requestmoney.controller;

import com.reddot.utilityservice.requestmoney.dto.DeclineRequestDto;
import com.reddot.utilityservice.requestmoney.dto.PayMoneyDto;
import com.reddot.utilityservice.requestmoney.dto.RequestMoneyDto;
import com.reddot.utilityservice.requestmoney.service.RequestMoneyService;
import com.reddot.utilityservice.utils.Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping(value = "/api/request-money")
public class RequestMoneyController {

    private final RequestMoneyService requestMoneyService;

    public RequestMoneyController(RequestMoneyService requestMoneyService) {
        this.requestMoneyService = requestMoneyService;
    }

    @PostMapping("/request")
    public ResponseEntity<Object> requestForMoney(@RequestBody RequestMoneyDto requestMoneyDto, HttpServletRequest httpRequest){
        return new ResponseEntity<>(requestMoneyService.saveRequest(requestMoneyDto, httpRequest.getHeader("SECRET_KEY")), Util.getHeaders(), HttpStatus.OK);
    }

    @GetMapping("/get-outgoing-requests/{requestFrom}/{pageNo}")
    public ResponseEntity<Object> getOutgoingRequest(@PathVariable("requestFrom") String requestFrom, @PathVariable("pageNo") Integer pageNo, HttpServletRequest httpRequest){
        return new ResponseEntity<>(requestMoneyService.getOutgoingRequests(requestFrom, pageNo, httpRequest.getHeader("SECRET_KEY")), Util.getHeaders(), HttpStatus.OK);
    }

    @GetMapping("/get-incoming-requests/{requestTo}/{pageNo}")
    public ResponseEntity<Object> getIncomingRequest(@PathVariable("requestTo") String requestTo, @PathVariable("pageNo") Integer pageNo, HttpServletRequest httpRequest){
        return new ResponseEntity<>(requestMoneyService.getIncomingRequests(requestTo, pageNo, httpRequest.getHeader("SECRET_KEY")), Util.getHeaders(), HttpStatus.OK);
    }

    @GetMapping("/get-outgoing-requests/{requestFrom}")
    public ResponseEntity<Object> getOutgoingRequest(@PathVariable("requestFrom") String requestFrom, HttpServletRequest httpRequest){
        return new ResponseEntity<>(requestMoneyService.getOutgoingRequests(requestFrom, httpRequest.getHeader("SECRET_KEY")), Util.getHeaders(), HttpStatus.OK);
    }

    @GetMapping("/get-incoming-requests/{requestTo}")
    public ResponseEntity<Object> getIncomingRequest(@PathVariable("requestTo") String requestTo, HttpServletRequest httpRequest){
        return new ResponseEntity<>(requestMoneyService.getIncomingRequests(requestTo, httpRequest.getHeader("SECRET_KEY")), Util.getHeaders(), HttpStatus.OK);
    }

    @GetMapping("/get-requests/{number}")
    public ResponseEntity<Object> getRequests(@PathVariable("number") String number, HttpServletRequest httpRequest){
        return new ResponseEntity<>(requestMoneyService.getRequests(number, httpRequest.getHeader("SECRET_KEY")), Util.getHeaders(), HttpStatus.OK);
    }

    @PostMapping("/pay")
    public ResponseEntity<Object> payToRequest(@RequestBody PayMoneyDto payMoneyDto, HttpServletRequest httpRequest){
        return new ResponseEntity<>(requestMoneyService.payMoney(payMoneyDto, httpRequest.getHeader("SECRET_KEY")), Util.getHeaders(), HttpStatus.OK);
    }

    @PostMapping("/decline")
    public ResponseEntity<Object> declineTheRequest(@RequestBody DeclineRequestDto declineRequestDto, HttpServletRequest httpRequest){
        return new ResponseEntity<>(requestMoneyService.declineRequest(declineRequestDto.getRequestId(), httpRequest.getHeader("SECRET_KEY")), Util.getHeaders(), HttpStatus.OK);
    }
}
