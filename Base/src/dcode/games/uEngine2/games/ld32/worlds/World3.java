package dcode.games.uEngine2.games.ld32.worlds;

import dcode.games.uEngine2.BGTasks.internalTasks.LoadBasicTexture;
import dcode.games.uEngine2.StData;
import dcode.games.uEngine2.games.ld32.InHandler;
import dcode.games.uEngine2.games.ld32.LStData;
import dcode.games.uEngine2.games.ld32.entity.IEntityData;
import dcode.games.uEngine2.games.ld32.entity.enemy.Grapes;
import dcode.games.uEngine2.games.ld32.entity.enemy.Onion;
import dcode.games.uEngine2.games.ld32.entity.enemy.Pizza;
import dcode.games.uEngine2.games.ld32.entity.enemy.Potato;
import dcode.games.uEngine2.games.ld32.world.GameWorld;
import dcode.games.uEngine2.games.ld32.world.WorldEntity;

import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * Created by dusakus on 19.04.15.
 */
public class World3 extends PWorld {
	@Override
	public String getTexID() {
		return "WORLD";
	}

	@Override
	public void loadYerself(GameWorld target) {

		StData.generalBGT.LPTasks.add(new LoadBasicTexture("world/lvl-003.png", "WORLD"));
		StData.generalBGT.LPTasks.add(new LoadBasicTexture("world/lvl-003_DATA.png", "WORLDD"));

		target.entities.add(new WorldEntity(new PotatoDATA(342, 277), new Potato(), 15, 30));
		target.entities.add(new WorldEntity(new PotatoDATA(392, 277), new Potato(), 15, 30));
		target.entities.add(new WorldEntity(new PotatoDATA(442, 277), new Potato(), 15, 30));

		target.entities.add(new WorldEntity(new PotatoDATA(871, 277), new Potato(), 15, 30));
		target.entities.add(new WorldEntity(new PotatoDATA(911, 277), new Potato(), 15, 30));
		target.entities.add(new WorldEntity(new PotatoDATA(951, 277), new Grapes(), 15, 26));
		target.entities.add(new WorldEntity(new PotatoDATA(991, 277), new Pizza(), 15, 34));
		target.entities.add(new WorldEntity(new PotatoDATA(1051, 277), new Potato(), 15, 30));
		target.entities.add(new WorldEntity(new PotatoDATA(1091, 277), new Grapes(), 15, 26));

		target.entities.add(new WorldEntity(new OnionDATA(1454, 173), new Onion(), 15, 30));
		target.entities.add(new WorldEntity(new OnionDATA(1535, 124), new Onion(), 15, 30));
		target.entities.add(new WorldEntity(new OnionDATA(1616, 73), new Onion(), 15, 30));

		target.entities.add(new WorldEntity(new PotatoDATA(213, 223), new Potato(), 15, 30));
		target.entities.add(new WorldEntity(new PotatoDATA(273, 223), new Pizza(), 15, 34));


		target.entities.add(new WorldEntity(new PotatoDATA(576, 237), new Grapes(), 15, 26));
		target.entities.add(new WorldEntity(new PotatoDATA(636, 237), new Grapes(), 15, 26));

		target.entities.add(new WorldEntity(new PotatoDATA(1869, 245), new Pizza(), 15, 34));

		target.entities.add(new WorldEntity(new PotatoDATA(2104, 277), new Potato(), 15, 30));
		target.entities.add(new WorldEntity(new PotatoDATA(2154, 277), new Potato(), 15, 30));
		target.entities.add(new WorldEntity(new PotatoDATA(2204, 277), new Pizza(), 15, 34));
		target.entities.add(new WorldEntity(new PotatoDATA(2254, 277), new Potato(), 15, 30));

		target.wp.inRoomY = 275;
		target.wp.inRoomX = 30;

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
