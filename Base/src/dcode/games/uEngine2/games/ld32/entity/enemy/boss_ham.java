package dcode.games.uEngine2.games.ld32.entity.enemy;

import dcode.games.uEngine2.LOGIC.ILogicTask;
import dcode.games.uEngine2.StData;
import dcode.games.uEngine2.games.ld32.entity.IEntityData;
import dcode.games.uEngine2.games.ld32.entity.IEntityLogic;
import dcode.games.uEngine2.games.ld32.world.WorldEntity;
import dcode.games.uEngine2.tools.numbarTools;

import java.awt.*;

/**
 * Created by dusakus on 20.04.15.
 */
public class boss_ham implements IEntityData, IEntityLogic {
	boolean active = true;
	private WorldEntity WE;

	@Override
	public int getType() {
		return 20;
	}

	@Override
	public Point getInitialLocation() {
		return new Point(500, 100);
	}

	@Override
	public String getTextureId() {
		return "ERR";
	}

	@Override
	public int getDepth() {
		return 0;
	}

	@Override
	public int getHealth() {
		return 0;
	}

	@Override
	public void initializeLogic() {

	}

	@Override
	public boolean shouldCheck() {
		return WE == null;
	}

	@Override
	public void update(WorldEntity we) {
		WE = we;
	}

	@Override
	public void unload() {
		active = false;
	}

	private class PotatoAIcore implements ILogicTask {

		public boolean playerSpotted = false;
		public boolean playerClose = false;
		public boolean imStuck = false;
		public boolean imAttacking = false;
		boolean imJumping = false;
		private int ticksToWait = 120;
		private boss_ham target;
		private int lastX = -1, lastY = -1;


		public PotatoAIcore(boss_ham potato) {
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
					target.jump();
					ticksToWait = 20;
				}
				if (playerSpotted) {
					checkPlayerClose();
					if (playerClose) {
						target.tryAttack();
						imAttacking = true;
						ticksToWait = 30;
					} else {
						if (numbarTools.checkBetween(
								StData.currentGC.currentSC.sprites[2].getX(),
								target.WE.getX() - 16,
								target.WE.getX() + 16)) {
							target.jump();
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
			if (numbarTools.checkBetween(StData.currentGC.currentSC.sprites[2].getX(), target.WE.getX() - 300, target.WE.getX() + 300)) {
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
			if (imJumping) {
				if (StData.currentGC.currentSC.sprites[2].getX() > target.WE.getX()) target.WE.tryMoving(1, 2, 2);
				else if (StData.currentGC.currentSC.sprites[2].getX() < target.WE.getX()) target.WE.tryMoving(-1, 2, 2);
			}
		}

		private void checkPlayerClose() {
			this.playerClose = numbarTools.checkBetween(
					StData.currentGC.currentSC.sprites[2].getX(),
					target.WE.getX() - 16,
					target.WE.getX() + 16)
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

	private class PotatoAnimator implements ILogicTask {

		private Potato target;

		private int animDelay;
		private int animFrame;
		private int facingMod;

		private int lastX = -1, lastY = -1;

		public PotatoAnimator(Potato potato) {
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

					target.WE.texKey = "EnPOTR";
					//StData.LOG.println("Anim: set to EnPOTR");
					if (animFrame > 6) animFrame = 1;
				} else {
					//StData.LOG.println("Anim: set to EnPOTS");
					target.WE.texKey = "EnPOTS";
					if (animFrame > 4) animFrame = 1;
				}
				if (target.pai.imAttacking) {
					target.WE.texKey = "EnPOTA";
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

}
