package com.huarenkeji.porkergame.bean;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 */
public class DDZPorker extends Porker implements Comparable<DDZPorker> {

    public static final int TWO_SIZE = 12;
    public static final int SMALL_KING_SIZE = 13;
    public static final int BIG_KING_SIZE = 14;

    public int porkerSize;

    public static List<DDZPorker> getAllPoker() {
        List<DDZPorker> allPoker = new ArrayList<>();
        int type = 1;
        int id = 0;
        int porkerSize = 11;
        for (int i = 1; i <= 52; i++) {
            if (porkerSize > 12) {
                porkerSize = 0;
            }
            DDZPorker porker = new DDZPorker();
            porker.porkerId = id;
            porker.porkerType = type;
            porker.porkerSize = porkerSize;
            porkerSize++;
            allPoker.add(porker);
            if (i % 13 == 0) {
                type++;
            }
            id++;
        }
        DDZPorker porker = new DDZPorker();
        porker.porkerId = 52;
        porker.porkerType = SMALL_KING;
        porker.porkerSize = 13;
        DDZPorker porker2 = new DDZPorker();
        porker2.porkerId = 53;
        porker2.porkerType = BIG_KING;
        porker2.porkerSize = 14;
        allPoker.add(porker);
        allPoker.add(porker2);
        Collections.sort(allPoker);
        return allPoker;
    }


    public static List<DDZPorker> getMovePoker(int moveType) {
        return getShufflePoker(moveType);
    }


    public static List<DDZPorker> getShufflePoker(int moveType) {
        List<DDZPorker> allPoker = getAllPoker();
        switch (moveType) {
            case Room.REMOVE_DOUBLE_THREE:
                allPoker.remove(allPoker.size()-1);
                allPoker.remove(allPoker.size()-1);
                break;
            case Room.REMOVE_DOUBLE_TWO:
                allPoker.remove(3);
                allPoker.remove(2);
                break;
            case Room.REMOVE_ONE_AND_TWO:
                allPoker.remove(6);
                allPoker.remove(5);
                break;
        }

        Collections.shuffle(allPoker);
        return allPoker;
    }


    @Override
    public int compareTo(DDZPorker ddzPorker) {
        int temp2 = this.porkerSize;
        int temp1 = ddzPorker.porkerSize;
        int sort = temp1 - temp2;
        return sort == 0 ?
                ddzPorker.porkerType - this.porkerType : sort;
    }


}
