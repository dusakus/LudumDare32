package dcode.games.uEngine2.games.ld32.entity.enemy;

import dcode.games.uEngine2.GFX.sprites.Sprite;
import dcode.games.uEngine2.LOGIC.ILogicTask;
import dcode.games.uEngine2.StData;
import dcode.games.uEngine2.games.ld32.LStData;
import dcode.games.uEngine2.games.ld32.entity.IEntityLogic;
import dcode.games.uEngine2.games.ld32.parts.Task_DelayedDamage;
import dcode.games.uEngine2.games.ld32.world.WorldEntity;
import dcode.games.uEngine2.tools.numbarTools;

import java.awt.*;

/**
 * Created by dusakus on 20.04.15.
 */
public class Onion implements IEntityLogic {

	protected onionAnimator pa;
	protected onionAICore pai;
	private WorldEntity WE;
	private boolean active = true;

	@Override
	public void initializeLogic() {
		pa = new onionAnimator(this);
		pai = new onionAICore(this);
		StData.currentGC.currentLT.registerBasic(pa);
		StData.currentGC.currentLT.registerBasic(pai);
	}

	@Override
	public boolean shouldCheck() {
		return true;
	}

	@Override
	public void update(WorldEntity we) {
		WE = we;
	}

	@Override
	public void unload() {
		active = false;
	}

	private void tryAttack() {
		StData.currentGC.currentLT.registerBasic(new OnionAttack(this, LStData.GL.getPlayer().inRoomX, LStData.GL.getPlayer().inRoomY));
	}

	private void jump() {
		WE.jump(-1, 1, 60);
		WE.jump(-1, 1, 40);
		WE.jump(-1, 1, 20);
		WE.jump(-1, 1, 15);
	}

	private class onionAICore implements ILogicTask {

		public boolean playerSpotted = false;
		public boolean playerClose = false;
		public boolean imMoving = false;
		public boolean imStuck = false;
		public boolean imAttacking = false;
		private int ticksToWait = 120;
		private Onion target;
		private int lastX = -1, lastY = -1;


		public onionAICore(Onion potato) {
			target = potato;
		}

		@Override
		public boolean isReady() {
			return target.WE != null;
		}

		@Override
		public void perform() {
			if (ticksToWait > 0) {
				ticksToWait--;
			} else {
				if (imStuck) {
					ticksToWait = 20;
				}
				if (playerSpotted) {
					checkPlayerClose();
					if (playerClose) {
						target.tryAttack();
						imAttacking = true;
						ticksToWait = 300;
						checkStuck();
					}
				} else
					checkPlayerSpotted();
			}
		}

		private void checkPlayerSpotted() {
			if (numbarTools.checkBetween(StData.currentGC.currentSC.sprites[2].getX(), target.WE.getX() - 80, target.WE.getX() + 80)) {
				playerSpotted = true;
			}
		}

		private void checkPlayerClose() {
			this.playerClose = numbarTools.checkBetween(
					StData.currentGC.currentSC.sprites[2].getX(),
					target.WE.getX() - 70,
					target.WE.getX() + 70)
					&&
					numbarTools.checkBetween(
							StData.currentGC.currentSC.sprites[2].getY(),
							target.WE.getY() - 4,
							target.WE.getY() + 4);

		}

		private void checkStuck() {
			if (target.WE.getX() == lastX && target.WE.getY() == lastY) {
				imStuck = true;
			} else {
				lastX = target.WE.getX();
				lastY = target.WE.getY();
				imStuck = false;
			}
		}

		@Override
		public boolean doRepeat() {
			return target.active;
		}
	}

	private class onionAnimator implements ILogicTask {

		private Onion target;

		private int animDelay;
		private int animFrame;
		private int facingMod;

		private int lastX = -1, lastY = -1;

		public onionAnimator(Onion potato) {
			target = potato;
		}

		@Override
		public boolean isReady() {
			return true;
		}

