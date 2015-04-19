package dcode.games.uEngine2.games.ld32.entity;

import dcode.games.uEngine2.StData;
import dcode.games.uEngine2.games.ld32.LStData;
import dcode.games.uEngine2.games.ld32.world.WorldEntity;
import dcode.games.uEngine2.games.ld32.world.WorldPlayer;

import java.awt.*;

/**
 * Created by dusakus on 18.04.15.
 */
public class FollowerEntity implements IEntityData, IEntityLogic {
    private final int motionSpeed;
    private final int distance;
    private final int offsetX, offsetY;
    private int motionDelay = 10;

    public FollowerEntity(int speed, int space, int spriteoffsetX, int spriteOffsetY) {
        motionSpeed = speed;
        distance = space;
        offsetX = spriteoffsetX;
        offsetY = spriteOffsetY;
    }

    @Override
    public int getType() {
        return WorldEntity.TYPE_FOLLOWER;
    }

    @Override
    public Point getInitialLocation() {
        return new Point(150, 150);
    }

    @Override
    public String getTextureId() {
        return "TESTENTITY";
    }

    @Override
    public int getDepth() {
        return 10;
    }

    @Override
    public void initializeLogic() {

    }

    @Override
    public boolean shouldCheck() {
        return true;
    }

    @Override
    public void update(WorldEntity we) {
        if (motionDelay <= 0) {
            if (((WorldPlayer) StData.currentGC.currentSC.sprites[2]).inRoomX > we.getX() + LStData.renderOffsetX - distance)
                we.updateCoords(motionSpeed, 0);
            if (((WorldPlayer) StData.currentGC.currentSC.sprites[2]).inRoomX < we.getX() + LStData.renderOffsetX + distance)
                we.updateCoords(-motionSpeed, 0);
            if (((WorldPlayer) StData.currentGC.currentSC.sprites[2]).inRoomY > we.getY() + LStData.renderOffsetY - distance)
                we.updateCoords(0, motionSpeed);
            if (((WorldPlayer) StData.currentGC.currentSC.sprites[2]).inRoomY < we.getY() + LStData.renderOffsetY + distance)
                we.updateCoords(0, -motionSpeed);
            motionDelay = 4;
        } else {
            motionDelay--;
        }
    }
}
