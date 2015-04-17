/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dcode.games.uEngine2;

import dcode.games.uEngine2.BGTasks.BackgroundTasks;
import dcode.games.uEngine2.LOGIC.LogicTasks;
import dcode.games.uEngine2.ResourceManager.ResMan;
import dcode.games.uEngine2.SFX.tslib.TinySound;
import dcode.games.uEngine2.translator.Translator;

import java.io.File;

/**
 * @author dusakus
 */
public class Startup {

	public static void StartGame(PuGameBase GB) {
		//System.setProperty("sun.java2d.opengl","True"); // does that change anything? (Except for crashing the game)
		Thread.currentThread().setName("ITNI");
		StData.GameInitializer = GB;
		StData.setup = GB.setup;
		StData.gameStorageDirectory = new File(System.getProperty("user.dir") + "/DCODE/uEngine/" + StData.setup.safeName);
		StData.LOG = new DCoutputH(GB.setup.debug);
		StData.threadManager = new ThreadManager();
		StData.resources = new ResMan();
		StData.currentGC = new GameContainer();
		StData.logicTasks = new LogicTasks();
		StData.generalBGT = new BackgroundTasks();
		if (StData.setup.enableTranslator) StData.translator = new Translator(StData.setup.defaultLangId);
		StData.threadManager.startEngine();
		StData.threadManager.setInputHandler(GB.initialInputHandler);
		GB.contentInitializer.loadInitialGameContent();
		StData.threadManager.monitorThreads();
		TinySound.shutdown();
		StData.LOG.println("MONITOR THREAD CEASED BEING ALIVE, EXITTING");
		StData.LOG.dumpBuffer();
		StData.LOG.END("uEngine2: BaYo!");

		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
		}
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
		}
		GB.contentInitializer.engineStopped();
	}
}
