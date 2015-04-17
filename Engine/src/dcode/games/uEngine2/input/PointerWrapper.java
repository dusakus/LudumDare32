/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dcode.games.uEngine2.input;

import dcode.games.uEngine2.PInputHandler;
import dcode.games.uEngine2.StData;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * @author dusakus
 */
public class PointerWrapper implements MouseListener {

	private PInputHandler PIH;

	public void setInputHandler(PInputHandler pih) {
		PIH = pih;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (PIH != null) {
			switch (e.getButton()) {
				case 1:
					PIH.clickedLeft(e.getX() / StData.setup.scale, e.getY() / StData.setup.scale);
					break;
				case 3:
					PIH.clickedRight(e.getX() / StData.setup.scale, e.getY() / StData.setup.scale);
					break;
				case 2:
					PIH.clickedMiddle(e.getX() / StData.setup.scale, e.getY() / StData.setup.scale);
					break;
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

}
