package edu.kit.informatik;

/**
 * Alle Ausgaben des Programms werden ausschliesslich ueber diese Klasse
 * gemacht.
 * 
 * @author Daniel
 * @version 2.0
 *
 */
public class Output {

    /**
     * gibt "OK" aus.
     */
    public static void printOK() {
        Terminal.printLine("OK");
    }

    /**
     * Gibt das uebergebene Ergebnis aus.
     * 
     * @param result Ergebnis
     */
    public static void printResult(String result) {
        Terminal.printLine(result);
    }

    /**
     * Gibt eine Fehlermeldung beginnend mit "Error, " aus.
     * 
     * @param errorMessage Fehlermeldung
     */
    public static void printError(String errorMessage) {
        Terminal.printError(errorMessage);
    }

    /**
     * Gibt das Spielbrett aus.
     * 
     * @param board Spielbrett
     */
    public static void printBoard(Board board) {
        for (int i = 0; i < 11; i++) {
            String currentRow = "";
            for (int j = 0; j < 15; j++) {
                Token currentField = board.getBoard()[i][j];
                if (!board.isAlreadyOccupied(i, j)) {
                    currentRow += "-";
                } else {
                    currentRow += currentField.getTokenType().getShortcut();
                }
            }
            Terminal.printLine(currentRow);
        }
    }

    /**
     * Gibt die Abkuerzung einer Figur aus (Vesta: V, Ceres: C, mission control: +
     * und null: -)
     * 
     * @param token Figur dessen Abkuerzung ausgegeben werden soll.
     */
    public static void state(Token token) {
        if (token == null) {
            Terminal.printLine("-");
        } else {
            Terminal.printLine(token.getTokenType().getShortcut());
        }
    }
}
