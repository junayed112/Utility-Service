package com.reddot.utilityservice.features.common;

import com.reddot.utilityservice.external_comunication.core.dto.CoreTransactionResponse;
import com.reddot.utilityservice.utils.SystemDateFormatter;

import java.math.BigDecimal;
import java.text.ParseException;

public class UtilityUtil {

    public static String getSMSTextUser(String type, CoreTransactionResponse coreTransactionResponse){
        try {
            return new StringBuilder("Pyament of TK ").append(coreTransactionResponse.getAmount())
                    .append(" made to ").append(type).append(" successfully.")
                    .append(" Fee TK ").append(coreTransactionResponse.getFee()).append(".")
                    .append(" Balance TK ").append(coreTransactionResponse.getBalanceFrom()).append(".")
                    .append(" TrxID ").append(coreTransactionResponse.getTxnId())
                    .append(" at ").append(SystemDateFormatter.convertDate(coreTransactionResponse.getTxnTime())).toString();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getSMSTextNotification(String type, CoreTransactionResponse coreTransactionResponse){
        return new StringBuilder("Pyament of TK ").append(coreTransactionResponse.getAmount())
                .append(" made to ").append(type).append(" successfully.")
                .append(" Fee TK ").append(coreTransactionResponse.getFee()).append(".")
                .append(" Balance TK ").append(coreTransactionResponse.getBalanceFrom()).append(".")
                .append(" TrxID ").append(coreTransactionResponse.getTxnId())
                .append(" at ").append(coreTransactionResponse.getTxnTime()).toString();
    }

    public static String walletNoToMsisdn(String walletNo) {
        String walletNoChecked = checkNumber(walletNo);
        return walletNoChecked.substring(0, 13);
    }

    public static String checkNumber(String accountNo) {
        if (accountNo.startsWith("88")) {
            return accountNo;
        }

        if (accountNo.startsWith("+88")) {
            accountNo = accountNo.substring(1);
        } else {
            StringBuilder sb = new StringBuilder(accountNo);
            sb.insert(0, "88");
            accountNo = sb.toString();
        }
        return accountNo;
    }

    public static String getRequestMoneySMS(String requestTo, BigDecimal requestedAmount){
        return new StringBuilder("Received TK ").append(requestedAmount)
                .append(" from ").append(requestTo).append(" successfully.").toString();
    }

}
