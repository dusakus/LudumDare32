package dcode.games.uEngine2.games.ld32;

import dcode.games.uEngine2.GFX.ILayer;
import dcode.games.uEngine2.GFX.ScreenContent;
import dcode.games.uEngine2.GFX.layers.ClearColorLayer;
import dcode.games.uEngine2.GFX.layers.FillTextureLayer;
import dcode.games.uEngine2.LOGIC.ILogicTask;
import dcode.games.uEngine2.LOGIC.LogicTasks;
import dcode.games.uEngine2.StData;
import dcode.games.uEngine2.games.ld32.parts.MenuEntry;
import dcode.games.uEngine2.games.ld32.parts.MenuPointer;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import static dcode.games.uEngine2.games.ld32.LStData.*;

/**
 * Created by dusakus on 10.04.15.
 */
public class MenuLogic implements ILogicTask {

	private ScreenContent inMenuSC;
	private ArrayList<MenuEntry> content;

	private int currentSelection = 1;
	private int maxSelection = 4;
	private int inputDelay = 10;

	private int confirmedStatusShift = 1;

	private long timestamp;
	private int waitFor = 100;
	private MenuAnimationLayer animator;
	private int keylock = 10;

	private boolean firstRun = true;

	@Override
	public boolean isReady() {
		return LStData.currentMode == LStData.MODE_MENU_MAIN;
	}

