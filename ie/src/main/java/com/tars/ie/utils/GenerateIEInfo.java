package com.tars.ie.utils;

import com.tars.ie.entity.IEInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GenerateIEInfo {
    static String getWTBH(String wtbh) {
        int len = wtbh.length();
        return "0".repeat(8 - len) + wtbh;
    }

    public static String randomWTBH() {
        StringBuilder wtbh = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            wtbh.append(new Random().nextInt(10));
        }
        return wtbh.toString();
    }

    static String getXM() {
        String names = "张王李赵谢刘孙";
        String nums = "零一二三四五六七八九十";
        Random random = new Random();
        int randomName = random.nextInt(names.length()) + 1;
        int randomLength = random.nextInt(3);
        StringBuilder name = new StringBuilder(
                String.valueOf(names.charAt(randomName)));
        for (int i = 0; i < randomLength; i++) {
            name.append(nums.charAt(random.nextInt(nums.length())));
        }
        return name.toString();
    }

    public static IEInfo generateIEInfo(String wtbh) {
        IEInfo ieInfo = new IEInfo();
        wtbh = getWTBH(wtbh);
        ieInfo.setWtbh(wtbh);
        ieInfo.setBgrxm(getXM());
        ieInfo.setFinish(-1);
        ieInfo.setIsDelete(0);
        return ieInfo;
    }

}
