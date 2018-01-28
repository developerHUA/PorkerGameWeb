package com.huarenkeji.test;


import com.huarenkeji.porkergame.bean.User;

import java.util.ArrayList;
import java.util.List;

public class PorkerTest {

    private static List<PorkerTest> userList = new ArrayList<>();
    private List<PorkerTest> userList2 = new ArrayList<>();
    private int id;

    public static void main(String[] args) {


        PorkerTest user1 = new PorkerTest();
        user1.setUserId(0);
        PorkerTest user2 = new PorkerTest();
        user2.setUserId(1);
        PorkerTest user3 = new PorkerTest();
        user3.setUserId(2);
        PorkerTest user4 = new PorkerTest();
        user4.setUserId(3);
        userList.add(user1);
        userList.add(user2);
        userList.add(user3);
        userList.add(user4);
        user4.setUser();


    }

    public void setUserId(int id) {
        this.id = id;
    }

    public void setUser() {
        for (int i = 0; i < userList.size(); i++) {
            for (int j = 0; j < i; j++) {
                userList.get(i).userList2.add(userList.get(j));
            }

            for (int j = userList.size() -1 ; j > i ; j--) {
                userList.get(i).userList2.add(0,userList.get(j));
            }

            userList.get(i).userList2.add(userList.get(i));
        }


        for (int i = 0; i < userList.size(); i++) {
//            for (int j = userList.get(i).userList2.size() - 1; j >= 0; j--) {
//                System.out.println(userList.get(i).userList2.get(j).id);
//            }
            for (int j = 0; j < userList.get(i).userList2.size(); j++) {
                System.out.println(userList.get(i).userList2.get(j).id);
            }
            System.out.println("---------------");
        }


    }


}