	@Override
	public void perform() {
		switch (currentStatus) {
			case 1:
				//TODO: leave the loop
				StData.LOG.println("[MenuLogic] Entry point reached");
				currentStatus++;
				break;
			case 2:
				if (inMenuSC == null) currentStatus = 501;
				else {
					StData.currentGC.currentSC = inMenuSC;
					animator.animateIn();
					bgm.stop();
					currentStatus = 10;
					LStData.bgm.stop();
					LStData.boss.stop();
					LStData.menu.play(true);

				}
				break;

			case 10:
				//set menu screen and enter menu loop
				if (animator.animFinished) currentStatus = 121; // enter main screen
				break;

			//MAIN
			case 121:
				content = new ArrayList<MenuEntry>();
				content.add(new MenuEntry("Start Game", 20, 60));
				content.add(new MenuEntry("Options", 20, 70));
				content.add(new MenuEntry("Reset", 20, 80));
				content.add(new MenuEntry("EXIT", 20, 90));

				currentStatus = 260;
				break;

			case 260:
				monitorInput();
				break;

			case 501:  //Create screen content
				StData.LOG.println("[MenuLogic] first run, creating content");
				inMenuSC = new ScreenContent();
				currentStatus++;
				break;
			case 502:  //Create MenuBgFiller layer
				inMenuSC.layers_Background.add(new ClearColorLayer(new Color(24, 40, 84)));
				inMenuSC.layers_Center.add(new MenuAnimationLayer(this)); //TODO: get that texture somewhere
				//inMenuSC.postProcessors[0] = new PProc_MenuUpScaler();
				currentStatus++;
				break;
			case 503:  //Create menu pointer
				inMenuSC.sprites[2] = new MenuPointer(this);
				inMenuSC.sprites_front[0] = 2;
				currentStatus++;
				break;
			case 504:
				//inMenuSC.layers_Center.add(new MenuListLayer(this));
				currentStatus = 2;
				break;
			case 1001:
				if (currentSelection == 1) {
					if (firstRun) {
						currentStatus = 1301;
					} else {
						currentMode = MODE_GAME_PLAY;
						currentStatus = 1;
						menu.stop();
					}
				} else if (currentSelection == 2) {
					currentStatus = 1203;
				} else if (currentSelection == 3) {
					currentStatus = 1204;
				} else if (currentSelection == 4) {
					currentMode = MODE_SHUTDOWN;
				}
				break;
			case 1201:
				StData.currentGC.currentLT = new LogicTasks();
				inMenuSC.layers_Overlay.add(new FillTextureLayer("DEATH"));
				StData.currentGC.currentSC = inMenuSC;
				keylock = 30;
				currentStatus++;
				break;
			case 1202:
				if (InHandler.instance.isKeyPressed(KeyEvent.VK_ENTER) && keylock == 0) {
					currentStatus = 2;
					inMenuSC.layers_Overlay.clear();
					inputDelay = 20;
					LStData.GL.reset();
				}
				break;
			case 1203:
				inMenuSC.layers_Overlay.add(new FillTextureLayer("HELP"));
				StData.currentGC.currentSC = inMenuSC;
				keylock = 30;
				currentStatus = 1202;
				break;
			case 1204:
				inMenuSC.layers_Overlay.add(new FillTextureLayer("CREDITS"));
				StData.currentGC.currentSC = inMenuSC;
				keylock = 30;
				currentStatus = 1202;
				break;
			case 1301:
				inMenuSC.layers_Overlay.add(new FillTextureLayer("INTRO1"));
				keylock = 30;
				currentStatus++;
				break;
			case 1302:
				if (keylock == 0) {
					currentStatus++;
					inMenuSC.layers_Overlay.clear();
				}
				break;
			case 1303:
				inMenuSC.layers_Overlay.add(new FillTextureLayer("INTRO2"));
				keylock = 30;
				currentStatus++;
				break;
			case 1304:
				if (keylock == 0) {
					currentStatus++;
					inMenuSC.layers_Overlay.clear();
				}
				break;
			case 1305:
				inMenuSC.layers_Overlay.add(new FillTextureLayer("INTRO3"));
				keylock = 30;
				currentStatus++;
				break;
			case 1306:
				if (keylock == 0) {
					currentStatus++;
					inMenuSC.layers_Overlay.clear();
				}
				break;
			case 1307:
				inMenuSC.layers_Overlay.add(new FillTextureLayer("INTRO4"));
				keylock = 30;
				currentStatus++;
				break;
			case 1308:
				if (keylock == 0) {
					currentStatus++;
					inMenuSC.layers_Overlay.clear();
				}
				break;
			case 1309:
				inMenuSC.layers_Overlay.add(new FillTextureLayer("INTRO5"));
				keylock = 30;
				currentStatus++;
				break;
			case 1310:
				if (keylock == 0) {
					currentStatus++;
					inMenuSC.layers_Overlay.clear();
				}
				break;
			case 1311:
				inMenuSC.layers_Overlay.add(new FillTextureLayer("INTRO6"));
				keylock = 30;
				currentStatus++;
				break;
			case 1312:
				if (keylock == 0) {
					currentStatus++;
					inMenuSC.layers_Overlay.clear();
				}
				break;
			case 1313:
				inMenuSC.layers_Overlay.add(new FillTextureLayer("INTRO7"));
				keylock = 30;
				currentStatus++;
				break;
			case 1314:
				if (keylock == 0) {
					currentStatus++;
					inMenuSC.layers_Overlay.clear();
				}
				break;
			case 1315:
				inMenuSC.layers_Overlay.add(new FillTextureLayer("INTRO8"));
				keylock = 30;
				currentStatus++;
				break;
			case 1316:
				if (keylock == 0) {
					currentStatus++;
					inMenuSC.layers_Overlay.clear();
				}
				break;
			case 1317:
				inMenuSC.layers_Overlay.add(new FillTextureLayer("INTRO9"));
				keylock = 30;
				currentStatus++;
				break;
			case 1318:
				if (keylock == 0) {
					currentStatus++;
					inMenuSC.layers_Overlay.clear();
				}
				break;
			case 1319:
				inMenuSC.layers_Overlay.add(new FillTextureLayer("INTRO10"));
				keylock = 30;
				currentStatus++;
				break;
			case 1320:
				if (keylock == 0) {
					currentStatus++;
					inMenuSC.layers_Overlay.clear();
				}
				break;
			case 1321:
				inMenuSC.layers_Overlay.add(new FillTextureLayer("INTRO11"));
				keylock = 30;
				currentStatus++;
				break;
			case 1322:
				if (InHandler.instance.isKeyPressed(KeyEvent.VK_ENTER) && keylock == 0) {
					currentMode = MODE_GAME_PLAY;
					currentStatus = 1;
					inMenuSC.layers_Overlay.clear();
					firstRun = false;
					menu.stop();
				}
				break;
			case 1401:
				inMenuSC.layers_Overlay.add(new FillTextureLayer("OUTRO1"));
				LStData.GL.reset();
				keylock = 30;
				currentStatus++;
				break;
			case 1402:
				if (keylock == 0) {
					currentStatus++;
					inMenuSC.layers_Overlay.clear();
				}
				break;
			case 1403:
				inMenuSC.layers_Overlay.add(new FillTextureLayer("OUTRO2"));
				keylock = 30;
				currentStatus++;
				break;
			case 1404:
				if (keylock == 0) {
					currentStatus++;
					inMenuSC.layers_Overlay.clear();
				}
				break;
			case 1405:
				inMenuSC.layers_Overlay.add(new FillTextureLayer("OUTRO3"));
				keylock = 30;
				currentStatus++;
				break;
			case 1406:
				if (keylock == 0) {
					currentStatus++;
					inMenuSC.layers_Overlay.clear();
				}
				break;
			case 1407:
				inMenuSC.layers_Overlay.add(new FillTextureLayer("OUTRO4"));
				keylock = 30;
				currentStatus++;
				break;
			case 1408:
				if (keylock == 0) {
					currentStatus++;
					inMenuSC.layers_Overlay.clear();
				}
				break;
			case 1409:
				inMenuSC.layers_Overlay.add(new FillTextureLayer("OUTRO5"));
				keylock = 30;
				currentStatus++;
				break;
			case 1410:
				if (keylock == 0) {
					currentStatus++;
					inMenuSC.layers_Overlay.clear();
				}
				break;
			case 1411:
				inMenuSC.layers_Overlay.add(new FillTextureLayer("OUTRO6"));
				keylock = 30;
				currentStatus++;
				break;
			case 1412:
				if (InHandler.instance.isKeyPressed(KeyEvent.VK_ENTER) && keylock == 0) {
					currentStatus = 2;
					inMenuSC.layers_Overlay.clear();
					firstRun = false;
				}
				break;

		}
		if (keylock > 0) {
			keylock--;
		}
	}

