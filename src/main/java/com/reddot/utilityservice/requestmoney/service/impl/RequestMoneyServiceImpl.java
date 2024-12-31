package com.reddot.utilityservice.requestmoney.service.impl;

import com.reddot.utilityservice.ApplicationPrperties;
import com.reddot.utilityservice.external_comunication.core.dto.CoreTransactionRequest;
import com.reddot.utilityservice.external_comunication.core.dto.CoreTransactionResponse;
import com.reddot.utilityservice.external_comunication.core.gateway.CoreGateWay;
import com.reddot.utilityservice.external_comunication.sms.SmsGateway;
import com.reddot.utilityservice.requestmoney.dto.*;
import com.reddot.utilityservice.requestmoney.entity.RequestMoneyEntity;
import com.reddot.utilityservice.requestmoney.enums.RequestMoneyStatus;
import com.reddot.utilityservice.requestmoney.repository.RequestMoneyRepository;
import com.reddot.utilityservice.requestmoney.service.RequestMoneyService;
import com.reddot.utilityservice.custom_exceptions.ApplicationException;
import com.reddot.utilityservice.custom_exceptions.CustomError;
import com.reddot.utilityservice.utils.Util;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RequestMoneyServiceImpl implements RequestMoneyService {
    private final RequestMoneyRepository requestMoneyRepository;
    private final CoreGateWay coreGateWay;
    private final ApplicationPrperties applicationPrperties;
    private final SmsGateway smsGateway;

    public RequestMoneyServiceImpl(RequestMoneyRepository requestMoneyRepository, CoreGateWay coreGateWay, ApplicationPrperties applicationPrperties, SmsGateway smsGateway) {
        this.requestMoneyRepository = requestMoneyRepository;
        this.coreGateWay = coreGateWay;
        this.applicationPrperties = applicationPrperties;
        this.smsGateway = smsGateway;
    }

    @Override
    public RequestActionResponse saveRequest(RequestMoneyDto requestMoneyDto, String headerSecretKey) {
        try{
            Util.isValidRequest(headerSecretKey, applicationPrperties.secretKey);
            coreGateWay.checkPin(new CheckPinDto(requestMoneyDto.getRequestFrom(), requestMoneyDto.getPin()));
            try{
                String requestId = Util.getRequestId(requestMoneyDto.getRequestFrom(), requestMoneyDto.getRequestTo());
                RequestMoneyEntity requestMoneyEntity = new RequestMoneyEntity();
                requestMoneyEntity.setRequestId(requestId);
                requestMoneyEntity.setRequestFrom(requestMoneyDto.getRequestFrom());
                requestMoneyEntity.setRequesterName(requestMoneyDto.getRequesterName());
                requestMoneyEntity.setRequestTo(requestMoneyDto.getRequestTo());
                requestMoneyEntity.setReceiverName(requestMoneyDto.getReceiverName());
                requestMoneyEntity.setRequestedAmount(requestMoneyDto.getRequestedAmount());
                requestMoneyEntity.setTransactionStatus(RequestMoneyStatus.PENDING.value);
                requestMoneyRepository.save(requestMoneyEntity);

                RequestActionResponse requestActionResponse = new RequestActionResponse();
                requestActionResponse.setRequestId(requestId);
                requestActionResponse.setMessage(new StringBuilder("Request for TK ")
                        .append(requestMoneyEntity.getRequestedAmount())
                        .append(" to ").append(requestMoneyEntity.getRequestTo())
                        .append(" sent successfully").toString());
                return requestActionResponse;
            }
            catch(Exception exception){
                throw new ApplicationException(new CustomError("400", exception.getMessage()));
            }
        }
        catch (ApplicationException applicationException){
            throw applicationException;
        }
    }

    @Override
    public List<RequestMoneyResponse> getOutgoingRequests(String requestFrom, Integer pageNo, String headerSecretKey) {
        try{
            Util.isValidRequest(headerSecretKey, applicationPrperties.secretKey);
            try{
                return requestMoneyRepository.findAllByRequestFrom(requestFrom, Util.getPageableObject(pageNo, applicationPrperties.pageSize)).stream().map(requestMoneyEntity ->
                        new RequestMoneyResponse(
                                requestMoneyEntity.getRequestId(),
                                requestMoneyEntity.getRequestFrom(),
                                requestMoneyEntity.getRequesterName(),
                                requestMoneyEntity.getRequestTo(),
                                requestMoneyEntity.getReceiverName(),
                                requestMoneyEntity.getRequestedAmount(),
                                requestMoneyEntity.getTransactionStatus(),
                                Util.formatDate(requestMoneyEntity.getCreateDateTime())
                        )
                ).collect(Collectors.toList());
            }
            catch (Exception exception){
                throw new ApplicationException(new CustomError("400", exception.getMessage()));
            }
        }
        catch (ApplicationException applicationException){
            throw applicationException;
        }

    }

    @Override
    public List<RequestMoneyResponse> getIncomingRequests(String requestTo, Integer pageNo, String headerSecretKey) {
        try{
            Util.isValidRequest(headerSecretKey, applicationPrperties.secretKey);
            try{
                return requestMoneyRepository.findAllByRequestTo(requestTo, Util.getPageableObject(pageNo, applicationPrperties.pageSize)).stream().map(requestMoneyEntity ->
                        new RequestMoneyResponse(
                                requestMoneyEntity.getRequestId(),
                                requestMoneyEntity.getRequestFrom(),
                                requestMoneyEntity.getRequesterName(),
                                requestMoneyEntity.getRequestTo(),
                                requestMoneyEntity.getReceiverName(),
                                requestMoneyEntity.getRequestedAmount(),
                                requestMoneyEntity.getTransactionStatus(),
                                Util.formatDate(requestMoneyEntity.getCreateDateTime())
                        )
                ).collect(Collectors.toList());
            }
            catch (Exception exception){
                throw new ApplicationException(new CustomError("400", exception.getMessage()));
            }
        }catch (ApplicationException applicationException){
            throw applicationException;
        }
    }

    @Override
    public List<RequestMoneyResponse> getOutgoingRequests(String requestFrom, String headerSecretKey) {
        try{
            Util.isValidRequest(headerSecretKey, applicationPrperties.secretKey);
            try{
                return requestMoneyRepository.findAllByRequestFrom(requestFrom).stream().map(requestMoneyEntity ->
                        new RequestMoneyResponse(
                                requestMoneyEntity.getRequestId(),
                                requestMoneyEntity.getRequestFrom(),
                                requestMoneyEntity.getRequesterName(),
                                requestMoneyEntity.getRequestTo(),
                                requestMoneyEntity.getReceiverName(),
                                requestMoneyEntity.getRequestedAmount(),
                                requestMoneyEntity.getTransactionStatus(),
                                Util.formatDate(requestMoneyEntity.getCreateDateTime())
                        )
                ).collect(Collectors.toList());
            }
            catch (Exception exception){
                throw new ApplicationException(new CustomError("400", exception.getMessage()));
            }
        }catch (ApplicationException applicationException){
            throw applicationException;
        }
    }

    @Override
    public List<RequestMoneyResponse> getIncomingRequests(String requestTo, String headerSecretKey) {
        try{
            Util.isValidRequest(headerSecretKey, applicationPrperties.secretKey);
            try{
                return requestMoneyRepository.findAllByRequestTo(requestTo).stream().map(requestMoneyEntity ->
                        new RequestMoneyResponse(
                                requestMoneyEntity.getRequestId(),
                                requestMoneyEntity.getRequestFrom(),
                                requestMoneyEntity.getRequesterName(),
                                requestMoneyEntity.getRequestTo(),
                                requestMoneyEntity.getReceiverName(),
                                requestMoneyEntity.getRequestedAmount(),
                                requestMoneyEntity.getTransactionStatus(),
                                Util.formatDate(requestMoneyEntity.getCreateDateTime())
                        )
                ).collect(Collectors.toList());
            }
            catch (Exception exception){
                throw new ApplicationException(new CustomError("400", exception.getMessage()));
            }
        }catch (ApplicationException applicationException){
            throw applicationException;
        }
    }

    @Override
    public RequestResponse getRequests(String number, String headerSecretKey) {
        try{
            RequestResponse requestResponse = new RequestResponse();
            requestResponse.setOutgoingRequest(getOutgoingRequests(number, headerSecretKey));
            requestResponse.setIncomingRequest(getIncomingRequests(number, headerSecretKey));

            return requestResponse;
        }catch(ApplicationException exception){
            throw exception;
        }
    }

    @Override
    public RequestActionResponse payMoney(PayMoneyDto payMoneyDto, String headerSecretKey) {
        try{
            Util.isValidRequest(headerSecretKey, applicationPrperties.secretKey);
            RequestMoneyEntity requestMoneyEntity = null;

            try{
                requestMoneyEntity = requestMoneyRepository.findByRequestId(payMoneyDto.getRequestId()).get();
            }
            catch (Exception exception){
                throw new ApplicationException(new CustomError("400", applicationPrperties.errorMessage));
            }

            coreGateWay.checkPin(new CheckPinDto(requestMoneyEntity.getRequestTo(), payMoneyDto.getPin()));

            if(RequestMoneyStatus.PENDING.value.equals(requestMoneyEntity.getTransactionStatus())){
                try{
                    CoreTransactionResponse coreTransactionResponse = coreGateWay.perFormTransaction(new CoreTransactionRequest(requestMoneyEntity.getRequestTo(), requestMoneyEntity.getRequestFrom(), payMoneyDto.getTransactionType(), payMoneyDto.getPin(), requestMoneyEntity.getRequestedAmount()));
                    if(coreTransactionResponse.getStatus() != null && coreTransactionResponse.getStatus().equalsIgnoreCase("PROCESSED")){
                        try{
                            requestMoneyEntity.setTransactionStatus(RequestMoneyStatus.PROCESSED.value);
                            requestMoneyRepository.save(requestMoneyEntity);
                            smsGateway.sendRequestMoneySMS(requestMoneyEntity.getRequestFrom(), requestMoneyEntity.getRequestTo(), requestMoneyEntity.getRequestedAmount());

                            return new RequestActionResponse(requestMoneyEntity.getRequestId(), "Payment Done.");
                        }
                        catch (Exception exception){
                            throw new ApplicationException(new CustomError("400", applicationPrperties.errorMessage));
                        }
                    }
                    else{
                        throw new ApplicationException(new CustomError("400", applicationPrperties.errorMessage));
                    }
                }
                catch (ApplicationException applicationException){
                    throw applicationException;
                }
            }
            else{
                throw new ApplicationException(new CustomError("400", "This request already processed."));
            }
        }
        catch(ApplicationException exception){
            throw exception;
        }
    }

    @Override
    public RequestActionResponse declineRequest(String requestId, String headerSecretKey) {
        try{
            Util.isValidRequest(headerSecretKey, applicationPrperties.secretKey);
            RequestMoneyEntity requestMoneyEntity = null;

            try{
                requestMoneyEntity = requestMoneyRepository.findByRequestId(requestId).get();
            }
            catch (Exception exception){
                throw new ApplicationException(new CustomError("400", applicationPrperties.errorMessage));
            }

            if(RequestMoneyStatus.PENDING.value.equals(requestMoneyEntity.getTransactionStatus())){
                try{
                    requestMoneyEntity.setTransactionStatus(RequestMoneyStatus.DECLINED.value);
                    requestMoneyRepository.save(requestMoneyEntity);
                    return new RequestActionResponse(requestId,"Request Decline.");
                }
                catch (Exception exception){
                    throw new ApplicationException(new CustomError("400", applicationPrperties.errorMessage));
                }
            }
            else{
                throw new ApplicationException(new CustomError("400", "This request already processed."));
            }
        }
        catch(ApplicationException exception){
            throw exception;
        }
    }
}
