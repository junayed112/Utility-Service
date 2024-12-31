package com.reddot.utilityservice.base;


//"status": "payment_success", "status_code": "111", "status_title": "PaymentSuccess", "data": {
//        "status": "000",
//        "m essage": "success", "stkid": "NRB"
//        },
//        "lid": "NRDE611384ACA4447202108115407"


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SSLBaseResponse {
    private String status;
    private String status_code;
    private String status_title;
    private String message = null;
    private String lid;
    private String transaction_id = null;

//    private String;
//    private String;
//    private String;
//    private String;
//    private String;

}

