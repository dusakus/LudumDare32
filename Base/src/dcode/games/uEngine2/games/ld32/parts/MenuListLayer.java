package dcode.games.uEngine2.games.ld32.parts;

import dcode.games.uEngine2.GFX.ILayer;
import dcode.games.uEngine2.StData;
import dcode.games.uEngine2.games.ld32.LStData;
import dcode.games.uEngine2.games.ld32.MenuLogic;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by dusakus on 18.04.15.
 */
public class MenuListLayer implements ILayer {

	MenuLogic ml;

	public MenuListLayer(MenuLogic menuLogic) {
		ml = menuLogic;
	}

	@Override
	public void draw(Graphics2D G2D) {
		ArrayList<MenuEntry> items = ml.getEntryList();

		for(MenuEntry me : items){
			if(me.specialSource){
				G2D.drawImage(StData.resources.grf.getTexture(me.specialKey), me.startX, me. startY, null);
			} else {
				G2D.drawImage(StData.resources.grf.getString("FNTW", me.text), me.startX, me. startY, null);
			}
		}

	}

	@Override
	public boolean removeMe() {
		return false;
	}

	@Override
	public boolean renderMe() {
		return LStData.currentStatus > 250 && LStData.currentStatus < 350;
	}
}
