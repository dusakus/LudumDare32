package dcode.games.uEngine2.games.ld32.world;

import dcode.games.uEngine2.BGTasks.internalTasks.LoadBasicTexture;
import dcode.games.uEngine2.GFX.sprites.Sprite;
import dcode.games.uEngine2.LOGIC.ILogicTask;
import dcode.games.uEngine2.StData;
import dcode.games.uEngine2.games.ld32.InHandler;
import dcode.games.uEngine2.games.ld32.LStData;
import dcode.games.uEngine2.games.ld32.parts.Task_DelayedDamage;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

/**
 * Created by dusakus on 18.04.15.
 */
public class WorldPlayer extends Sprite {

    public int inRoomX = 100;
    public int inRoomY = 100;
    public int commandGoLeft = 0;
    public int commandGoRight = 0;
    public int commandGoJump = 0;
    public boolean hazPhysics = false;
    private boolean hadPhysics = false;
    private String itemMod = "N";
    private String animType = "S";
    private int animId = 1;             // 1-4 facing left, 11-14 facing right
    private int animFrame = 1;
    private int lastInRoomX = 100;
    private int lastInRoomY = 100;
    private int animDelay = 3;
    private boolean attackLocked = false;

    public WorldPlayer(int x, int y) {
        inRoomX = x;
        inRoomY = y;

        lastInRoomX = x;
        lastInRoomY = y;

        hazPhysics = true;
    }

    public static void loadTextures(PlayerItem item) {
        String s = "";
        if (item != null && item.isMainItem) s = item.playerModifier;
        for (int i = 1; i < 5; i++) {
            StData.generalBGT.LPTasks.add(new LoadBasicTexture("sprite/player/PLA" + s + "FRAME" + i + ".png", "PLA" + s + i));             //Load textures for both facings
            StData.generalBGT.LPTasks.add(new LoadBasicTexture("sprite/player/PLA" + s + "FRAME" + (i + 10) + ".png", "PLA" + s + (i + 10)));
        }

    }

    @Override
    public int getX() {
        return super.getX() - 15;
    }

    @Override
    public int getY() {
        return super.getY() - 31;
    }

    @Override
    public Image getCustomTexture() {
        return StData.resources.grf.getTexture("Pla" + itemMod + animType + animId);
    }

    @Override
    public boolean doCustomRender() {
        return false;
    }

    @Override
    public void customRender(Graphics2D G) {

    }

    public void goLeft() {
        if (commandGoLeft > 5) return;
        StData.currentGC.currentLT.registerBasic(new playerMotionPerformer(this, -1));
        commandGoLeft++;
    }

    public void goRight() {
        if (commandGoRight > 5) return;
        StData.currentGC.currentLT.registerBasic(new playerMotionPerformer(this, 1));
        commandGoRight++;

    }

    public void goJump() {
        if (commandGoJump > 5) return;
        StData.currentGC.currentLT.registerBasic(new playerJumpLogic(this, -1, 1));
        commandGoJump++;

    }

    public void updateCoords(int x, int y) {
        //TODO: proper movement, with collisions
        if (!hazPhysics) {
            inRoomX += x;
            inRoomY += y;
        }
    }

    public void updatePlayer() {
        if (!attackLocked) {
            if (animDelay == 0) {
                animFrame++;
                if (lastInRoomX != inRoomX || lastInRoomY != inRoomY) {
                    animType = "M";
                    if (animFrame > 2) animFrame = 1;
                    if (lastInRoomX < inRoomX) animId = animFrame;
                    if (lastInRoomX > inRoomX) animId = animFrame + 10;
                    lastInRoomX = inRoomX;
                    lastInRoomY = inRoomY;
                    animDelay = 3;
                    if (!checkCollisionAt(inRoomX, inRoomY + 1, true)) {
                        animType = "J";
                        if (animId < 9) animId = 1;
                        else animId = 11;
                    }
                } else {
                    animType = "S";
                    if (animFrame > 3) animFrame = 1;
                    if (animId < 9) animId = animFrame;
                    else animId = animFrame + 10;
                    animDelay = 12;
                }
            } else {
                animDelay--;
            }
        }

        if (hazPhysics && !hadPhysics) {
            StData.currentGC.currentLT.registerBasic(new playerPhysicsLogic(this, 1, 2));
            hadPhysics = true;
        }
        if (!hazPhysics) {
            hadPhysics = false;
        }

        this.x = inRoomX - LStData.renderOffsetX;
        this.y = inRoomY - LStData.renderOffsetY;
    }

    public void tryAttacking() {
        if (attackLocked) return;
        if (itemMod.equals("C")) {
            if (LStData.ammoStat > 0) {
                LStData.ammoStat--;
                StData.currentGC.currentLT.registerBasic(new attackCarrot(this));
                attackLocked = true;
            }
        } else if (itemMod.equals("M")) {
            StData.currentGC.currentLT.registerBasic(new attackMeat(this));
            attackLocked = true;
        }
    }

