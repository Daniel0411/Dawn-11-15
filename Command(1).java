package edu.kit.informatik;

import java.util.List;

/**
 * Enum beinhaltet alle Befehle, deren Ausführung und wie die Enums als String
 * geschrieben werden.
 * 
 * @author Daniel
 * @version 2.0
 * 
 */
public enum Command {
    /**
     * Enum fuer den print-Befehl. Beinhaltet die String Schreibweise des Befehls
     * und die Ausfuehrung.
     */
    PRINT("print") {
        /**
         * Gibt das aktuelle Spielbrett aus.
         * 
         * @param param    Parameter des Befehls
         * @param crtMatch Grundlegende Spieleigenschaften (Brett, nature, mission
         *                 control)
         */
        public void execute(Parameter param, CurrentMatch crtMatch) {
            Output.printBoard(crtMatch.getBoard());
        }
    },
    /**
     * Enum fuer den show-result-Befehl. Beinhaltet die String Schreibweise des
     * Befehls und die Ausfuehrung.
     */
    SHOW_RESULT("show-result") {
        /**
         * Berechnet das Ergebnis und gibt es aus.
         * 
         * @param param    Parameter des Befehls
         * @param crtMatch Grundlegende Spieleigenschaften (Brett, nature, mission
         *                 control)
         */
        public void execute(Parameter param, CurrentMatch crtMatch) {
            Board board = crtMatch.getBoard();
            int vestaResult = board.getPartResult(crtMatch.getNature().getVesta());
            int ceresResult = board.getPartResult(crtMatch.getNature().getCeres());
            int result = 0;
            if (vestaResult > ceresResult) {
                result = vestaResult + (vestaResult - ceresResult);
            } else {
                result = ceresResult + (ceresResult - vestaResult);
            }
            Output.printResult(String.valueOf(result));
        }
    },
    /**
     * Enum fuer den reset-Befehl. Beinhaltet die String Schreibweise des Befehls
     * und die Ausfuehrung.
     */
    RESET("reset") {
        /**
         * Setzt das Spiel zurueck und startet ein neues Spiel.
         * 
         * @param param    Parameter des Befehls
         * @param crtMatch Grundlegende Spieleigenschaften (Brett, nature, mission
         *                 control)
         */
        public void execute(Parameter param, CurrentMatch crtMatch) {
            crtMatch.reset();
            GameStructure.reset();
            Output.printOK();
        }
    },
    /**
     * Enum fuer den state-Befehl. Beinhaltet die String Schreibweise des Befehls
     * und die Ausfuehrung.
     */
    STATE("state") {
        /**
         * Gibt die Abkuerzng einer Figur eines einzelnen Feldes auf dem Brett aus.
         * 
         * @param param    Parameter des Befehls
         * @param crtMatch Grundlegende Spieleigenschaften (Brett, nature, mission
         *                 control)
         */
        public void execute(Parameter param, CurrentMatch crtMatch) {
            Token token = crtMatch.getBoard().getToken(param.getCoordinates().get(0));
            Output.state(token);
        }
    },
    /**
     * Enum fuer den set-vc-Befehl. Beinhaltet die String Schreibweise des Befehls
     * und die Ausfuehrung.
     */
    SET_VC("set-vc") {
        /**
         * Setzt entweder die Figur Vesta in nature und auf das board, wenn noch Phase 1
         * des Spieles ist, oder Ceres, wenn Phase 2 des Spieles ist.
         * 
         * @param param    Parameter des Befehls
         * @param crtMatch Grundlegende Spieleigenschaften (Brett, nature, mission
         *                 control)
         */
        public void execute(Parameter param, CurrentMatch crtMatch) {
            boolean setOne = crtMatch.getCrtSetOne();
            Nature nature = crtMatch.getNature();
            Board board = crtMatch.getBoard();
            List<Coordinate> coordinates = param.getCoordinates();

            if (setOne == true) {
                nature.setVesta(new VestaOrCeres(TokenType.VESTA, coordinates.get(0)));
                board.setToken(nature.getVesta(), coordinates.get(0));

            } else {
                nature.setCeres(new VestaOrCeres(TokenType.CERES, coordinates.get(0)));
                board.setToken(nature.getCeres(), coordinates.get(0));
            }
            Output.printOK();
        }
    },
    /**
     * Enum fuer den roll-Befehl. Beinhaltet die String Schreibweise des Befehls und
     * die Ausfuehrung.
     */
    ROLL("roll") {
        /**
         * Speichert die gewuerfelte Zahl in GameStructure.rolledCmd ab.
         * 
         * @param param    Parameter des Befehls
         * @param crtMatch Grundlegende Spieleigenschaften (Brett, nature, mission
         *                 control)
         */
        public void execute(Parameter param, CurrentMatch crtMatch) {
            Output.printOK();
            GameStructure.rolledCmd = param.getRolled();
        }
    },
    /**
     * Enum fuer den place-Befehl. Beinhaltet die String Schreibweise des Befehls
     * und die Ausfuehrung.
     */
    PLACE("place") {
        /**
         * Prueft ob die Laenge der gelegten Figur gleich einer der erlaubten Zahlen
         * ist. Wirft Exception falls nicht, sonst wird eine Dawn Figur, bei Laenge 7
         * gelegt, ansonsten eine normale Figur.
         * 
         * @param param    Parameter des Befehls
         * @param crtMatch Grundlegende Spieleigenschaften (Brett, nature, mission
         *                 control)
         * @throws IllegalArgumentException falls die Laenge der gelegten Figur ungleich
         *                                  der erlaubten Zahlen ist
         */
        public void execute(Parameter param, CurrentMatch crtMatch)
                throws IllegalArgumentException {
            boolean setOne = crtMatch.getCrtSetOne();
            MissionControl missionControl = crtMatch.getMissionControl();
            Board board = crtMatch.getBoard();
            List<Coordinate> coordinates = param.getCoordinates();
            List<Integer> numberList = missionControl.chooseNextNumber(setOne,
                    GameStructure.rolledCmd);

            // Pruefen ob die Laenge der gesetzten Figur gleich einer der erlaubten Zahlen
            // ist, welche gelegt werden duerfen.
            boolean throwException = true;
            int tmpLength = coordinates.get(0).getDistance(coordinates.get(1));
            for (int i = 0; i < numberList.size(); i++) {
                if (numberList.get(i) == tmpLength) {
                    throwException = false;
                }
            }
            if (throwException == true) {
                throw new IllegalArgumentException(
                        "allowed token number and number of set tokens is not equal!");
            }

            // Entweder Dawn Figur oder normale setzen und die Zahl der Laenge der Figur aus
            // dem Set von Mission Control loeschen
            if (tmpLength == 7) {
                board.setDAWN(coordinates.get(0), coordinates.get(1));
                missionControl.deleteFromSet(setOne, 7);
            } else {
                board.setToken(new Token(TokenType.WEAKER_DAWN), coordinates.get(0),
                        coordinates.get(1));
                missionControl.deleteFromSet(setOne, tmpLength);
            }
            Output.printOK();
            GameStructure.length = tmpLength;
        }
    },
    /**
     * Enum fuer den move-Befehl. Beinhaltet die String Schreibweise des Befehls und
     * die Ausfuehrung.
     */
    MOVE("move") {
        /**
         * Prueft ob die Koordinaten zum Bewegen der Figur valide sind und ob eine Feld,
         * auf das gerueckt werden soll, bereits belegt ist. Falls nicht wird entweder
         * vesta oder ceres um die angegebenen Koordinaten bewegt.
         * 
         * @param param    Parameter des Befehls
         * @param crtMatch Grundlegende Spieleigenschaften (Brett, nature, mission
         *                 control)
         * @throws IllegalArgumentException falls die Koordinaten nicht valide sind oder
         *                                  ein Feld auf das gerueckt weren soll bereits
         *                                  belegt ist.
         */
        public void execute(Parameter param, CurrentMatch crtMatch)
                throws IllegalArgumentException {
            boolean setOne = crtMatch.getCrtSetOne();
            Nature nature = crtMatch.getNature();
            Board board = crtMatch.getBoard();
            List<Coordinate> coordinates = param.getCoordinates();

            // Pruefe ob die angegebenen Koordinaten valide sind und ob der Befehl
            // ausgefuehrt werden darf.
            if (!setOne && nature.getCeresCoordinate() == null) {
                throw new IllegalArgumentException(
                        "command not allowed in this phase of the game!");
            }
            if (setOne) {
                if (!coordinates.get(0).neighbor(nature.getVestaCoordinate())) {
                    throw new IllegalArgumentException("illegal move!");
                }
            } else {
                if (!coordinates.get(0).neighbor(nature.getCeresCoordinate())) {
                    throw new IllegalArgumentException("illegal move!");
                }
            }
            if (GameStructure.length < coordinates.size()) {
                throw new IllegalArgumentException("too many moves!");
            }

            // Pruefe ob Felder, auf die gerueckt werden soll bereits belegt sind.
            for (int i = 0; i < coordinates.size(); i++) {
                if (setOne && !coordinates.get(i).equals(nature.getVestaCoordinate())
                        && board.isAlreadyOccupied(coordinates.get(i))) {
                    throw new IllegalArgumentException(
                            "at least one field in the path is already occupied!");
                } else if (!setOne && !coordinates.get(i).equals(nature.getCeresCoordinate())
                        && board.isAlreadyOccupied(coordinates.get(i))) {
                    throw new IllegalArgumentException(
                            "at least one field in the path is already occupied!");
                }
            }

            // Loesche die alte Koordinate vom Feld und setze die Neue in nature und auf das
            // Feld.
            if (setOne) {
                board.deleteToken(nature.getVestaCoordinate());
                nature.setVeresCoordinate(coordinates.get(coordinates.size() - 1));
                board.setToken(nature.getVesta(), coordinates.get(coordinates.size() - 1));
            } else {
                board.deleteToken(nature.getCeresCoordinate());
                nature.setCeresCoordinate(coordinates.get(coordinates.size() - 1));
                board.setToken(nature.getCeres(), coordinates.get(coordinates.size() - 1));
            }
            Output.printOK();
        }
    },
    /**
     * Enum fuer den quit-Befehl. Beinhaltet die String Schreibweise des Befehls und
     * die Ausfuehrung.
     */
    QUIT("quit") {
        /**
         * Setzt roundCounter auf -1, wodurch die Schleife, durch die das Spiel laeuft
         * beendet wird und das Programm terminiert.
         * 
         * @param param    Parameter des Befehls
         * @param crtMatch Grundlegende Spieleigenschaften (Brett, nature, mission
         *                 control)
         */
        public void execute(Parameter param, CurrentMatch crtMatch) {
            GameStructure.roundCounter = -1;
        }
    },
    /**
     * Enum fuer den Fall, dass ein eingegebener Befehl nicht existiert. Daher gibt
     * es auch keine Ausfuehrung des Befehls.
     */
    ILLEGAL_COMMAND("illegal") {
        /**
         * Fuer ILLEGAL_COMMAND gibt es keine Ausfuehrung.
         * 
         * @param param    Parameter des Befehls
         * @param crtMatch Grundlegende Spieleigenschaften (Brett, nature, mission
         *                 control)
         */
        public void execute(Parameter param, CurrentMatch crtMatch) {
            return;
        }
    };

