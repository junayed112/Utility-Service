package com.reddot.utilityservice.utils;

import com.reddot.utilityservice.custom_exceptions.ApplicationException;
import com.reddot.utilityservice.custom_exceptions.CustomError;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

@Component
public class SystemDateFormatter {
    public static String convertDate(String strDate) throws ParseException {
        String formattedDate = "";
        try {
            SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
            inputDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date date = inputDateFormat.parse(strDate);
            SimpleDateFormat outputDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a");
            String outputDate = outputDateFormat.format(date);
            System.out.println("Converted date: " + outputDate);
            return  outputDate;
        } catch (Exception e) {
            e.printStackTrace();
            return strDate;
        }
    }
}
