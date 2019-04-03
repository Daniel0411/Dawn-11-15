package edu.kit.informatik;

import java.util.List;

/**
 * Modelliert die Struktur des Spiels und welche Befehle in welcher Phase des
 * Spiels erlaubt sind. crtMatch beinhaltet das aktuelle Spielbrett, Nature und
 * MissionControl. length ist die Laenge der zuletzt gelegten Figur von
 * MissionControl und rolledCmd die zuletzt gewuerfelte Zahl. roundCounter
 * zaehlt die Anzahl der bereits gespielten Runden. Eine Runde ist hierbei
 * beendet, wenn vesta oder ceres sich bewegt haben oder wenn es nicht mehr
 * moeglich ist diese zu bewegen, wenn eine Figur von MissionControl gelegt
 * wurde.
 * 
 * @author Daniel
 * @version 2.0
 *
 */
public class GameStructure {
    /**
     * Laenge der zuletzt gelegten Figur.
     */
    protected static int length = 0;
    /**
     * Zuletzt gewuerfelte Zahl.
     */
    protected static int rolledCmd = 0;
    /**
     * Anzahl bereits gespielter Runden.
     */
    protected static int roundCounter = 0;

    private CurrentMatch crtMatch;

    /**
     * Fasst das aktuelle Spielbrett, MissionControl und Nature in CurrentMatch
     * zusammen.
     * 
     */
    public GameStructure() {
        crtMatch = new CurrentMatch(new Board(), new Nature(), new MissionControl());
    }
    
    /**
     * Startet die einzelnen Phasen (bis Runde 6 die erste Phase, bis Runde 12 die
     * zweite und danach die Endphase). Speichert die korrekten Input Daten (Befehl
     * und Parameter) und uebergibt sie an die einzelnen Phasen. Faengt moegliche
     * Eingabefehler ab und gibt einen Error aus.
     */
    public void dawn11x15() {
        ValidateInput valideInput = null;
        Command command = null;
        List<Coordinate> coordinates = null;
        Parameter param = null;
        int rolled = 0;

        // Versucht die einzelnen Parameter zusammenzufassen.
        try {
            valideInput = new ValidateInput();
            command = valideInput.getCommand();
            coordinates = valideInput.getCoordinates();
            rolled = valideInput.getRolled();
            param = new Parameter(coordinates, rolled);
        } catch (IllegalArgumentException e) {
            Output.printError(e.getMessage());
            return;
        }

        // return false wenn Programm beendet werden soll.
        if (command == Command.QUIT) {
            command.execute(param, crtMatch);
            return;
        }

        // Versucht die Phasen entsprechend der Rundenzahl auszufuehren.
        try {
            if (roundCounter < 6) {
                phase(true, command, param);
            } else if (roundCounter < 12) {
                crtMatch.setCrtSetOne(false);
                phase(false, command, param);
            } else {
                endPhase(command, param);
            }
        } catch (IllegalArgumentException e) {
            Output.printError(e.getMessage());
        }
    }

