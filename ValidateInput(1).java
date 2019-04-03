package edu.kit.informatik;

import java.util.ArrayList;
import java.util.List;

/**
 * Ueberprueft ob das Foramt der Eingabe stimmt, also ob der Befehl existiert
 * und korrekt ist und die dazugehoerenden Parameter ebenfalls das zum Befehl
 * passende Format haben und korrekt sind.
 * 
 * @author Daniel
 * @version 2.0
 *
 */
public class ValidateInput {
    private Input input = new Input();
    private String rest = input.getRest();

    private Command command;
    private List<Coordinate> coordinates = new ArrayList<Coordinate>();
    private int rolled;

    /**
     * fuehrt validateCommand aus und belegt somit die command, coordinates oder
     * rolled.
     */
    public ValidateInput() {
        validateCommand();
    }

    /**
     * Prueft ob eine Eingabe (Befehl und Parameter) korrekt sind. Wirft eine
     * Exception wenn die Eingabe inkorrekt ist. Falls sie korrekt ist wird command
     * auf den aktuellen Befehl gesetzt, in coordinates werden die
     * Parameter-Koordinaten gespeichert und in rolled die gewuerfelte Zahl.
     * 
     * @throws IllegalArgumentException falls Eingabe falsches Format hat oder
     *                                  inkorrekt ist.
     */
    public void validateCommand() throws IllegalArgumentException {
        command = Command.stringToEnum(input.getCommand());

        switch (command) {
        case PRINT:
            checkNoParameter();
            break;
        case SHOW_RESULT:
            checkNoParameter();
            break;
        case RESET:
            checkNoParameter();
            break;
        case QUIT:
            checkNoParameter();
            break;
        case STATE:
            checkOneCoordinate();
            break;
        case SET_VC:
            checkOneCoordinate();
            break;
        case ROLL:
            checkRollParameter();
            break;
        case PLACE:
            checkTwoCoordinates();
            break;
        case MOVE:
            checkMultipleCoordinates();
            break;
        default:
            throw new IllegalArgumentException("command does not exist!");
        }
    }

    /**
     * Gibt die Koordinate zurueck.
     * 
     * @return Liste der Koordinaten.
     */
    public List<Coordinate> getCoordinates() {
        return coordinates;
    }

    /**
     * Gibt die gewuerfelte Zahl zurueck.
     * 
     * @return gewuerfelte Zahl als int.
     */
    public int getRolled() {
        return rolled;
    }

    /**
     * Gibt den Befehl zurueck.
     * 
     * @return Befehl als Command.
     */
    public Command getCommand() {
        return command;
    }

