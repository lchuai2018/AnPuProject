package com.example.anpuservice.utils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class StringTransUtil {

    public static List<Integer> getIntegerList(String strAry) {
        List<Integer> newPowerIds = Arrays.asList(strAry.split(","))
                .stream()
                .map(s -> Integer.parseInt(s.trim()))
                .collect(Collectors.toList());
        return newPowerIds;
    }

    public static Object isEmpty(Object param) {
        return param == null ? "" : param;
    }

    public static void main(String[] args) {
    }

}
