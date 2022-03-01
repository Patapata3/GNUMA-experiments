package org.unibayreuth.gnumaexperiments.util;

import java.util.Date;

public class DateUtils {
    /**
     * Get a number of seconds between two dates
     * @param date1 - first date
     * @param date2 - second date
     * @return - number of seconds
     */
    public static long secondsBetween(Date date1, Date date2) {
        return (date2.getTime() - date1.getTime()) / 1000;
    }
}
