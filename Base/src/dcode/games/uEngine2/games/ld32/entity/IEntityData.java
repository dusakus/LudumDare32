package dcode.games.uEngine2.games.ld32.entity;

import java.awt.*;

/**
 * Created by dusakus on 18.04.15.
 */
public interface IEntityData {
	int getType();
	Point getInitialLocation();
	String getTextureId();
	int getDepth();
}
