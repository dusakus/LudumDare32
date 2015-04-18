package dcode.games.uEngine2.games.ld32.world;

import dcode.games.uEngine2.BGTasks.internalTasks.LoadBasicTexture;
import dcode.games.uEngine2.GFX.sprites.Sprite;
import dcode.games.uEngine2.StData;
import dcode.games.uEngine2.games.ld32.LStData;

import java.awt.*;

/**
 * Created by dusakus on 18.04.15.
 */
public class WorldPlayer extends Sprite {

    public int inRoomX = 100;
    public int inRoomY = 100;
    private String itemMod = "";
    private int animId = 1;             // 1-4 facing left, 11-14 facing right
    private int animFrame = 1;
    private int lastInRoomX = 100;
    private int lastInRoomY = 100;

    private int animDelay = 3;

    public WorldPlayer(int x, int y) {
        inRoomX = x;
        inRoomY = y;

        lastInRoomX = x;
        lastInRoomY = y;
    }

    public static void loadTextures(PlayerItem item) {
        String s = "";
        if (item != null && item.isMainItem) s = item.playerModifier;
        for (int i = 1; i < 5; i++) {
            StData.generalBGT.LPTasks.add(new LoadBasicTexture("sprite/player/PLA" + s + "FRAME" + i + ".png", "PLA" + s + i));             //Load textures for both facings
            StData.generalBGT.LPTasks.add(new LoadBasicTexture("sprite/player/PLA" + s + "FRAME" + (i + 10) + ".png", "PLA" + s + (i + 10)));
        }
    }

    @Override
    public Image getCustomTexture() {
        return StData.resources.grf.getTexture("PLA" + itemMod + animId);
    }

    @Override
    public boolean doCustomRender() {
        return false;
    }

    @Override
    public void customRender(Graphics2D G) {

    }

    public void updateCoords(int x, int y) {
        //TODO: proper movement, with collisions

        inRoomX += x;
        inRoomY += y;
    }

    public void updatePlayer() {
        if (animDelay == 0) {
            if (lastInRoomX != inRoomX || lastInRoomY != inRoomY) {
                animFrame++;
                if (animFrame > 4) animFrame = 1;
                if (lastInRoomX < inRoomX) animId = animFrame + 10;
                if (lastInRoomX > inRoomX) animId = animFrame;
                lastInRoomX = inRoomX;
                lastInRoomY = inRoomY;
                animDelay = 3;
            }
        } else {
            animDelay--;
        }

        this.x = inRoomX - LStData.renderOffsetX;
        this.y = inRoomY - LStData.renderOffsetY;
    }
}
