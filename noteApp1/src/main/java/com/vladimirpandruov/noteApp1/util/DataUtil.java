package com.vladimirpandruov.noteApp1.util;

import java.time.format.DateTimeFormatter;

public class DataUtil {

    public static DateTimeFormatter dateTimeFormatter() {
        return DateTimeFormatter.ofPattern("MM-dd-yyyy hh:mm:ss");
    }

}
