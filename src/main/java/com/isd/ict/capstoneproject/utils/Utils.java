package com.isd.ict.capstoneproject.utils;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.logging.Logger;
/**
 * The {@link Utils utils} class provides formats for all class
 * @author Group 3
 */public class Utils {

    public static DateFormat DATE_FORMATTER = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    private static final Logger LOGGER = getLogger(Utils.class.getName());
    static {
        System.setProperty("java.util.logging.SimpleFormatter.format", "[%4$-4s] [%1$tF %1$tT] [%2$-7s] %5$s %n");
    }

    /**
     * Get logger
     *
     * @param className
     * @return {@link Logger logger}
     */
    public static Logger getLogger(String className) {
        return Logger.getLogger(className);
    }

    /**
     * Get currency format
     *
     * @param num
     * @return {@link String string}
     */
    public static String getCurrencyFormat(int num) {
        Locale vietnam = new Locale("vi", "VN");
        NumberFormat defaultFormat = NumberFormat.getCurrencyInstance(vietnam);
        return defaultFormat.format(num);
    }
}
