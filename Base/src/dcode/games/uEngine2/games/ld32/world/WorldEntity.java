package dcode.games.uEngine2.games.ld32.world;

import dcode.games.uEngine2.GFX.sprites.Sprite;
import dcode.games.uEngine2.StData;

import java.awt.*;

/**
 * Created by dusakus on 18.04.15.
 */
public class WorldEntity extends Sprite{

	protected int textureVariation = 0;
	public String texKey = "MISS";


	public void tick(){

	}

	@Override
	public Image getCustomTexture() {
		return StData.resources.grf.getTexture(texKey+textureVariation);
	}

	@Override
	public boolean doCustomRender() {
		return false;
	}

	@Override
	public void customRender(Graphics2D G) {

	}
}
