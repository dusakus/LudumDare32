package dcode.games.uEngine2.games.ld32.render;

import dcode.games.uEngine2.GFX.ILayer;
import dcode.games.uEngine2.StData;
import dcode.games.uEngine2.games.ld32.LStData;
import dcode.games.uEngine2.tools.numbarTools;

import java.awt.*;

/**
 * Created by dusakus on 20.04.15.
 */
public class Layer_StatusOverlay implements ILayer {
	@Override
	public void draw(Graphics2D G2D) {
		int h = (int) ((float) (LStData.maxHealth - LStData.healthStat) / LStData.maxHealth * 45);
		int a = (int) ((float) (LStData.maxAmmo - LStData.ammoStat) / LStData.maxAmmo * 16);

		h = numbarTools.clamp(h, 0, 45);
		a = numbarTools.clamp(a, 0, 16);

		G2D.drawImage(StData.resources.grf.getTexture("HBAR" + h), 2, 2, null);
		G2D.drawImage(StData.resources.grf.getTexture("ABAR" + a), 101, 2, null);
	}

	@Override
	public boolean removeMe() {
		return false;
	}

	@Override
	public boolean renderMe() {
		return LStData.currentStatus == 201;
	}
}
