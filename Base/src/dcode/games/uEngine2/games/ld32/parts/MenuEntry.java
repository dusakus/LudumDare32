package dcode.games.uEngine2.games.ld32.parts;

/**
 * Created by dusakus on 18.04.15.
 */
public class MenuEntry {
    public int startX = -100, startY = -100;
    public boolean specialSource = false;
    public String specialKey = "MISSING";
    public String text = "ERROR";

    public MenuEntry(String s, int i, int i1) {
        text = s;
        startX = i;
        startY = i1;
    }
}
