package com.planetsystems.tela.managementapp.shared;

public enum DatePattern {
    DAY_MONTH_YEAR("dd/MM/yyyy"),	//31-01-2012
    DAY_MONTH_YEAR_HOUR_MINUTE_SECONDS("dd/MM/yyyy HH:mm:ss"), //31-01-2012 23:59:59
    MONTH_DAY_YEAR("MM/dd/yyyy"),	//01-31-2012
    MONTH_DAY_YEAR_HOUR_MINUTE_SECONDS("MM/dd/yyyy HH:mm:ss"), //01-31-2012 23:59:59
    YEAR_MONTH_DAY("yyyy/MM/dd"),	//2012-01-31
    YEAR_MONTH_DAY_HOUR_MINUTE_SECONDS("yyyy/MM/dd HH:mm:ss"),	//2012-01-31 23:59:59
    YEAR_MONTH_DAY_HOUR_MINUTE_SECOND_MILLISECONDS("yyyy/MM/dd HH:mm:ss.SSS"), //2012-01-31 23:59:59.999
    HOUR_MINUTE_SECONDS("HH:mm:ss"),
    HOUR_MINUTE("HH:mm"),
    DAY("EEEE"),
    YEAR("yyyy"),
    DAY_DATE("EEEE dd/MM/yyyy");  //17:14:08


    private final String pattern;

    DatePattern(String pattern) {
        this.pattern = pattern;
    }

    public String getPattern() {
        return pattern;
    }

    public static DatePattern getDatePattern(String pattern) {
        for (DatePattern pattern1 : DatePattern.values()) {
            if (pattern1.getPattern().equalsIgnoreCase(pattern)) {
                return pattern1;
            }
        }
        return null;
    }

    public static String getType(String pattern) {
        int i = 0;
        for (DatePattern pattern1 : DatePattern.values()) {
            if (pattern1.getPattern().equalsIgnoreCase(pattern)) {
                return Integer.toString(i);
            }
            i++;
        }
        return null;
    }


}
