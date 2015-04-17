package dcode.games.uEngine2.games.ld32;

import dcode.games.uEngine2.GFX.ILayer;

import java.awt.*;

/**
 * Created by dusakus on 10.02.15.
 */
public class LAYER_Loading implements ILayer {
    int blkcount = 1;

    int targetStatus;

    public LAYER_Loading(int target) {
        targetStatus = target;
    }

    @Override
    public void draw(Graphics2D G2D) {
        G2D.setFont(new Font(Font.MONOSPACED, Font.BOLD, 20));
        G2D.setColor(Color.BLACK);
        G2D.fillRect(8, 8, 384, 224);
        G2D.setColor(Color.GREEN);
        G2D.drawString("Current Mode: " + LStData.currentMode, 16, 40);
        G2D.drawString("Current Status: " + LStData.currentStatus + " / " + targetStatus, 16, 56);


        G2D.setColor(Color.green);
        G2D.drawString("LOADING", 13, 192);

        G2D.setColor(Color.green);
        for (int i = 0; i < blkcount; i++) {
            G2D.fillRect(12 + i * 8, 194, 6, 10);
        }
        blkcount++;
        if (blkcount > 11) {
            blkcount = 1;
        }

    }

    @Override
    public boolean removeMe() {
        return LStData.currentStatus == targetStatus;
    }

    @Override
    public boolean renderMe() {
        return true;
    }
}
