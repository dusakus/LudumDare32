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
public class World15 extends PWorld {


	@Override
	public String getTexID() {
		return "WORLD";
	}

	@Override
	public void loadYerself(GameWorld target) {
		StData.generalBGT.LPTasks.add(new LoadBasicTexture("world/lvl-015.png", "WORLD"));
		StData.generalBGT.LPTasks.add(new LoadBasicTexture("world/lvl-015_DATA.png", "WORLDD"));


		target.entities.add(new WorldEntity(new OnionDATA(271, 961), new Onion(), 15, 30));
		target.entities.add(new WorldEntity(new OnionDATA(367, 737), new Onion(), 15, 30));
		target.entities.add(new WorldEntity(new OnionDATA(575, 929), new Onion(), 15, 30));
		target.entities.add(new WorldEntity(new OnionDATA(975, 481), new Onion(), 15, 30));
		target.entities.add(new WorldEntity(new OnionDATA(814, 833), new Onion(), 15, 30));
		target.entities.add(new WorldEntity(new OnionDATA(592, 609), new Onion(), 15, 30));

		target.entities.add(new WorldEntity(new PotatoDATA(235, 1117), new Potato(), 15, 30));
		target.entities.add(new WorldEntity(new PotatoDATA(205, 1021), new Potato(), 15, 30));
		target.entities.add(new WorldEntity(new PotatoDATA(325, 957), new Potato(), 15, 30));
		target.entities.add(new WorldEntity(new PotatoDATA(552, 925), new Grapes(), 15, 26));
		target.entities.add(new WorldEntity(new PotatoDATA(590, 925), new Potato(), 15, 30));

		target.entities.add(new WorldEntity(new OnionDATA(779, 573), new Onion(), 15, 30));
		target.entities.add(new WorldEntity(new OnionDATA(396, 573), new Onion(), 15, 30));
		target.entities.add(new WorldEntity(new OnionDATA(621, 573), new Onion(), 15, 30));
		target.entities.add(new WorldEntity(new OnionDATA(618, 701), new Onion(), 15, 30));

		target.entities.add(new WorldEntity(new PotatoDATA(521, 157), new Potato(), 15, 30));
		target.entities.add(new WorldEntity(new PotatoDATA(648, 157), new Potato(), 15, 30));

		target.entities.add(new WorldEntity(new PotatoDATA(841, 221), new Pizza(), 15, 34));
		target.entities.add(new WorldEntity(new PotatoDATA(882, 253), new Pizza(), 15, 34));
		target.entities.add(new WorldEntity(new PotatoDATA(914, 285), new Pizza(), 15, 34));
		target.entities.add(new WorldEntity(new PotatoDATA(275, 445), new Pizza(), 15, 34));
		target.entities.add(new WorldEntity(new PotatoDATA(306, 477), new Pizza(), 15, 34));
		target.entities.add(new WorldEntity(new PotatoDATA(328, 509), new Pizza(), 15, 34));

		target.entities.add(new WorldEntity(new PotatoDATA(876, 701), new Potato(), 15, 30));
		target.entities.add(new WorldEntity(new PotatoDATA(661, 797), new Potato(), 15, 30));
		target.entities.add(new WorldEntity(new PotatoDATA(776, 669), new Potato(), 15, 30));
		target.entities.add(new WorldEntity(new PotatoDATA(709, 669), new Potato(), 15, 30));
		target.entities.add(new WorldEntity(new PotatoDATA(947, 509), new Potato(), 15, 30));
		target.entities.add(new WorldEntity(new PotatoDATA(264, 765), new Potato(), 15, 30));

		LStData.bgm.stop();
		LStData.boss.play(true);

		target.wp.inRoomY = 1150;
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
				LStData.currentMode = LStData.MODE_MENU_MAIN;
				LStData.currentStatus = 1401;
				break;
			case 1:
				LStData.currentStatus = 209;
				LStData.GL.currentLevel++;
				break;
			case 2:
				LStData.GL.getPlayer().hazPhysics = false;
				if (InHandler.instance.isKeyPressed(KeyEvent.VK_UP)) LStData.GL.getPlayer().inRoomY -= 2;
				if (InHandler.instance.isKeyPressed(KeyEvent.VK_DOWN)) LStData.GL.getPlayer().inRoomY += 2;
				break;
			case 3:
				LStData.GL.getPlayer().hazPhysics = true;
			case 10:
				LStData.GL.getPlayer().getDamaged(10);
				break;
			case 8:
				LStData.healthStat = 0;
				LStData.GL.getPlayer().getDamaged(9999999);

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

}
