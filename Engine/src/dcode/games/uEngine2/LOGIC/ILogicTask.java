/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dcode.games.uEngine2.LOGIC;

/**
 * @author dusakus
 */
public interface ILogicTask {
    boolean isReady();

    void perform();

    boolean doRepeat();
}
