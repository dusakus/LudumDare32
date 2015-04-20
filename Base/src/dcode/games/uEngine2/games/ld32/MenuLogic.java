package dcode.games.uEngine2.games.ld32;

import dcode.games.uEngine2.GFX.ScreenContent;
import dcode.games.uEngine2.GFX.layers.FillTextureLayer;
import dcode.games.uEngine2.LOGIC.ILogicTask;
import dcode.games.uEngine2.StData;
import dcode.games.uEngine2.games.ld32.parts.MenuEntry;
import dcode.games.uEngine2.games.ld32.parts.MenuListLayer;
import dcode.games.uEngine2.games.ld32.parts.MenuPointer;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import static dcode.games.uEngine2.games.ld32.LStData.*;

/**
 * Created by dusakus on 10.04.15.
 */
public class MenuLogic implements ILogicTask {

    private ScreenContent inMenuSC;
    private ArrayList<MenuEntry> content;

    private int currentSelection = 1;
    private int maxSelection = 4;
    private int inputDelay = 10;

    private int confirmedStatusShift = 1;

    @Override
    public boolean isReady() {
        return LStData.currentMode == LStData.MODE_MENU_MAIN;
    }

    @Override
    public void perform() {
        switch (currentStatus) {
            case 1:
                //TODO: leave the loop
                StData.LOG.println("[MenuLogic] Entry point reached");
                currentStatus++;
                break;
            case 2:
                if (inMenuSC == null) currentStatus = 501;
                else currentStatus = 10;
                break;

            case 10:
                //set menu screen and enter menu loop
                StData.currentGC.currentSC = inMenuSC;
                currentStatus = 121; // enter main screen
                break;

            //MAIN
            case 121:
                content = new ArrayList<MenuEntry>();
                content.add(new MenuEntry("Start Game", 20, 60));
                content.add(new MenuEntry("Options", 20, 70));
                content.add(new MenuEntry("Reset", 20, 80));
                content.add(new MenuEntry("EXIT", 20, 90));

                currentStatus = 260;
                break;

            case 260:
                monitorInput();
                break;

            case 501:  //Create screen content
                StData.LOG.println("[MenuLogic] first run, creating content");
                inMenuSC = new ScreenContent();
                currentStatus++;
                break;
            case 502:  //Create MenuBgFiller layer
                inMenuSC.layers_Center.add(new FillTextureLayer("MeBG1")); //TODO: get that texture somewhere
                //inMenuSC.postProcessors[0] = new PProc_MenuUpScaler();
                currentStatus++;
                break;
            case 503:  //Create menu pointer
                inMenuSC.sprites[2] = new MenuPointer(this);
                inMenuSC.sprites_front[0] = 2;
                currentStatus++;
                break;
            case 504:
                inMenuSC.layers_Center.add(new MenuListLayer(this));
                currentStatus = 2;
                break;
            case 1001:
                if (currentSelection == 1) {
                    //Enter the game, (TEMP)
                    currentMode = MODE_GAME_PLAY;
                    currentStatus = 1;
                }
        }
    }

    private void monitorInput() {
        if (inputDelay > 0) inputDelay--;
        else {
            if (currentSelection > 1 && (InHandler.instance.isKeyPressed(KeyEvent.VK_UP) || InHandler.instance.isKeyPressed(KeyEvent.VK_W))) {
                currentSelection--;
                inputDelay = 5;
            }
            if (currentSelection < maxSelection && (InHandler.instance.isKeyPressed(KeyEvent.VK_DOWN) || InHandler.instance.isKeyPressed(KeyEvent.VK_S))) {
                currentSelection++;
                inputDelay = 5;
            }
        }
        if (InHandler.instance.isKeyPressed(KeyEvent.VK_ENTER)) currentStatus = 1000 + confirmedStatusShift;
    }

    @Override
    public boolean doRepeat() {
        return true;
    }

    public Point getCurrentPoinerCoords() {
        return new Point(18, 55 + currentSelection * 10);
    }

    public ArrayList<MenuEntry> getEntryList() {
        return content;
    }
}