		@Override
		public void perform() {
			if (animDelay <= 0) {
				animFrame++;
				if (lastX != target.WE.getIWX()) {
					if (lastX < target.WE.getIWX()) facingMod = 10;
					else if (lastX > target.WE.getIWX()) facingMod = 0;

					target.WE.texKey = "EnONIS";
					//StData.LOG.println("Anim: set to EnPOTR");
					if (animFrame > 2) animFrame = 1;
				} else {
					//StData.LOG.println("Anim: set to EnPOTS");
					target.WE.texKey = "EnONIS";
					if (animFrame > 2) animFrame = 1;
				}
				if (target.pai.imAttacking) {
					target.WE.texKey = "EnONIA";
					//StData.LOG.println("Anim: set to EnPOTA");
					//if (animFrame > 5) animFrame = 1;
					//target.pai.imAttacking = false;
				}
				animDelay = 4;
				lastX = target.WE.getIWX();
				lastY = target.WE.getIWY();

			} else {
				animDelay--;
			}
			if (target.pai.playerSpotted) {
				if (StData.currentGC.currentSC.sprites[2].getX() > target.WE.getX()) facingMod = 10;
				else facingMod = 0;
			}
			target.WE.textureVariation = animFrame + facingMod;
		}

		@Override
		public boolean doRepeat() {
			return target.active;
		}
	}

	private class OnionAttack extends Sprite implements ILogicTask {
		int ticksLeft = 100;
		private Onion onion;
		private int inRoomX;
		private int inRoomY;
		private int step = 1;


		public OnionAttack(Onion onion, int inRoomX, int inRoomY) {
			this.onion = onion;
			this.inRoomX = inRoomX;
			this.inRoomY = inRoomY;
			StData.currentGC.currentSC.sprites[118] = this;
			StData.currentGC.currentSC.sprites_front[118] = 118;
		}

		@Override
		public boolean isReady() {
			return true;
		}

		@Override
		public void perform() {
			ticksLeft--;
			onion.WE.textureVariation = step;
			switch (ticksLeft) {
				case 99:
					step = 1;
					break;
				case 95:
					step++;
					break;
				case 91:
					step++;
					break;
				case 88:
					step++;
					break;
				case 85:
					step++;
					break;
				case 82:
					step++;
					break;
				case 79:
					step++;
					break;
				case 75:
					step++;
					break;
				case 71:
					step++;
					break;
				case 68:
					step++;
					break;
				case 65:
					step++;
					break;
				case 62:
					step++;
					break;
				case 59:
					step++;
					LStData.SND_rlaser.play();
					break;
				case 21:
					step = 16;
					break;
				case 18:
					step++;
					break;
				case 15:
					step++;
					break;
				case 12:
					step++;
					break;
				case 9:
					step++;
					StData.currentGC.currentSC.sprites[118] = null;
					step = 1;
					onion.pai.imAttacking = false;

					break;
			}
			if (ticksLeft < 46 && ticksLeft > 9) {
				StData.currentGC.currentLT.registerBasic(new Task_DelayedDamage(25, 1, true, inRoomX - 6, inRoomY - 199, 12, 200));
			}
		}

		@Override
		public boolean doRepeat() {
			return ticksLeft > 0;
		}

		@Override
		public Image getCustomTexture() {
			return null;
		}

		@Override
		public boolean doCustomRender() {
			return true;
		}

		@Override
		public void customRender(Graphics2D G) {
			if (ticksLeft < 58 && ticksLeft > 12)
				G.drawImage(StData.resources.grf.getScaledTexture("EnONIR", 16, 200), onion.WE.getX() + 8, onion.WE.getY() - 195, null);
			if (ticksLeft < 45 && ticksLeft > 8)
				G.drawImage(StData.resources.grf.getScaledTexture("EnONIR", 16, 200), inRoomX - 8 - LStData.renderOffsetX, inRoomY - 199 - LStData.renderOffsetY, null);

		}
	}
}
