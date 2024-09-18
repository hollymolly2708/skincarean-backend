package com.skincareMall.skincareMall.utils;

import java.sql.Timestamp;

public class Utilities {
    private Utilities(){
        throw new UnsupportedOperationException("Cannot instantiate this class");
    }
    public static Timestamp changeFormatToTimeStamp() {
        return new Timestamp(System.currentTimeMillis());
    }
    public static Timestamp changeFormatToTimeStamp(Long timeMillis){
        return new Timestamp(timeMillis);
    }

    public static Long next30days() {
        return System.currentTimeMillis() + (1000 * 16 * 24 * 30);
    }
}
