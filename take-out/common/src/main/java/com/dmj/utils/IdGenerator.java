package com.dmj.utils;

import java.util.Random;
import java.util.UUID;

public class IdGenerator {
    public static void main(String[] args) {
        System.out.println(generateLongId16());
    }
    public static String generateStringId16() {
        long timestamp = System.currentTimeMillis();
        Random random = new Random();
        int randomNum = random.nextInt(900000) + 100000; // 生成6位随机数
        String id = String.valueOf(timestamp) + String.valueOf(randomNum);
        return id.substring(0, 16);
    }
    public static Long generateLongId16() {
        return Long.parseLong(generateStringId16());
    }
}
