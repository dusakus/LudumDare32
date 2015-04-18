package dcode.games.uEngine2.games.ld32.parts;

import dcode.games.uEngine2.GFX.sprites.Sprite;
import dcode.games.uEngine2.StData;
import dcode.games.uEngine2.games.ld32.MenuLogic;

import java.awt.*;

/**
 * Created by dusakus on 18.04.15.
 */
public class MenuPointer extends Sprite {

    MenuLogic ml;

    public MenuPointer(MenuLogic dataSource) {
        ml = dataSource;
    }

    @Override
    public Image getCustomTexture() {
        return null;
    }

    @Override
    public boolean doCustomRender() {
        return true;
    }

    @Override
    public void customRender(Graphics2D G) {
        Point p = ml.getCurrentPoinerCoords();
        G.drawImage(StData.resources.grf.getTexture("MARR"), p.x - 15, p.y - 7, null);
    }
}