    private boolean checkCollisionAt(int x, int y, boolean juf) {
        return LStData.GL.getColisionAt(x, y, juf);
    }

    private void tryMoveX(int dir) {
        //TODO: Check collision first
        if (!checkCollisionAt(inRoomX + dir, inRoomY, true)) {
            this.inRoomX += dir;
            LStData.GL.updateTriggers(inRoomX + dir, inRoomY);
        } else if (!checkCollisionAt(inRoomX + dir, inRoomY - 1, true)) {
            this.inRoomX += dir;
            this.inRoomY--;
            LStData.GL.updateTriggers(inRoomX + dir, inRoomY);
        } else if (!checkCollisionAt(inRoomX + dir, inRoomY - 2, true)) {
            this.inRoomX += dir;
            this.inRoomY -= 2;
            LStData.GL.updateTriggers(inRoomX + dir, inRoomY);
        }

    }

    private void tryMoveY(int dir) {
        //TODO: Check collision first
        if (!checkCollisionAt(inRoomX, inRoomY + dir, !InHandler.instance.isKeyPressed(KeyEvent.VK_DOWN) && dir > 0)) {
            this.inRoomY += dir;
            LStData.GL.updateTriggers(inRoomX, inRoomY + dir);
        }
    }

    public void setItemModifier(String n) {
        this.itemMod = n;
    }

    public void getDamaged(int damage) {
        //TODO: better damage handling
        if (LStData.invincTicks == 0) {
            LStData.invincTicks = 15;
            LStData.healthStat -= damage;
        }
    }

    private class playerMotionPerformer implements ILogicTask {
        private int tickCount = 3;
        private WorldPlayer wp;
        private int dir;

        public playerMotionPerformer(WorldPlayer wp, int dir) {
            this.wp = wp;
            this.dir = dir;
        }

        @Override
        public boolean isReady() {
            return true;
        }

        @Override
        public void perform() {
            if (tickCount > 1) {
                wp.tryMoveX(dir);
                if (tickCount == 2) wp.tryMoveX(dir);
                tickCount--;
            } else {
                if (dir > 0) wp.commandGoRight--;
                else wp.commandGoLeft--;
            }
        }

        @Override
        public boolean doRepeat() {
            return tickCount > 0;
        }
    }

    private class playerPhysicsLogic implements ILogicTask {

        private WorldPlayer wp;
        private int dir;
        private int steps;

        public playerPhysicsLogic(WorldPlayer wp, int dir, int steps) {
            this.wp = wp;
            this.dir = dir;
            this.steps = steps;
        }

        @Override
        public boolean isReady() {
            return true;
        }

        @Override
        public void perform() {
            for (int i = 0; i < steps; i++) {
                wp.tryMoveY(dir);
            }
        }

        @Override
        public boolean doRepeat() {
            return wp.hazPhysics;
        }
    }

    private class playerJumpLogic implements ILogicTask {

        private int tickCount = 5;
        private WorldPlayer wp;
        private int dir;
        private int steps;

        public playerJumpLogic(WorldPlayer wp, int dir, int steps) {
            this.wp = wp;
            this.dir = dir;
            this.steps = steps;
        }

        @Override
        public boolean isReady() {
            return true;
        }

        @Override
        public void perform() {
            for (int i = 0; i < steps; i++) {
                wp.tryMoveY(dir);
            }
            tickCount--;
            if (tickCount == 0) {
                wp.commandGoJump--;
            }
        }

        @Override
        public boolean doRepeat() {
            return tickCount >= 0;
        }
    }

    private class attackCarrot implements ILogicTask {

        int ticksLeft = 32;
        WorldPlayer target;
        private int facingMod = 0;

        public attackCarrot(WorldPlayer worldPlayer) {
            target = worldPlayer;
            target.animType = "A";
            if (target.animId >= 10) facingMod = 10;
        }

        @Override
        public boolean isReady() {
            return true;
        }

        @Override
        public void perform() {
            ticksLeft--;
            if (ticksLeft == 7) {
                if (facingMod > 5)
                    StData.currentGC.currentLT.registerBasic(new carrotShoot(-8, target.inRoomX - 3, target.inRoomY)); //int direction, int startX, int startY
                else
                    StData.currentGC.currentLT.registerBasic(new carrotShoot(8, target.inRoomX + 3, target.inRoomY)); //int direction, int startX, int startY
            }
            if (ticksLeft > 24) {
                target.animId = 1 + facingMod;
            } else if (ticksLeft > 16) {
                target.animId = 2 + facingMod;
            } else if (ticksLeft > 12) {
                target.animId = 3 + facingMod;
            } else if (ticksLeft > 8) {
                target.animId = 4 + facingMod;
            } else if (ticksLeft > 4) {
                target.animId = 5 + facingMod;
            } else if (ticksLeft > 2) {
                target.animId = 6 + facingMod;
            } else if (ticksLeft == 0) {
                target.attackLocked = false;
            }
        }

