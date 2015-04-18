package dcode.games.uEngine2.games.ld32.items;

import dcode.games.uEngine2.games.ld32.world.PlayerItem;

/**
 * Created by dusakus on 18.04.15.
 */
public class ItemList {

    private static PlayerItem[] itemList = new PlayerItem[64];


    public static void fillList() {

    }

    public static PlayerItem getItem(int id) {
        return itemList[id];
    }

    public static boolean itemExists(int id) {
        return itemList[id] != null;
    }

    public static int getSize() {
        return itemList.length;
    }
}
