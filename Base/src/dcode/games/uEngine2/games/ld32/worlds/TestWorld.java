package dcode.games.uEngine2.games.ld32.worlds;

import dcode.games.uEngine2.BGTasks.internalTasks.LoadBasicTexture;
import dcode.games.uEngine2.StData;
import dcode.games.uEngine2.games.ld32.LStData;
import dcode.games.uEngine2.games.ld32.entity.FollowerEntity;
import dcode.games.uEngine2.games.ld32.entity.IEntityData;
import dcode.games.uEngine2.games.ld32.entity.IEntityLogic;
import dcode.games.uEngine2.games.ld32.world.GameWorld;
import dcode.games.uEngine2.games.ld32.world.WorldEntity;
import dcode.games.uEngine2.games.ld32.world.WorldPlayer;

import java.awt.*;

/**
 * Created by dusakus on 18.04.15.
 */
public class TestWorld extends PWorld {


    @Override
    public String getTexID() {
        return "WORLD";
    }

    @Override
    public void loadYerself(GameWorld target) {
        target.entities.add(new TestEntity(new TestEntityData(), new FollowerEntity(2, 50, 0, 0)));
        target.entities.add(new TestEntity(new TestEntityData(), new FollowerEntity(1, 60, 0, 0)));
        target.entities.add(new TestEntity(new TestEntityData(), new FollowerEntity(2, 50, 0, 0)));
        target.entities.add(new TestEntity(new TestEntityData(), new FollowerEntity(1, 40, 0, 0)));
        target.entities.add(new TestEntity(new TestEntityData(), new FollowerEntity(2, 50, 0, 0)));
        target.entities.add(new TestEntity(new TestEntityData(), new FollowerEntity(1, 50, 0, 0)));
        target.entities.add(new TestEntity(new TestEntityData(), new FollowerEntity(2, 20, 0, 0)));
        target.entities.add(new TestEntity(new TestEntityData(), new FollowerEntity(1, 56, 0, 0)));
        target.entities.add(new TestEntity(new TestEntityData(), new FollowerEntity(2, 50, 0, 0)));
        target.entities.add(new TestEntity(new TestEntityData(), new FollowerEntity(1, 53, 0, 0)));
        target.entities.add(new TestEntity(new TestEntityData(), new FollowerEntity(2, 10, 0, 0)));
        target.entities.add(new TestEntity(new TestEntityData(), new FollowerEntity(1, 50, 0, 0)));
        target.entities.add(new TestEntity(new TestEntityData(), new FollowerEntity(2, 34, 0, 0)));
        target.entities.add(new TestEntity(new TestEntityData(), new FollowerEntity(1, 50, 0, 0)));
        target.entities.add(new TestEntity(new TestEntityData(), new FollowerEntity(2, 30, 0, 0)));
        target.entities.add(new TestEntity(new TestEntityData(), new FollowerEntity(1, 50, 0, 0)));
        StData.generalBGT.LPTasks.add(new LoadBasicTexture("world/TESTWORLD.png", "WORLD"));
    }

    @Override
    public boolean checkUnlocked(int RColor) {
        return true;
    }

    @Override
    public void triggerEvent(Color data) {
        if (data.getBlue() == 240) {
            StData.LOG.println("TRIGGERED");
        }
    }

    private class TestEntity extends WorldEntity {

        public TestEntity(IEntityData ed, IEntityLogic el) {
            super(ed, el, 16, 16);
        }
    }

    private class TestEntityData implements IEntityData {

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
        public int getHealth() {
            return 0;
        }
    }

    private class TestEntityLogic implements IEntityLogic {
        private int motionDelay = 10;

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
                if (((WorldPlayer) StData.currentGC.currentSC.sprites[2]).inRoomX > we.getX() + LStData.renderOffsetX - 32)
                    we.updateCoords(1, 0);
                if (((WorldPlayer) StData.currentGC.currentSC.sprites[2]).inRoomX < we.getX() + LStData.renderOffsetX + 32)
                    we.updateCoords(-1, 0);
                if (((WorldPlayer) StData.currentGC.currentSC.sprites[2]).inRoomY > we.getY() + LStData.renderOffsetY - 32)
                    we.updateCoords(0, 1);
                if (((WorldPlayer) StData.currentGC.currentSC.sprites[2]).inRoomY < we.getY() + LStData.renderOffsetY + 32)
                    we.updateCoords(0, -1);
                motionDelay = 3;
            } else {
                motionDelay--;
            }
        }

        @Override
        public void unload() {

        }
    }
}
