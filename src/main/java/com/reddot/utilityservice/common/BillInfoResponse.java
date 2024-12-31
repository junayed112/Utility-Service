package com.reddot.utilityservice.common;

import lombok.Data;

import java.util.ArrayList;
import java.util.SimpleTimeZone;

@Data
public class BillInfoResponse {
    String transaction_id;
    String due_amount;
    ArrayList<InfoItem> infoItemArrayList = new ArrayList<>();
}
