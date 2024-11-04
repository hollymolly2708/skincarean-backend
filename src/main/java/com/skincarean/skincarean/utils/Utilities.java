package com.skincarean.skincarean.utils;

import java.sql.Timestamp;
import java.util.Random;

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
        return System.currentTimeMillis() + (1000 * 60 * 24 * 30);
    }

    public static String generatePaymentCode(int length){
        Random random = new Random();
        StringBuilder code = new StringBuilder();

        for(int i =0; i < length; i++){
            int digit = random.nextInt(10);
            code.append(digit);
        }
        return code.toString();
    }
}
