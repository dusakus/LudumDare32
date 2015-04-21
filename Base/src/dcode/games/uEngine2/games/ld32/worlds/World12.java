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
 * Created by dusakus on 20.04.15.
 */
public class World12 extends PWorld {

	@Override
	public String getTexID() {
		return "WORLD";
	}

	@Override
	public void loadYerself(GameWorld target) {
		StData.generalBGT.LPTasks.add(new LoadBasicTexture("world/lvl-012.png", "WORLD"));
		StData.generalBGT.LPTasks.add(new LoadBasicTexture("world/lvl-012_DATA.png", "WORLDD"));

		target.entities.add(new WorldEntity(new PotatoDATA(295, 252), new Grapes(), 15, 26));
		target.entities.add(new WorldEntity(new PotatoDATA(765, 252), new Potato(), 15, 30));
		target.entities.add(new WorldEntity(new PotatoDATA(235, 220), new Potato(), 15, 30));
		target.entities.add(new WorldEntity(new PotatoDATA(235, 350), new Potato(), 15, 30));
		target.entities.add(new WorldEntity(new PotatoDATA(335, 540), new Potato(), 15, 30));
		target.entities.add(new WorldEntity(new PotatoDATA(272, 540), new Potato(), 15, 30));
		target.entities.add(new WorldEntity(new PotatoDATA(207, 540), new Potato(), 15, 30));
		target.entities.add(new WorldEntity(new PotatoDATA(143, 540), new Pizza(), 15, 34));
		target.entities.add(new WorldEntity(new PotatoDATA(107, 700), new Potato(), 15, 30));
		target.entities.add(new WorldEntity(new OnionDATA(400, 700), new Onion(), 15, 30));

		target.wp.inRoomY = 95;
		target.wp.inRoomX = 14;

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
			case 2:
				LStData.GL.getPlayer().hazPhysics = false;
				if (InHandler.instance.isKeyPressed(KeyEvent.VK_UP)) LStData.GL.getPlayer().inRoomY -= 2;
				if (InHandler.instance.isKeyPressed(KeyEvent.VK_DOWN)) LStData.GL.getPlayer().inRoomY += 2;
				break;
			case 3:
				LStData.GL.getPlayer().hazPhysics = true;
				break;
			case 1:
				LStData.GL.getPlayer().getDamaged(10);
				break;
			case 8:
				LStData.healthStat = 0;
				LStData.GL.getPlayer().getDamaged(9999999);

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

