package dcode.games.uEngine2.games.ld32.worlds;

import dcode.games.uEngine2.BGTasks.internalTasks.LoadBasicTexture;
import dcode.games.uEngine2.StData;
import dcode.games.uEngine2.games.ld32.InHandler;
import dcode.games.uEngine2.games.ld32.LStData;
import dcode.games.uEngine2.games.ld32.entity.IEntityData;
import dcode.games.uEngine2.games.ld32.entity.enemy.Grapes;
import dcode.games.uEngine2.games.ld32.entity.enemy.Onion;
import dcode.games.uEngine2.games.ld32.entity.enemy.Potato;
import dcode.games.uEngine2.games.ld32.world.GameWorld;
import dcode.games.uEngine2.games.ld32.world.WorldEntity;

import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * Created by dusakus on 19.04.15.
 */
public class World2 extends PWorld {
	@Override
	public String getTexID() {
		return "WORLD";
	}

	@Override
	public void loadYerself(GameWorld target) {
		StData.generalBGT.LPTasks.add(new LoadBasicTexture("world/lvl-002.png", "WORLD"));
		StData.generalBGT.LPTasks.add(new LoadBasicTexture("world/lvl-002f_DATA.png", "WORLDD"));

		target.entities.add(new WorldEntity(new PotatoDATA(342, 276), new Potato(), 15, 30));
		target.entities.add(new WorldEntity(new PotatoDATA(392, 276), new Potato(), 15, 30));
		target.entities.add(new WorldEntity(new PotatoDATA(442, 276), new Potato(), 15, 30));

		target.entities.add(new WorldEntity(new PotatoDATA(871, 276), new Potato(), 15, 30));
		target.entities.add(new WorldEntity(new PotatoDATA(911, 276), new Potato(), 15, 30));
		target.entities.add(new WorldEntity(new PotatoDATA(951, 276), new Grapes(), 15, 26));
		target.entities.add(new WorldEntity(new PotatoDATA(991, 276), new Potato(), 15, 30));
		target.entities.add(new WorldEntity(new PotatoDATA(1051, 276), new Potato(), 15, 30));
		target.entities.add(new WorldEntity(new PotatoDATA(1091, 276), new Grapes(), 15, 26));

		target.entities.add(new WorldEntity(new OnionDATA(1120, 156), new Onion(), 15, 30));
		target.entities.add(new WorldEntity(new OnionDATA(1440, 220), new Onion(), 15, 30));
		target.entities.add(new WorldEntity(new OnionDATA(2136, 199), new Onion(), 15, 30));

		target.entities.add(new WorldEntity(new PotatoDATA(1195, 220), new Potato(), 15, 30));
		target.entities.add(new WorldEntity(new PotatoDATA(1235, 220), new Potato(), 15, 30));

		target.entities.add(new WorldEntity(new PotatoDATA(1231, 150), new Grapes(), 15, 26));
		target.entities.add(new WorldEntity(new PotatoDATA(1281, 156), new Potato(), 15, 30));

		target.entities.add(new WorldEntity(new PotatoDATA(1738, 276), new Potato(), 15, 30));
		target.entities.add(new WorldEntity(new PotatoDATA(1808, 276), new Potato(), 15, 30));
		target.entities.add(new WorldEntity(new PotatoDATA(1878, 276), new Potato(), 15, 30));
		target.entities.add(new WorldEntity(new PotatoDATA(1948, 276), new Potato(), 15, 30));
		target.entities.add(new WorldEntity(new PotatoDATA(2018, 276), new Potato(), 15, 30));
		target.entities.add(new WorldEntity(new PotatoDATA(2088, 276), new Grapes(), 15, 30));
		target.entities.add(new WorldEntity(new PotatoDATA(2158, 276), new Grapes(), 15, 30));
		target.entities.add(new WorldEntity(new PotatoDATA(2228, 276), new Potato(), 15, 30));

		target.entities.add(new WorldEntity(new PotatoDATA(2312, 276), new Grapes(), 15, 30));

		target.wp.inRoomY = 185;
		target.wp.inRoomX = 10;

	}

	@Override
	public boolean checkUnlocked(int RColor) {
		return false;
	}

	@Override
	public void triggerEvent(Color data) {
		switch (data.getBlue() - 100) {
			case 0:
				LStData.currentStatus = 209;
				break;
			case 1:
				LStData.GL.getPlayer().hazPhysics = false;
				if (InHandler.instance.isKeyPressed(KeyEvent.VK_UP)) LStData.GL.getPlayer().inRoomY -= 2;
				if (InHandler.instance.isKeyPressed(KeyEvent.VK_DOWN)) LStData.GL.getPlayer().inRoomY += 2;
				break;
			case 2:
				LStData.GL.getPlayer().hazPhysics = true;
				break;
		}
	}

	private class PotatoDATA implements IEntityData {

		Point p;

		public PotatoDATA(int x, int y) {
			p = new Point(x, y);
		}

		@Override
		public int getType() {
			return 0;
		}

		@Override
		public Point getInitialLocation() {
			return p;
		}

		@Override
		public String getTextureId() {
			return "EnPOT";
		}

		@Override
		public int getDepth() {
			return 0;
		}

		@Override
		public int getHealth() {
			return 200;
		}
	}

	private class OnionDATA implements IEntityData {
		Point p;

		public OnionDATA(int i, int i1) {
			p = new Point(i, i1);
		}

		@Override
		public int getType() {
			return 0;
		}

		@Override
		public Point getInitialLocation() {
			return p;
		}

		@Override
		public String getTextureId() {
			return "EnONI";
		}

		@Override
		public int getDepth() {
			return 0;
		}

		@Override
		public int getHealth() {
			return 500;
		}
	}

}
