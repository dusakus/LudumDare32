package dcode.games.uEngine2.games.ld32.world;

import dcode.games.uEngine2.games.ld32.parts.WorldTrigger;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by dusakus on 18.04.15.
 */
public class GameWorld {
	public ArrayList<WorldEntity> entities;
	public WorldPlayer wp;
	public WorldTrigger[] triggers = new WorldTrigger[255];


	public GameWorld(){
		entities = new ArrayList<WorldEntity>();
	}

	public void tick(){
		for (WorldEntity we : entities){
			if(we != null) we.tick();
		}
		wp.updatePlayer();
		for (int i = 0; i < 255; i++) {
			if(triggers[i] != null){
				triggers[i].checkTrigger(getDataAt(wp.inRoomX, wp.inRoomY));
			}
		}
	}

	private Color getDataAt(int inRoomX, int inRoomY) {
		return new Color(255,255,255);
	}

	public void trigger(Color data){

	}
}
