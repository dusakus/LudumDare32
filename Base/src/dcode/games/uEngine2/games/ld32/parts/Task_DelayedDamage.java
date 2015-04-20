package dcode.games.uEngine2.games.ld32.parts;

import dcode.games.uEngine2.LOGIC.ILogicTask;
import dcode.games.uEngine2.games.ld32.LStData;

import java.awt.*;

/**
 * Created by dusakus on 20.04.15.
 */
public class Task_DelayedDamage implements ILogicTask {

	private int ticksLeft;
	private Rectangle area;
	private boolean targetSwitch;
	private int damage;

	public Task_DelayedDamage(int damage, int delayBy, boolean atPlayer, int startX, int startY, int boxW, int boxH) {
		ticksLeft = delayBy;
		targetSwitch = atPlayer;
		area = new Rectangle(startX, startY, boxW, boxH);
		this.damage = damage;
	}

	@Override
	public boolean isReady() {
		return true;
	}

	@Override
	public void perform() {
		if (ticksLeft == 0) {
			if (targetSwitch) {
				if (area.contains(LStData.GL.getPlayer().inRoomX, LStData.GL.getPlayer().inRoomY)) {
					LStData.GL.getPlayer().getDamaged(damage);
				}
			} else {
				LStData.GL.damageArea(area, damage);
			}
		}
		ticksLeft--;
	}

	@Override
	public boolean doRepeat() {
		return ticksLeft >= -1;
	}
}
