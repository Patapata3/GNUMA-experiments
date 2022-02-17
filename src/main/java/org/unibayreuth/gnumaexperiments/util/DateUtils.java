package org.unibayreuth.gnumaexperiments.util;

import java.util.Date;

public class DateUtils {
    public static long secondsBetween(Date date1, Date date2) {
        return (date2.getTime() - date1.getTime()) / 1000;
    }
}
