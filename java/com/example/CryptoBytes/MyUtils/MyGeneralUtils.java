package com.example.CryptoBytes.MyUtils;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MyGeneralUtils {
    public static String pattern="#,###,###.####";
    public static DecimalFormat numberFormat = new DecimalFormat(pattern);

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public static int getIndexOfString(String[] strAry, String s)
    {
        for(int i=0;i<strAry.length;i++)
        {
            if(strAry[i].equals(s))
                return i;
        }
        return -1;
    }

    public static String getCurrentAndTimeAsString()
    {
        return (String) new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(new Date());
    }

    public static String getFloatDecimalFormattedString(double num)
    {
        return numberFormat.format(Math.abs(num));
    }
}
