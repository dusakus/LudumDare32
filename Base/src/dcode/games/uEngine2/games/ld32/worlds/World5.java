package dcode.games.uEngine2.games.ld32.worlds;

import dcode.games.uEngine2.BGTasks.internalTasks.LoadBasicTexture;
import dcode.games.uEngine2.StData;
import dcode.games.uEngine2.games.ld32.InHandler;
import dcode.games.uEngine2.games.ld32.LStData;
import dcode.games.uEngine2.games.ld32.entity.enemy.boss_ham;
import dcode.games.uEngine2.games.ld32.world.GameWorld;
import dcode.games.uEngine2.games.ld32.world.WorldEntity;

import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * Created by dusakus on 20.04.15.
 */
public class World5 extends PWorld {
	boss_ham bh = new boss_ham();

	@Override
	public String getTexID() {
		return "WORLD";
	}

	@Override
	public void loadYerself(GameWorld target) {
		StData.generalBGT.LPTasks.add(new LoadBasicTexture("world/lvl-005.png", "WORLD"));
		StData.generalBGT.LPTasks.add(new LoadBasicTexture("world/lvl-005_DATA.png", "WORLDD"));


		target.entities.add(new WorldEntity(bh, bh, 30, 60));

		LStData.bgm.stop();
		LStData.boss.play(true);

		target.wp.inRoomY = 398;
		target.wp.inRoomX = 20;

	}

	@Override
	public boolean checkUnlocked(int RColor) {
		return false;
	}

	@Override
	public void triggerEvent(Color data) {
		switch (data.getBlue() - 100) {
			case 0:
				if (!bh.active) LStData.currentStatus = 209;
				break;
			case 2:
				LStData.GL.getPlayer().hazPhysics = false;
				if (InHandler.instance.isKeyPressed(KeyEvent.VK_UP)) LStData.GL.getPlayer().inRoomY -= 2;
				if (InHandler.instance.isKeyPressed(KeyEvent.VK_DOWN)) LStData.GL.getPlayer().inRoomY += 2;
				break;
			case 1:
				LStData.GL.getPlayer().hazPhysics = true;
		}

	}
}