        @Override
        public boolean doRepeat() {
            return ticksLeft > -1;
        }
    }

    private class attackMeat implements ILogicTask {

        int ticksLeft = 6;
        WorldPlayer target;
        private int facingMod = 0;

        public attackMeat(WorldPlayer worldPlayer) {
            target = worldPlayer;
            target.animType = "A";
            if (target.animId >= 10) facingMod = 10;
        }

        @Override
        public boolean isReady() {
            return true;
        }

        @Override
        public void perform() {
            ticksLeft--;
            if (ticksLeft == 2) {
                if (facingMod > 5)
                    StData.currentGC.currentLT.registerBasic(new Task_DelayedDamage(100, 1, false, target.inRoomX - 1, target.inRoomY - 10, 10, 20)); //int direction, int startX, int startY
                else
                    StData.currentGC.currentLT.registerBasic(new Task_DelayedDamage(100, 1, false, target.inRoomX - 12, target.inRoomY - 10, 10, 20)); //int direction, int startX, int startY
            }
            if (ticksLeft > 5) {
                target.animId = 1 + facingMod;
            } else if (ticksLeft > 3) {
                target.animId = 2 + facingMod;
            } else if (ticksLeft > 0) {
                target.animId = 3 + facingMod;
            } else if (ticksLeft == 0) {
                target.attackLocked = false;
            }
        }

        @Override
        public boolean doRepeat() {
            return ticksLeft > -1;
        }
    }

    private class carrotShoot extends Sprite implements ILogicTask {

        private int liveTicksLeft = 16;

        private int dir;
        private int inRoomX;
        private int inRoomY;

        public carrotShoot(int i, int inRoomX, int inRoomY) {
            this.dir = i;
            this.inRoomX = inRoomX;
            this.inRoomY = inRoomY;
            StData.currentGC.currentSC.sprites[119] = this;
            StData.currentGC.currentSC.sprites_front[119] = 119;
            StData.currentGC.currentLT.registerBasic(this);
        }

        @Override
        public boolean isReady() {
            return true;
        }

        @Override
        public void perform() {
            if (dir > 0) {
                StData.currentGC.currentLT.registerBasic(new Task_DelayedDamage(75, 1, false, inRoomX + ((16 - liveTicksLeft) * dir), inRoomY + (2 * (16 - liveTicksLeft)), 10, 10));
                StData.currentGC.currentLT.registerBasic(new Task_DelayedDamage(75, 1, false, inRoomX + ((16 - liveTicksLeft) * dir), inRoomY, 30, 10));
                StData.currentGC.currentLT.registerBasic(new Task_DelayedDamage(75, 1, false, inRoomX + ((16 - liveTicksLeft) * dir), inRoomY - (2 * (16 - liveTicksLeft)), 10, 10));
            } else {
                StData.currentGC.currentLT.registerBasic(new Task_DelayedDamage(75, 1, false, inRoomX + ((16 - liveTicksLeft) * dir), inRoomY + (2 * (16 - liveTicksLeft)), 10, 10));
                StData.currentGC.currentLT.registerBasic(new Task_DelayedDamage(75, 1, false, inRoomX + ((16 - liveTicksLeft) * dir), inRoomY, 30, 10));
                StData.currentGC.currentLT.registerBasic(new Task_DelayedDamage(75, 1, false, inRoomX + ((16 - liveTicksLeft) * dir), inRoomY - (2 * (16 - liveTicksLeft)), 10, 10));
            }

            if (liveTicksLeft == 1) {
                StData.currentGC.currentSC.sprites[119] = null;
            }

            liveTicksLeft--;
        }

        @Override
        public boolean doRepeat() {
            return liveTicksLeft > -1;
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
            BufferedImage tex = StData.resources.grf.getTexture("CBULETL");
            if (dir < 0) tex = StData.resources.grf.getTexture("CBULETR");
            G.drawImage(tex, inRoomX + ((16 - liveTicksLeft) * dir) - LStData.renderOffsetX, inRoomY - 10 + (2 * (16 - liveTicksLeft)) - LStData.renderOffsetY, null);
            G.drawImage(tex, inRoomX + ((16 - liveTicksLeft) * dir) - LStData.renderOffsetX, inRoomY - 10 - LStData.renderOffsetY, null);
            G.drawImage(tex, inRoomX + ((16 - liveTicksLeft) * dir) - LStData.renderOffsetX, inRoomY - 10 - (2 * (16 - liveTicksLeft)) - LStData.renderOffsetY, null);
        }
    }
}