	private void monitorInput() {
		if (inputDelay > 0) inputDelay--;
		else {
			if (currentSelection > 1 && (InHandler.instance.isKeyPressed(KeyEvent.VK_UP) || InHandler.instance.isKeyPressed(KeyEvent.VK_W))) {
				currentSelection--;
				inputDelay = 5;
			}
			if (currentSelection < maxSelection && (InHandler.instance.isKeyPressed(KeyEvent.VK_DOWN) || InHandler.instance.isKeyPressed(KeyEvent.VK_S))) {
				currentSelection++;
				inputDelay = 5;
			}
		}
		if (InHandler.instance.isKeyPressed(KeyEvent.VK_ENTER)) currentStatus = 1000 + confirmedStatusShift;
	}

	@Override
	public boolean doRepeat() {
		return true;
	}

	public Point getCurrentPoinerCoords() {
		return new Point(12, 88 + currentSelection * 12);
	}

	public ArrayList<MenuEntry> getEntryList() {
		return content;
	}

	public void setAnimator(MenuAnimationLayer animator) {
		this.animator = animator;
	}

	private class MenuAnimationLayer implements ILayer {

		public boolean animFinished = true;

		private int currentFrame = 0;
		private int targetFrame = 0;
		private int delay = 0;

		public MenuAnimationLayer(MenuLogic menuLogic) {
			menuLogic.setAnimator(this);
		}

		public void animateIn() {
			animFinished = false;
			currentFrame = 0;
			targetFrame = 30;
			delay = 8;
		}

		@Override
		public void draw(Graphics2D G2D) {
			G2D.drawImage(LStData.menuBackground.getFrame(currentFrame), 0, 0, null);
			if (!animFinished) {
				if (currentFrame != targetFrame) {
					if (delay == 0) {
						currentFrame++;
						delay = 5;
					} else delay--;
				} else animFinished = true;
			} else {
				if (delay == 0) {
					if (currentFrame == 38) currentFrame = 37;
					else currentFrame = 38;
					delay = 15;
				} else delay--;
			}
		}

		@Override
		public boolean removeMe() {
			return false;
		}

		@Override
		public boolean renderMe() {
			return true;
		}
	}
}
