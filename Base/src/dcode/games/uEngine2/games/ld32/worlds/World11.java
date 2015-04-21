package dcode.games.uEngine2.games.ld32.worlds;

import dcode.games.uEngine2.BGTasks.internalTasks.LoadBasicTexture;
import dcode.games.uEngine2.GFX.layers.ClearColorLayer;
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
public class World11 extends PWorld {

	@Override
	public String getTexID() {
		return "WORLD";
	}

	@Override
	public void loadYerself(GameWorld target) {
		StData.generalBGT.LPTasks.add(new LoadBasicTexture("world/level11.png", "WORLD"));
		StData.generalBGT.LPTasks.add(new LoadBasicTexture("world/level11_map.png", "WORLDD"));

		StData.currentGC.currentSC.layers_Background.clear();
		StData.currentGC.currentSC.layers_Background.add(new ClearColorLayer(new Color(87, 88, 99)));

		target.entities.add(new WorldEntity(new PotatoDATA(170, 251), new Potato(), 15, 30));
		target.entities.add(new WorldEntity(new PotatoDATA(260, 220), new Potato(), 15, 30));
		target.entities.add(new WorldEntity(new PotatoDATA(300, 220), new Potato(), 15, 30));
		target.entities.add(new WorldEntity(new PotatoDATA(150, 122), new Pizza(), 15, 34));
		target.entities.add(new WorldEntity(new PotatoDATA(190, 122), new Potato(), 15, 30));
		target.entities.add(new WorldEntity(new PotatoDATA(50, 60), new Grapes(), 15, 26));
		target.entities.add(new WorldEntity(new PotatoDATA(100, 60), new Grapes(), 15, 26));
		target.entities.add(new WorldEntity(new PotatoDATA(75, 60), new Potato(), 15, 30));
		target.entities.add(new WorldEntity(new PotatoDATA(367, 122), new Pizza(), 15, 34));
		target.entities.add(new WorldEntity(new PotatoDATA(526, 155), new Potato(), 15, 30));
		target.entities.add(new WorldEntity(new PotatoDATA(765, 251), new Grapes(), 15, 26));
		target.entities.add(new WorldEntity(new PotatoDATA(795, 251), new Grapes(), 15, 26));
		target.entities.add(new WorldEntity(new PotatoDATA(715, 251), new Grapes(), 15, 26));
		target.entities.add(new WorldEntity(new PotatoDATA(710, 155), new Potato(), 15, 30));
		target.entities.add(new WorldEntity(new PotatoDATA(750, 155), new Potato(), 15, 30));
		target.entities.add(new WorldEntity(new PotatoDATA(790, 155), new Pizza(), 15, 34));
		target.entities.add(new WorldEntity(new OnionDATA(910, 250), new Onion(), 15, 30));

		LStData.boss.stop();
		LStData.bgm.play(true);

		target.wp.inRoomY = 255;
		target.wp.inRoomX = 7;

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
			case 10:
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
