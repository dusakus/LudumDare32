package dcode.games.uEngine2.games.ld32.worlds;

import dcode.games.uEngine2.BGTasks.internalTasks.LoadBasicTexture;
import dcode.games.uEngine2.StData;
import dcode.games.uEngine2.games.ld32.LStData;
import dcode.games.uEngine2.games.ld32.entity.IEntityData;
import dcode.games.uEngine2.games.ld32.entity.enemy.Grapes;
import dcode.games.uEngine2.games.ld32.entity.enemy.Pizza;
import dcode.games.uEngine2.games.ld32.entity.enemy.Potato;
import dcode.games.uEngine2.games.ld32.world.GameWorld;
import dcode.games.uEngine2.games.ld32.world.WorldEntity;

import java.awt.*;

/**
 * Created by dusakus on 20.04.15.
 */
public class World14 extends PWorld {

	@Override
	public String getTexID() {
		return "WORLD";
	}

	@Override
	public void loadYerself(GameWorld target) {
		StData.generalBGT.LPTasks.add(new LoadBasicTexture("world/lvl-014.png", "WORLD"));
		StData.generalBGT.LPTasks.add(new LoadBasicTexture("world/lvl-014_DATA.png", "WORLDD"));

		target.entities.add(new WorldEntity(new PotatoDATA(100, 350), new Potato(), 15, 30));
		target.entities.add(new WorldEntity(new PotatoDATA(130, 350), new Pizza(), 15, 34));
		target.entities.add(new WorldEntity(new PotatoDATA(160, 350), new Grapes(), 15, 26));
		target.entities.add(new WorldEntity(new PotatoDATA(190, 350), new Potato(), 15, 30));
		target.entities.add(new WorldEntity(new PotatoDATA(220, 350), new Potato(), 15, 30));
		target.entities.add(new WorldEntity(new PotatoDATA(250, 350), new Pizza(), 15, 34));
		target.entities.add(new WorldEntity(new PotatoDATA(280, 350), new Grapes(), 15, 26));
		target.entities.add(new WorldEntity(new PotatoDATA(310, 350), new Potato(), 15, 30));
		target.entities.add(new WorldEntity(new PotatoDATA(340, 350), new Potato(), 15, 30));
		target.entities.add(new WorldEntity(new PotatoDATA(370, 350), new Pizza(), 15, 34));
		target.entities.add(new WorldEntity(new PotatoDATA(400, 350), new Grapes(), 15, 26));
		target.entities.add(new WorldEntity(new PotatoDATA(430, 350), new Potato(), 15, 30));
		target.entities.add(new WorldEntity(new PotatoDATA(430, 222), new Potato(), 15, 30));
		target.entities.add(new WorldEntity(new PotatoDATA(400, 222), new Pizza(), 15, 34));
		target.entities.add(new WorldEntity(new PotatoDATA(370, 222), new Grapes(), 15, 26));
		target.entities.add(new WorldEntity(new PotatoDATA(340, 222), new Potato(), 15, 30));
		target.entities.add(new WorldEntity(new PotatoDATA(310, 222), new Potato(), 15, 30));
		target.entities.add(new WorldEntity(new PotatoDATA(280, 222), new Pizza(), 15, 34));
		target.entities.add(new WorldEntity(new PotatoDATA(250, 222), new Grapes(), 15, 26));
		target.entities.add(new WorldEntity(new PotatoDATA(220, 222), new Potato(), 15, 30));
		target.entities.add(new WorldEntity(new PotatoDATA(190, 222), new Potato(), 15, 30));
		target.entities.add(new WorldEntity(new PotatoDATA(160, 222), new Pizza(), 15, 34));
		target.entities.add(new WorldEntity(new PotatoDATA(130, 222), new Grapes(), 15, 26));
		target.entities.add(new WorldEntity(new PotatoDATA(100, 222), new Potato(), 15, 30));
		target.entities.add(new WorldEntity(new PotatoDATA(100, 94), new Potato(), 15, 30));
		target.entities.add(new WorldEntity(new PotatoDATA(130, 94), new Pizza(), 15, 34));
		target.entities.add(new WorldEntity(new PotatoDATA(160, 94), new Grapes(), 15, 26));
		target.entities.add(new WorldEntity(new PotatoDATA(190, 94), new Potato(), 15, 30));
		target.entities.add(new WorldEntity(new PotatoDATA(220, 94), new Potato(), 15, 30));
		target.entities.add(new WorldEntity(new PotatoDATA(250, 94), new Pizza(), 15, 34));
		target.entities.add(new WorldEntity(new PotatoDATA(280, 94), new Grapes(), 15, 26));
		target.entities.add(new WorldEntity(new PotatoDATA(310, 94), new Potato(), 15, 30));
		target.entities.add(new WorldEntity(new PotatoDATA(340, 94), new Potato(), 15, 30));
		target.entities.add(new WorldEntity(new PotatoDATA(370, 94), new Pizza(), 15, 34));
		target.entities.add(new WorldEntity(new PotatoDATA(400, 94), new Grapes(), 15, 26));
		target.entities.add(new WorldEntity(new PotatoDATA(430, 94), new Potato(), 15, 30));

		target.wp.inRoomY = 350;
		target.wp.inRoomX = 12;

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
				LStData.currentStatus = 209;
				LStData.GL.currentLevel++;
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

}