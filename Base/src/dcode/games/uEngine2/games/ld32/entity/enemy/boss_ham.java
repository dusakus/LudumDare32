package dcode.games.uEngine2.games.ld32.entity.enemy;

import dcode.games.uEngine2.GFX.sprites.Sprite;
import dcode.games.uEngine2.LOGIC.ILogicTask;
import dcode.games.uEngine2.StData;
import dcode.games.uEngine2.games.ld32.LStData;
import dcode.games.uEngine2.games.ld32.entity.IEntityData;
import dcode.games.uEngine2.games.ld32.entity.IEntityLogic;
import dcode.games.uEngine2.games.ld32.parts.Task_DelayedDamage;
import dcode.games.uEngine2.games.ld32.world.WorldEntity;
import dcode.games.uEngine2.tools.numbarTools;

import java.awt.*;
import java.awt.geom.Line2D;

/**
 * Created by dusakus on 20.04.15.
 */
public class boss_ham implements IEntityData, IEntityLogic {
	public boolean active = true;
	private WorldEntity WE;

	private PotatoAIcore pai;
	private jumpingHamburgAnimator pa;

	@Override
	public int getType() {
		return 20;
	}

	@Override
	public Point getInitialLocation() {
		return new Point(705, 50);
	}

	@Override
	public String getTextureId() {
		return "EnHAMS";
	}

	@Override
	public int getDepth() {
		return 5;
	}

	@Override
	public int getHealth() {
		return 2500;
	}

