package com.reddot.utilityservice.utils;

import com.reddot.utilityservice.common.TransactionEntity;
import com.reddot.utilityservice.custom_exceptions.ApplicationException;
import com.reddot.utilityservice.custom_exceptions.CustomError;
import com.reddot.utilityservice.external_comunication.ssl_services.dto.service_list.UtilityDetailData;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.OffsetDateTime;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class Util {

    public static Date getStartOfDay() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DATE);
        calendar.set(year, month, day, 0, 0, 0);
        long approximateTimestamp = calendar.getTime().getTime();
        long extraMillis = (approximateTimestamp % 1000);
        long exactTimestamp = approximateTimestamp - extraMillis;
        return new Date(exactTimestamp);
    }

    public static Date getEndOfDay() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DATE);
        calendar.set(year, month, day, 23, 59, 59);
        long approximateTimestamp = calendar.getTime().getTime();
        long extraMillis = (approximateTimestamp % 1000);
        long exactTimestamp = approximateTimestamp - extraMillis + 999;
        return new Date(exactTimestamp);
    }

    public static String updateMsisdn(String inputMsisdn){
        return ("88" + inputMsisdn.substring(inputMsisdn.length()-11, inputMsisdn.length()));
    }

    public static Date atStartOfDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public static Date atEndOfDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }

    public static String encodeToBase64(String str){
        try {
           return Base64.getEncoder().encodeToString(str.getBytes());
        }catch (Exception e){
            e.printStackTrace();
            throw new ApplicationException(new CustomError("400","Some thing went wrong"));
        }
    }

    public static String getTransactionId(String walletNo){
        return encodeToBase64(new Random().nextInt(9999)+ updateMsisdn(walletNo).substring(2) + System.currentTimeMillis());
    }

    public static void setTransactionEntityProperties(TransactionEntity transactionEntity, BigDecimal amount, String notificationNumber, String coreTransactionStatus, String coreTransactionStatusCode, String coreCommission, String coreFee, String coreTransactionId, String fromAccount){
        transactionEntity.setAmount(amount);
        transactionEntity.setNotificationNumber(notificationNumber);
        transactionEntity.setCoreTransactionStatus(coreTransactionStatus);
        transactionEntity.setCoreTransactionStatusCode(coreTransactionStatusCode);
        transactionEntity.setCoreCommission(coreCommission);
        transactionEntity.setCoreFee(coreFee);
        transactionEntity.setCoreTxnId(coreTransactionId);
        transactionEntity.setFromAccount(fromAccount);
    }

    public static String getRequestId(String requestFrom, String requestTo){
        return encodeToBase64(
                new Random().nextInt(9999)
                        + requestFrom.substring(1, requestFrom.length() -1)
                        + requestTo.substring(1, requestTo.length() -1)
                        + System.currentTimeMillis());
    }

    public static HttpHeaders getHeaders(){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        return headers;
    }
    public static Pageable getPageableObject(Integer pageNo, Integer pageSize){
        Pageable pageable = PageRequest.of(pageNo, pageSize);

        return pageable;
    }

    public static void isValidRequest(String headerSecretKey, String secretKey){
        if(headerSecretKey == null || headerSecretKey.isEmpty() || !headerSecretKey.equals(secretKey)){
            throw new ApplicationException(new CustomError("401", "Invalid secret key."));
        }
    }

    public static String formatDate(OffsetDateTime offsetDateTime){
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy hh:mm aa");
        Date date = Date.from(offsetDateTime.toInstant());
        return sdf.format(date);
    }

}
