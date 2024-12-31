package com.reddot.utilityservice.requestmoney.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckPinDto {
    private String walletNo;
    private String pin;
}
