package com.reddot.utilityservice.enums;



public enum UtilityKeys {

    DESCO_POSTPAID( "DE16673927669120", "upX5OgO36etaMf1x"),
    DESCO_PREPAID( "DE16673928093648", "pDd6gAaPSGi12vDs");

    public final String auth_key;
    public final String secrete_key;

    UtilityKeys(String authKey, String secreteKey) {
        auth_key = authKey;
        secrete_key = secreteKey;
    }


}
