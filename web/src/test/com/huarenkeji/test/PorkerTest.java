package com.huarenkeji.test;


import java.util.HashMap;
import java.util.Map;

public class PorkerTest {


    public static Map<Integer,String> map = new HashMap<>();

    public static void main(String[] args) {
        map.put(2,"2332");
        int i = 2;
        System.out.println(map.get(i));
    }

}