    /**
     * Prueft, ob das Format des roll Befehls und dessen Parameter korrekt ist.
     * Falls ja werden die Parameter gesetzt, falls nicht Exception geworfen.
     * 
     * @throws IllegalArgumentException bei inkorrekter Eingabe des roll Befehls.
     */
    private void checkRollParameter() throws IllegalArgumentException {
        moreThanOneBlankException();
        try {
            if (rest.equals("DAWN")) {
                rolled = 7;
            } else {
                int rolledInput = Integer.valueOf(rest);
                if (rolledInput < 2 || rolledInput > 6) {
                    throw new IllegalArgumentException("illegal parameter!");
                }
                rolled = rolledInput;
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("illegal argument!");
        }
    }

    /**
     * Prueft, ob das Format der Eingabe korrekt ist, wenn kein Parameter erwartet
     * wird.
     * 
     * @throws IllegalArgumentException falls Format falsch ist.
     */
    private void checkNoParameter() throws IllegalArgumentException {
        if (input.containsBlank()) {
            throw new IllegalArgumentException("command does not exist or illegal parameter!");
        }
    }

    /**
     * Prueft ob das Format der Eingabe korrekt ist, wenn mindestens eine und
     * maximal 7 Koordinaten als Parameter erwartet werden.
     * 
     * @throws IllegalArgumentException
     */
    private void checkMultipleCoordinates() throws IllegalArgumentException {
        moreThanOneBlankException();
        String[] splittedCoordinates = rest.split(":");
        if (splittedCoordinates.length > 7) {
            throw new IllegalArgumentException("too many moves!");
        }
        int row;
        int column;
        int nextRow = 0;
        int nextColumn = 0;

        // Fuer den Fall, dass es nur eine Koordinate gibt, wird jetzt schon versucht
        // nextRow und nextColumn mit den Werten der Koordinate zu belegen, da die
        // Schleife dann uebersprungen wird.
        try {
            nextRow = Integer.valueOf(splittedCoordinates[0].split(";")[0]);
            nextColumn = Integer.valueOf(splittedCoordinates[0].split(";")[1]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("illegal parameter!");
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new IllegalArgumentException("illegal parameter!");
        }

        // mehrere Koordinaten werden auf Korrektheit geprueft und ob zwei
        // aufeinanderfolgende Koordinaten benachbart sind.
        for (int i = 0; i < splittedCoordinates.length - 1; i++) {
            String[] singleCoordinate = splittedCoordinates[i].split(";");
            String[] nextSingleCoordinate = new String[0];
            nextSingleCoordinate = splittedCoordinates[i + 1].split(";");

            try {
                row = Integer.valueOf(singleCoordinate[0]);
                column = Integer.valueOf(singleCoordinate[1]);
                nextRow = Integer.valueOf(nextSingleCoordinate[0]);
                nextColumn = Integer.valueOf(nextSingleCoordinate[1]);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("illegal parameter!");
            }

            Coordinate currentCoord = new Coordinate(row, column);
            Coordinate nextCoord = new Coordinate(nextRow, nextColumn);
            if (!currentCoord.neighbor(nextCoord)) {
                throw new IllegalArgumentException("moves not exactly one field apart!");
            }
            coordinates.add(new Coordinate(row, column));
        }
        coordinates.add(new Coordinate(nextRow, nextColumn));
    }

    /**
     * Prueft, ob das Format der Eingabe korrekt ist, wenn zwei Koordinaten als
     * Parameter erwartet werden und ob entweder die Zeilen oder Spalten gleich
     * sind.
     * 
     * @throws IllegalArgumentException falls Format falsch ist.
     */
    private void checkTwoCoordinates() throws IllegalArgumentException {
        moreThanOneBlankException();
        if ((rest.split(":").length != 2) || (rest.split(";").length != 3)) {
            throw new IllegalArgumentException("illegal parameter!");
        }
        int rowOne;
        int columnOne;
        int rowTwo;
        int columnTwo;

        // Versuche die Parameter der Eingabe in zwei Koordinaten aufzuteilen
        try {
            String coordinateOne = rest.split(":")[0];
            String coordinateTwo = rest.split(":")[1];
            rowOne = Integer.valueOf(coordinateOne.split(";")[0]);
            columnOne = Integer.valueOf(coordinateOne.split(";")[1]);
            rowTwo = Integer.valueOf(coordinateTwo.split(";")[0]);
            columnTwo = Integer.valueOf(coordinateTwo.split(";")[1]);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new IllegalArgumentException("illegal parameter!");
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("illegal parameter!");
        }

        // Speichert die Koordinaten, gegebenenfalls als Dawn, ab
        Coordinate coordinateOne = null;
        Coordinate coordinateTwo = null;
        if (isDawn(rowOne, columnOne, rowTwo, columnTwo)) {
            coordinateOne = new CoordinateDawn(rowOne, columnOne);
            coordinateTwo = new CoordinateDawn(rowTwo, columnTwo);
        } else {
            coordinateOne = new Coordinate(rowOne, columnOne);
            coordinateTwo = new Coordinate(rowTwo, columnTwo);
        }
        coordinates.add(coordinateOne);
        coordinates.add(coordinateTwo);

    }

    /**
     * Prueft, ob zwei Koordinaten 7 Felder in vertikaler oder horizontaler Richtung
     * auseinander liegen und somit ein Dawn vorliegt. Dabei wird noch geprueft, ob
     * zwei Zeilen oder zwei Spalten gleich sind, wenn nicht wird eine Exception
     * geworfen.
     * 
     * @param rowOne    erste Zeile
     * @param columnOne erste Spalte
     * @param rowTwo    zweite Zeile
     * @param columnTwo zweite Spalte
     * @return true, falls die Koordinaten 7 Felder aueinander liegen, sonst false
     */
    private boolean isDawn(int rowOne, int columnOne, int rowTwo, int columnTwo) {
        int rowOneCge = rowOne;
        int columnOneCge = columnOne;
        int rowTwoCge = rowTwo;
        int columnTwoCge = columnTwo;
        if (rowOne == rowTwo) {
            if (columnOneCge > columnTwoCge) {
                int tmp = columnOneCge;
                columnOneCge = columnTwoCge;
                columnTwoCge = tmp;
            }
            if (columnTwoCge - columnOneCge == 6) {
                return true;
            }
        } else if (columnOneCge == columnTwoCge) {
            if (rowOneCge > rowTwoCge) {
                int tmp = rowOneCge;
                rowOneCge = rowTwoCge;
                rowTwoCge = tmp;
            }
            if (rowTwoCge - rowOneCge == 6) {
                return true;
            }
        } else {
            throw new IllegalArgumentException("either rows or columns must be equal!");
        }
        return false;
    }

    /**
     * Prueft, ob das Format der Eingabe korrekt ist, wenn eine Koordinate als
     * Parameter erwartet wird.
     * 
     * @throws IllegalArgumentException falls Format falsch ist.
     */
    private void checkOneCoordinate() throws IllegalArgumentException {
        moreThanOneBlankException();
        if (rest.split(";").length > 2) {
            throw new IllegalArgumentException("illegal parameter!");
        }

        // Versuche die Parameter der Eingabe in eine Koordinate aufzuteilen.
        try {
            int row = Integer.valueOf(rest.split(";")[0]);
            int column = Integer.valueOf(rest.split(";")[1]);
            coordinates.add(new Coordinate(row, column));
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new IllegalArgumentException("illegal parameter!");
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("illegal parameter!");
        }
    }

    /**
     * Wirft Excpetion, falls der input mehr als ein Leerzeichen enthaelt.
     * 
     * @throws IllegalArgumentException falls Input mehr als ein Leerzeichen
     *                                  enthaelt.
     */
    private void moreThanOneBlankException() throws IllegalArgumentException {
        if (input.containsMoreThanOneBlank()) {
            throw new IllegalArgumentException("too many blanks, wrong format!");
        }
    }
}