	@Override
	public void initializeLogic() {
		pa = new jumpingHamburgAnimator(this);
		pai = new PotatoAIcore(this);
		StData.currentGC.currentLT.registerBasic(pa);
		StData.currentGC.currentLT.registerBasic(pai);
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


	private void tryAttack() {
		WE.texKey = "EnHAMA";
		StData.currentGC.currentLT.registerBasic(new shootingHamburgAnimator(this, new Point(LStData.GL.getPlayer().inRoomX, LStData.GL.getPlayer().inRoomY)));
	}


	private void jump() {
		WE.texKey = "EnHAMS";
		pai.imJumping = true;
		StData.currentGC.currentLT.registerBasic(new jumpingHamburgAnimator(this));
		WE.jump(-1, 1, 60);
		WE.jump(-1, 1, 40);
		WE.jump(-1, 1, 40);
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
						ticksToWait = 180;
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
							ticksToWait = 2;
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
					target.WE.getX() - 30,
					target.WE.getX() + 30)
					&&
					numbarTools.checkBetween(
							StData.currentGC.currentSC.sprites[2].getY(),
							target.WE.getY() - 30,
							target.WE.getY() + 30);

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

	private class jumpingHamburgAnimator implements ILogicTask {

		private boss_ham target;
		private int animFrame = 1;
		private int facingMod;
		private int lastX = -1, lastY = -1;
		private int ticksleft = 70;

		public jumpingHamburgAnimator(boss_ham potato) {
			target = potato;
		}

		@Override
		public boolean isReady() {
			return true;
		}

		@Override
		public void perform() {
			if (ticksleft == 60) animFrame = 1;
			if (ticksleft == 58) animFrame = 2;
			if (ticksleft == 54) animFrame = 3;
			if (ticksleft == 50) animFrame = 4;
			if (ticksleft == 46) animFrame = 5;
			if (ticksleft == 42) animFrame = 6;
			if (ticksleft == 38) animFrame = 7;
			if (ticksleft == 34) animFrame = 8;
			if (ticksleft == 30) animFrame = 9;
			if (ticksleft == 20) animFrame = 10;
			if (ticksleft == 10) animFrame = 11;
			if (ticksleft == 4) animFrame = 12;
			if (ticksleft == 2) animFrame = 13;
			if (ticksleft == 2) target.pai.imJumping = false;
			if (ticksleft == 0) animFrame = 1;


			if (target.pai.playerSpotted) {
				if (StData.currentGC.currentSC.sprites[2].getX() > target.WE.getX()) facingMod = 10;
				else facingMod = 0;
			}
			ticksleft--;
			target.WE.textureVariation = animFrame;
		}

		@Override
		public boolean doRepeat() {
			return ticksleft >= 0;
		}
	}

	private class shootingHamburgAnimator implements ILogicTask {

		lazerSprite ls;

		private boss_ham target;
		private int animFrame = 1;
		private int ticksleft = 30;

		private Point pla;
		private Point src;

		public shootingHamburgAnimator(boss_ham potato, Point PLAYER) {
			target = potato;
			ls = new lazerSprite();
			pla = PLAYER;
			src = new Point(potato.WE.getIWX(), potato.WE.getIWY() - 14);
			StData.currentGC.currentSC.sprites[115] = ls;
			StData.currentGC.currentSC.sprites_front[115] = 115;
		}

		@Override
		public boolean isReady() {
			return true;
		}

		@Override
		public void perform() {
			if (ticksleft > 22) animFrame = numbarTools.mod(ticksleft - 31);
			if (ticksleft == 31) ls.setBeam(src, pla);
			if (ticksleft < 9) animFrame = (ticksleft);
			if (ticksleft == 15)
				LStData.SND_laser.play();
			StData.currentGC.currentLT.registerBasic(new Task_DelayedDamage(49, 2, true, (int) pla.getX() - 40, (int) pla.getY() - 40, 80, 80));
			if (ticksleft == 15)
				StData.currentGC.currentLT.registerBasic(new OnionAttack(target, (int) pla.getX(), (int) pla.getY()));
			if (ticksleft == 0) animFrame = 1;
			if (ticksleft == 0) StData.currentGC.currentSC.sprites[115] = null;

			ticksleft--;
			target.WE.textureVariation = animFrame;
		}

		@Override
		public boolean doRepeat() {
			return ticksleft >= 0;
		}
	}

	private class lazerSprite extends Sprite {

		Point SRC = null, TRG = null;
		Line2D line;

		public void setBeam(Point src, Point pla) {
			SRC = src;
			TRG = pla;
			line = new Line2D.Double(src.getX(), src.getY(), pla.getX(), pla.getY());
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
			if (SRC != null && TRG != null) {
				G.setStroke(new BasicStroke(5));
				G.setColor(new Color(174, 159, 25));
				G.draw(line);
				G.setColor(new Color(244, 238, 181));
				G.drawLine(SRC.x - 2 - LStData.renderOffsetX, SRC.y - LStData.renderOffsetY, TRG.x - 2 - LStData.renderOffsetX, TRG.y - LStData.renderOffsetY);
				G.drawLine(SRC.x + 2 - LStData.renderOffsetX, SRC.y - LStData.renderOffsetY, TRG.x + 2 - LStData.renderOffsetX, TRG.y - LStData.renderOffsetY);
				G.drawLine(SRC.x - LStData.renderOffsetX, SRC.y - 2 - LStData.renderOffsetY, TRG.x - LStData.renderOffsetX, TRG.y - 2 - LStData.renderOffsetY);
				G.drawLine(SRC.x - LStData.renderOffsetX, SRC.y + 2 - LStData.renderOffsetY, TRG.x - LStData.renderOffsetX, TRG.y + 2 - LStData.renderOffsetY);
			}
		}
	}

	private class OnionAttack extends Sprite implements ILogicTask {
		int ticksLeft = 26;
		//private boss_ham onion;
		private int inRoomX;
		private int inRoomY;
		private int step = 1;
		private int dirMod = 0;


		public OnionAttack(boss_ham onion, int inRoomX, int inRoomY) {
			//this.onion = onion;
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
			//onion.WE.textureVariation = step;
			switch (ticksLeft) {
				case 25:
					step = 1;
					LStData.SND_boom.play();
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