    private String asString;

    /**
     * Setzt asString auf den Wert, der vom jeweiligen Enum uebergeben wird und
     * besagt, wie ein Enum als String dargestellt wird.
     * 
     * @param asString
     */
    private Command(String asString) {
        this.asString = asString;
    }

    /**
     * Gibt den jeweiligen String zurueck.
     * 
     * @return Enum als String
     */
    public String getString() {
        return asString;
    }

    /**
     * Iteriert ueber alle Enums und vergleicht, ob die jeweiligen String
     * Darstellungen der Enums mit dem uebergebenen Wert uebereinstimmen. Falls ja
     * wird dieses Enum zurueckgegeben, falls keins uebereinstimmt wird
     * ILLEGAL_COMMAND zurueckgegeben.
     * 
     * @param command String zu dem das passende Enum gesucht wird.
     * @return null, falls kein Enum gefunden wurde, sonst das passende Enum.
     */
    public static Command stringToEnum(String command) {
        Command toReturn = ILLEGAL_COMMAND;
        for (Command type : Command.values()) {
            if (type.getString().equals(command)) {
                toReturn = type;
            }
        }
        return toReturn;
    }

    /**
     * Abstrakte Methode, die die Ausfuehrung der einzelnen Befehle darstellen soll.
     * 
     * @param param    Parameter
     * @param crtMatch Grundlegende Informationen ueber das Spiel(Brett,
     *                 MissionControl, Nature, setOne oder setTwo)
     */
    public abstract void execute(Parameter param, CurrentMatch crtMatch);
}
