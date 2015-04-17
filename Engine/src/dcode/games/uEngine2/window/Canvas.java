/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dcode.games.uEngine2.window;

import dcode.games.uEngine2.StData;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

/**
 * @author dusakus
 */
public class Canvas extends java.awt.Canvas {

	String currentdraw = "INIT";
	int msgticksleft = 100;
	private boolean doScale = false;

	public Canvas() {
		StData.LOG.println("uEngine2: Creating canvas", "N");
		setMinimumSize(new Dimension(StData.setup.width * StData.setup.scale, StData.setup.height * StData.setup.scale));
		setMaximumSize(new Dimension(StData.setup.width * StData.setup.scale, StData.setup.height * StData.setup.scale));
		setPreferredSize(new Dimension(StData.setup.width * StData.setup.scale, StData.setup.height * StData.setup.scale));

		StData.NextFrame = new BufferedImage(StData.setup.width, StData.setup.height, BufferedImage.TYPE_INT_ARGB);

		if (StData.setup.scale != 1) doScale = true;
	}

	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(2);
			bs = getBufferStrategy();
		}
		Graphics grf = bs.getDrawGraphics();

		if (doScale) {
			grf.drawImage(scaleFrame(StData.NextFrame, StData.setup.scale), 0, 0, null);
		} else {
			grf.drawImage(StData.NextFrame, 0, 0, null);
		}

		bs.show();
		//this.repaint();
		this.requestFocusInWindow();
	}

	public void onInitWrite(String s) {
		currentdraw = s;

		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(2);
			bs = getBufferStrategy();
		}
		Graphics grf = bs.getDrawGraphics();
		grf.setColor(Color.BLACK);
		grf.fillRect(5, 5, 160 * StData.setup.scale - 10, 24);
		grf.setColor(Color.YELLOW);
		grf.drawString(currentdraw, 10, 20);

		bs.show();
		//this.repaint();
		this.requestFocusInWindow();
		msgticksleft = 50;

	}

	public void drawBString() {
		if (msgticksleft > 0) {
			BufferStrategy bs = getBufferStrategy();
			if (bs == null) {
				createBufferStrategy(2);
				bs = getBufferStrategy();
			}
			Graphics grf = bs.getDrawGraphics();

			grf.setColor(Color.BLACK);
			grf.fillRect(5, 5, 160 * StData.setup.scale - 10, 24);
			grf.setColor(Color.YELLOW);
			grf.drawString(currentdraw, 10, 20);

			bs.show();
			this.requestFocusInWindow();
			msgticksleft--;
		}
	}

	private Image scaleFrame(BufferedImage GRF, int scale) {
		return GRF.getScaledInstance(StData.setup.width * scale, StData.setup.height * scale, Image.SCALE_REPLICATE);
	}
}
