package dcode.games.uEngine2.window;

import dcode.games.uEngine2.StData;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * Created by dusakus on 13.04.15.
 */
public class exitListener implements WindowListener {
	@Override
	public void windowOpened(WindowEvent e) {

	}

	@Override
	public void windowClosing(WindowEvent e) {
		StData.gameIsRunning = false;
	}

	@Override
	public void windowClosed(WindowEvent e) {

	}

	@Override
	public void windowIconified(WindowEvent e) {
		StData.gameFreeze = true;
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		StData.gameFreeze = false;
	}

	@Override
	public void windowActivated(WindowEvent e) {
		StData.gameFreeze = false;
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		StData.gameFreeze = true;
	}
}