    /**
     * Modelliert die erste und zweite Phase des Spiels. Die erste Phase wird
     * ausgefuehrt, wenn phaseOne true ist, sonst die Zweite. Wurde in der ersten
     * Phase noch kein Vesta gesetzt, wird dies zuerst gemacht. Danach wird
     * abwechselnd gewuerfelt, eine Figur von MissionControl gemaess den Regeln
     * gestzt und falls moeglich Vesta bewegt, bis alle Figuren des ersten Satzes
     * von MissionControl aufgebraucht sind. Danach wird analog die zweite Phase mit
     * Ceres statt Vesta ausgefuehrt. Wirft eine Exception, wenn ein Befehl
     * eingegeben wurde, der in dieser Phase nicht erlaubt ist oder der Reihenfolge
     * widerspricht.
     * 
     * @param phaseOne true, falls Phase eins ausgefuehrt werden soll, sonst Phase
     *                 zwei.
     * @param command  Befehl
     * @param param    beinhaltet alle moeglichen Parameter (rolled number,
     *                 coordinates)
     * @throws IllegalArgumentException falls Befehl eingegeben wurde, der in dieser
     *                                  Phase nicht erlaubt ist oder der Reihenfolge
     *                                  widerspricht.
     */
    private void phase(boolean phaseOne, Command command, Parameter param)
            throws IllegalArgumentException {
        // Vesta (Phase 1) oder Ceres (Phase 2) werden platziert, falls sie es noch
        // nicht wurde.
        if (command == Command.SET_VC && crtMatch.getNature().isVestaOrCeresNull(phaseOne)) {
            command.execute(param, crtMatch);
        }

        // Der roll Befehl wird ausgefuehrt, falls in dieser Runde noch nicht
        // gewuerfelt wurde.
        else if ((command == Command.ROLL) && (rolledCmd == 0)
                && !crtMatch.getNature().isVestaOrCeresNull(phaseOne)) {
            command.execute(param, crtMatch);
        }

        // Der place Befehl wird ausgefuehrt, falls in dieser Runde noch nicht
        // platziert, aber schon gewuerfelt wurde. Move Befehl falls noetig
        // ueberspringen.
        else if ((command == Command.PLACE) && (rolledCmd != 0) && (length == 0)) {
            command.execute(param, crtMatch);
            skipMoveIfNecessary();
        }

        // Der move Befehl wird ausgefuehrt, falls schon platziert wurde und es noch
        // moegliche Zuege fuer Vesta oder Ceres gibt. Rundenzahl wird eins erhoeht und
        // Wuerfel zurueckgesetzt.
        else if ((command == Command.MOVE) && length != 0) {
            command.execute(param, crtMatch);
            rolledCmd = 0;
            length = 0;
            roundCounter += 1;
        }

        // Wirft bei unerlaubtem Befehl Exception.
        else if (!alltimeAllowedCommands(command, param)) {
            throw new IllegalArgumentException("command not allowed in this phase of the game!");
        }
    }

    /**
     * Startet die naechste Runde, wenn kein Zug mehr fuer vesta und ceres moeglich
     * ist.
     */
    private void skipMoveIfNecessary() {
        boolean setOne = crtMatch.getCrtSetOne();
        Nature nature = crtMatch.getNature();
        Board board = crtMatch.getBoard();
        if (setOne && (board.getEmptyNeighbor(nature.getVestaCoordinate()).size() == 0)) {
            rolledCmd = 0;
            length = 0;
            roundCounter += 1;
        } else if (!setOne && (board.getEmptyNeighbor(nature.getCeresCoordinate()).size() == 0)) {
            rolledCmd = 0;
            length = 0;
            roundCounter += 1;
        }
    }

    /**
     * Modelliert die Phase des Spiels, nachdem alle Steine gelegt wurden. Fuehrt
     * nur noch die Befehle show-result, print, state und reset aus. Wirft bei
     * anderen Befehlen eine Exception.
     * 
     * @param command Befehl
     * @param param   beinhaltet alle moeglichen Parameter (rolled number,
     *                coordinates)
     * @throws IllegalArgumentException falls ein Befehl eingegeben wurde, welcher
     *                                  in der Endphase nicht erlaubt ist.
     */
    private void endPhase(Command command, Parameter param) throws IllegalArgumentException {
        if (command == Command.SHOW_RESULT) {
            command.execute(param, crtMatch);
        } else if (!alltimeAllowedCommands(command, param)) {
            throw new IllegalArgumentException("command not allowed in this phase of the game!");
        }
    }

    /**
     * Fuehrt Befehle aus, die waehrend aller Phasen erlaubt sind. Gibt false
     * zurueck, wenn keiner der Befehle ausgefuehrt wurde, true falls einer
     * ausgefuehrt wurde.
     * 
     * @param command Befehl
     * @param param   beinhaltet alle moeglichen Parameter (rolled number,
     *                coordinates)
     * @return false wenn kein Befehl ausgefuehrt wurde, sonst true
     */
    private boolean alltimeAllowedCommands(Command command, Parameter param) {
        switch (command) {
        case STATE:
            command.execute(param, crtMatch);
            break;
        case PRINT:
            command.execute(param, crtMatch);
            break;
        case RESET:
            command.execute(param, crtMatch);
            break;
        default:
            return false;
        }
        return true;
    }

    /**
     * Setzt die aktuelle Spielstruktur zurueck auf Spielanfang. Dazu wird die
     * Laenge der aktuellen Figur, die aktuelle Wuerfelzahl und die Rundenzahl auf 0
     * gesetzt
     */
    public static void reset() {
        length = 0;
        rolledCmd = 0;
        roundCounter = 0;
    }

}
