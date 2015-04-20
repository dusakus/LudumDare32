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
public class Grapes implements IEntityLogic {

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
		StData.currentGC.currentLT.registerBasic(new OnionAttack(this, WE.getIWX(), WE.getIWY()));
		this.unload();
		WE.setY(1000);
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
		private Grapes target;
		private int lastX = -1, lastY = -1;


		public onionAICore(Grapes potato) {
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
					//target.jump();
					ticksToWait = 20;
				}
				if (playerSpotted) {
					checkPlayerClose();
					if (playerClose) {
						target.tryAttack();
						imAttacking = true;
						ticksToWait = 1000;
					} else {
						if (numbarTools.checkBetween(
								StData.currentGC.currentSC.sprites[2].getX(),
								target.WE.getX() - 16,
								target.WE.getX() + 16)) {
							//target.jump();
							ticksToWait = 12;
						} else {
							checkStuck();
							goToPlayer();
							ticksToWait = 4;
						}
					}
				} else {
					checkStuck();
					ticksToWait = pickRandomAction();
					checkPlayerSpotted();
				}
			}
		}

		private void checkPlayerSpotted() {
			if (numbarTools.checkBetween(StData.currentGC.currentSC.sprites[2].getX(), target.WE.getX() - 150, target.WE.getX() + 150)) {
				playerSpotted = true;
			}
		}

		private int pickRandomAction() {
			int i = StData.gRand.nextInt() % 20;
			if (i <= 16) {
				if (i % 2 == 0) target.WE.tryMoving(-1, 1, i * 3);
				else target.WE.tryMoving(1, 1, i * 3);
				return 60;
			} else {
				return 8;
			}
		}

		private void goToPlayer() {
			if (StData.currentGC.currentSC.sprites[2].getX() > target.WE.getX()) target.WE.tryMoving(1, 1, 4);
			else if (StData.currentGC.currentSC.sprites[2].getX() < target.WE.getX()) target.WE.tryMoving(-1, 1, 4);
		}

		private void checkPlayerClose() {
			this.playerClose = numbarTools.checkBetween(
					StData.currentGC.currentSC.sprites[2].getX(),
					target.WE.getX() - 20,
					target.WE.getX() + 20)
					&&
					numbarTools.checkBetween(
							StData.currentGC.currentSC.sprites[2].getY(),
							target.WE.getY() - 20,
							target.WE.getY() + 20);

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

		private Grapes target;

		private int animDelay;
		private int animFrame;
		private int facingMod;

		private int lastX = -1, lastY = -1;

		public onionAnimator(Grapes potato) {
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
				if (lastX != target.WE.getX()) {
					if (lastX < target.WE.getX()) facingMod = 10;
					else if (lastX > target.WE.getX()) facingMod = 0;

					target.WE.texKey = "EnGRAR";
					//StData.LOG.println("Anim: set to EnPOTR");
					if (animFrame > 4) animFrame = 1;
				} else {
					//StData.LOG.println("Anim: set to EnPOTS");
					target.WE.texKey = "EnGRAR";
					if (animFrame > 4) animFrame = 1;
				}
				if (target.pai.imAttacking) {
					target.WE.texKey = "EnGRAE";
					//StData.LOG.println("Anim: set to EnPOTA");
					if (animFrame > 5) animFrame = 1;
					target.pai.imAttacking = false;
				}
				animDelay = 4;
				lastX = target.WE.getX();
				lastY = target.WE.getY();

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
		int ticksLeft = 60;
		private Grapes onion;
		private int inRoomX;
		private int inRoomY;
		private int step = 1;
		private int dirMod = 0;


		public OnionAttack(Grapes onion, int inRoomX, int inRoomY) {
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
				case 60:
					step = 1;
					onion.WE.unregister();
					break;
				case 55:
					step++;
					break;
				case 50:
					step++;
					break;
				case 45:
					step++;
					break;
				case 40:
					step++;
					break;
				case 35:
					step++;
					break;
				case 30:
					step++;
					break;
				case 25:
					step = 1;
					break;
				case 1:
					step++;
					StData.currentGC.currentSC.sprites[118] = null;
					step = 1;
					break;
			}

			if (ticksLeft < 25 && ticksLeft > 9) {
				step++;
			}
			if (ticksLeft < 25 && ticksLeft > 9) {
				StData.currentGC.currentLT.registerBasic(new Task_DelayedDamage(150, 1, true, inRoomX - 30, inRoomY - 30, 64, 64));
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
			if (ticksLeft > 25) {
				G.drawImage(StData.resources.grf.getTexture("EnGRAE" + (step + dirMod)), inRoomX - LStData.renderOffsetX - 15, inRoomY - LStData.renderOffsetY - 28, null);
			} else {
				G.drawImage(StData.resources.grf.getTexture("EnGRAB" + (step + dirMod)), inRoomX - LStData.renderOffsetX - 31, inRoomY - LStData.renderOffsetY - 60, null);
			}
		}
	}
}