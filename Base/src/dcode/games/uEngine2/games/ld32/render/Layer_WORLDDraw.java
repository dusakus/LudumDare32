package dcode.games.uEngine2.games.ld32.render;

import dcode.games.uEngine2.GFX.ILayer;
import dcode.games.uEngine2.StData;
import dcode.games.uEngine2.games.ld32.LStData;
import dcode.games.uEngine2.games.ld32.world.WorldPlayer;
import dcode.games.uEngine2.tools.numbarTools;

import java.awt.*;

/**
 * Created by dusakus on 18.04.15.
 */
public class Layer_WORLDDraw implements ILayer {
	@Override
	public void draw(Graphics2D G2D) {
		G2D.drawImage(StData.resources.grf.getPartTexture("WORLD",  //TODO: get the WORLD texture
				numbarTools.clamp(((WorldPlayer) StData.currentGC.currentSC.sprites[2]).inRoomX - 200, 0, LStData.roomWidth - 400),
				numbarTools.clamp(((WorldPlayer) StData.currentGC.currentSC.sprites[2]).inRoomY - 150, 0, LStData.roomHeight - 300),
				400, 300
		), 0, 0, null);
	}

	@Override
	public boolean removeMe() {
		return false;
	}

	@Override
	public boolean renderMe() {
		return LStData.currentStatus == 201; // if is in game
	}
}
