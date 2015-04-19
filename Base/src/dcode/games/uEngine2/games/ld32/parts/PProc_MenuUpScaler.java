package dcode.games.uEngine2.games.ld32.parts;

import dcode.games.uEngine2.GFX.postproc.IGrfPostProcessor;
import dcode.games.uEngine2.games.ld32.LStData;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by dusakus on 18.04.15.
 */
public class PProc_MenuUpScaler implements IGrfPostProcessor {
	@Override
	public BufferedImage processFrame(BufferedImage NextFrame) {
		Graphics g = NextFrame.createGraphics();
		g.drawImage(NextFrame.getSubimage(0, 60, 200, 80).getScaledInstance(400, 160, BufferedImage.SCALE_REPLICATE), 0, 60, null);
		g.dispose();
		return NextFrame;
	}

	@Override
	public boolean enabled() {
		return LStData.currentMode == LStData.MODE_MENU_MAIN;
	}
}
