package dcode.games.uEngine2.games.ld32;

import dcode.games.uEngine2.games.ld32.world.WorldPlayer;

/**
 * Created by dusakus on 10.04.15.
 */
public class InHandler extends dcode.games.uEngine2.PInputHandler {

    public static InHandler instance;

    public InHandler() {
        instance = this;
    }


    @Override
    public void keyTyped(char Char) {

    }

    @Override
    public void keyPressed(int ID) {

    }

    @Override
    public void keyReleased(int ID) {

    }

    @Override
    public void keyPressed(String Key) {

    }

    @Override
    public void keyReleased(String Key) {

    }

    @Override
    public void keyPressed_W() {
        if (LStData.currentMode == LStData.MODE_GAME_PLAY && LStData.currentStatus == 201) {
            ((WorldPlayer) LStData.GL.getSContent().sprites[2]).updateCoords(0, -1);
        }
    }

    @Override
    public void keyPressed_S() {
        if (LStData.currentMode == LStData.MODE_GAME_PLAY && LStData.currentStatus == 201) {
            ((WorldPlayer) LStData.GL.getSContent().sprites[2]).updateCoords(0, 1);
        }

    }

    @Override
    public void keyPressed_A() {
        if (LStData.currentMode == LStData.MODE_GAME_PLAY && LStData.currentStatus == 201) {
            ((WorldPlayer) LStData.GL.getSContent().sprites[2]).updateCoords(-1, 0);
        }

    }

    @Override
    public void keyPressed_D() {
        if (LStData.currentMode == LStData.MODE_GAME_PLAY && LStData.currentStatus == 201) {
            ((WorldPlayer) LStData.GL.getSContent().sprites[2]).updateCoords(1, 0);
        }

    }

    @Override
    public void keyPressed_Z() {

    }

    @Override
    public void keyPressed_X() {

    }

    @Override
    public void keyPressed_UP() {

    }

    @Override
    public void keyPressed_DOWN() {

    }

    @Override
    public void keyPressed_LEFT() {

    }

    @Override
    public void keyPressed_RIGHT() {

    }

    @Override
    public void keyPressed_ENTER() {

    }

    @Override
    public void keyPressed_SPACE() {

    }

    @Override
    public void keyPressed_BACKSPACE() {

    }

    @Override
    public void keyPressed_ESC() {

    }

    @Override
    public void keyPressed_TAB() {

    }

    @Override
    public void keyPressed_KonamiCode() {

    }

    @Override
    public void clickedRight(int x, int y) {
    }

    @Override
    public void clickedLeft(int x, int y) {
    }

    @Override
    public void clickedMiddle(int x, int y) {

    }

    @Override
    public void scrolledUp(int x, int y) {

    }

    @Override
    public void scrolledDown(int x, int y) {

    }
}
