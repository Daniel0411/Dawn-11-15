package edu.kit.informatik;

/**
 * Enthaelt die grundlegenden Informationen ueber das aktuelle Spiel (das Brett,
 * MissionControl, Nature und ob gerade Figurenset 1 oder 2 von Mission Control
 * verwendet wird)
 * 
 * @author Daniel
 * @version 2.0
 *
 */
public class CurrentMatch {
    private Board board;
    private Nature nature;
    private MissionControl missionControl;
    private boolean crtSetOne = true;

    /**
     * Konstruktor setzt board, nature und missionControl.
     * 
     * @param board          zu setztendes Brett
     * @param nature         zu setztende Nature
     * @param missionControl zu setztendes MissionControl
     */
    public CurrentMatch(Board board, Nature nature, MissionControl missionControl) {
        this.board = board;
        this.nature = nature;
        this.missionControl = missionControl;
    }

    /**
     * Setzt alle Klassenattribute zurueck auf die Anfangswerte.
     */
    public void reset() {
        board.reset();
        nature.reset();
        missionControl.reset();
        crtSetOne = true;
    }

    /**
     * Gibt das aktuelle Brett zurueck.
     * 
     * @return board
     */
    public Board getBoard() {
        return board;
    }

    /**
     * Gibt MissionControl zurueck.
     * 
     * @return missionControl
     */
    public MissionControl getMissionControl() {
        return missionControl;
    }

    /**
     * Gibt Nature zurueck.
     * 
     * @return nature
     */
    public Nature getNature() {
        return nature;
    }

    /**
     * Gibt true zurueck, falls set 1 verwendet wird, sonst false.
     * 
     * @return true, falls set 1 verwendet, sonst false
     */
    public boolean getCrtSetOne() {
        return crtSetOne;
    }

    /**
     * Setze, ob gerade Figurenset 1 oder 2 verwendet wird. true fuer 1, false fuer
     * 2
     * 
     * @param crtSetOne true fuer Set 1, sonst false
     */
    public void setCrtSetOne(boolean crtSetOne) {
        this.crtSetOne = crtSetOne;
    }
}
