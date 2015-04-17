/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dcode.games.uEngine2.window;

import dcode.games.uEngine2.StData;
import dcode.games.uEngine2.input.KeyWrapper;
import dcode.games.uEngine2.input.PointerWrapper;

import javax.swing.*;
import java.awt.*;

/**
 * @author dusakus
 */
public class Window {

	private JFrame window;
	private Canvas gc;

	public Window(Canvas GC) {
		gc = GC;
		StData.LOG.println("uEngine2: Creating window", "N");
		window = new JFrame(StData.setup.windowTitle + " | in DCode uEngine " + StData.VersionString);
		window.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		window.setLayout(new BorderLayout());
		window.add(GC, BorderLayout.CENTER);
		window.setResizable(false);
		window.pack();
		window.setLocationRelativeTo(null);
		window.setVisible(true);
		StData.LOG.println("uEngine2: Done", "N");
	}

	public void registerListeners(KeyWrapper keyM, PointerWrapper pointM) {
		gc.addKeyListener(keyM);
		gc.addMouseListener(pointM);
		gc.requestFocus();
		gc.requestFocusInWindow();

		window.addWindowListener(new exitListener());
	}

	public void updateWLabel(String s) {
		if (s != null) {
			window.setTitle(s);
		} else {
			window.setTitle(StData.setup.windowTitle + " | in DCode uEngine " + StData.VersionString);
		}
	}

	public void rimuw() {
		window.dispose();
	}

}
