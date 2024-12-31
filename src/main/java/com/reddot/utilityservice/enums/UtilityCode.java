package com.reddot.utilityservice.enums;

import com.reddot.utilityservice.custom_exceptions.ApplicationException;
import com.reddot.utilityservice.custom_exceptions.CustomError;

public enum UtilityCode {

    DESCO_PREPAID("DESCO_PREPAID"),
    WASA("WASA_GENERAL-BILL"),

    DESCO_POSTPAID("DESCO_POSTPAID"),
    TITAS_METERED("TITAS_METERED"),
    TITAS_NON_METERED("TITAS_NON-METERED"),

    TITAS_GAS_NON_METERED("TITAS_NON-METERED"),
    TITAS_GAS_METERED("TITAS_METER");

    public final String code;

    UtilityCode(String code) {
        this.code = code;
    }

    public static UtilityCode fromString(String code) {

        for (UtilityCode currentCode: UtilityCode.values()){
            if (code.equals(currentCode.code)){
                return currentCode;
            }
        }
        throw new ApplicationException(new CustomError("400","Code not matched"));
    }
}
