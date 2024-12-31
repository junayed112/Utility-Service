package com.reddot.utilityservice.enums;

public enum UtilityTitle {

    DESCO_PREPAID("DESCO Prepaid","ডেসকো প্রিপেইড"),
    DESCO_POSTPAID("DESCO Postpaid","ডেসকো পোস্টপেইড"),

    TITAS_METER("Titas Gas Meter", "তিতাস গ্যাস মিটার"),
    TITAS("Titas Gas", "তিতাস গ্যাস"),
    TITAS_NON_METER("Titas Gas Non Meter", "তিতাস গ্যাস নন-মিটার"),
    TITAS_GAS_NON_METERED("TITAS Gas Non Metered","তিতাস গ্যাস নন মিটারড"),
    WASA("WASA","ওয়াসা");

    public final String en;
    public final String bn;

    UtilityTitle( String en, String bn) {
        this.en = en;
        this.bn = bn;
    }

}
