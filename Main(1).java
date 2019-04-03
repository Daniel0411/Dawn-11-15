package edu.kit.informatik;

public class Main {
    /**
     * Main Methode die das Programm und das Spiel startet.
     * 
     * @param args 
     */
    public static void main(String[] args) {

        GameStructure structure = new GameStructure();

        // So lange die Rundenzahl des aktuellen Spiels nicht -1 ist, wird das Spiel
        // ausgefuehrt.
        while (GameStructure.roundCounter >= 0) {
            structure.dawn11x15();
        }
    }
}
