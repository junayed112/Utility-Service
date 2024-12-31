package com.reddot.utilityservice.enums;

public interface DropdownItems {

    enum MONTHS{
        JANUARY,
        FEBRUARY,
        MARCH,
        APRIL,
        MAY,
        JUN,
        JULY,
        AUGUST,
        SEPTEMBER,
        OCTOBER,
        NOVEMBER,
        DECEMBER
    }
}


//public enum DAY {MON, TUES, WED, THU, FRI, SAT, SUN};
//EnumSet.allOf(DAY.class).stream().map(e -> e.name()).collect(Collectors.toList())