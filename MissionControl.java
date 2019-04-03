package edu.kit.informatik;

import java.util.ArrayList;
import java.util.List;

/**
 * Modelliert den Spieler Mission Control und dessen zwei Sets Spielfiguren.
 * 
 * @author Daniel
 * @version 2.0
 *
 */
public class MissionControl {
    private Integer[] setOne = new Integer[6];
    private Integer[] setTwo = new Integer[6];

    /**
     * Befuellt die zwei arrays jeweils mit den möglichen Wuerfelzahlen.
     */
    public MissionControl() {
        for (int i = 0; i < 6; i++) {
            setOne[i] = i + 2;
            setTwo[i] = i + 2;
        }
    }

    /**
     * Setzt Mission Control zurueck. Fuellt die zwei Sets also wieder mit allen
     * möglichen Wuerfelzahlen auf.
     */
    public void reset() {
        for (int i = 0; i < 6; i++) {
            setOne[i] = i + 2;
            setTwo[i] = i + 2;
        }
    }

    /**
     * Schreibt 0 an die Stelle im array setOne oder setTwo, an der vorher die
     * uebergebene Zahl number stand. setOne wenn currentlySetOne true ist, sonst
     * setTwo.
     * 
     * @param currentlySetOne Legt fest, ob von setOne oder setTwo geloescht weren
     *                        soll. setOne wenn currentlySetOne true, sonst setTwo.
     * @param number          Die Zahl, die geloescht werden soll.
     */
    public void deleteFromSet(boolean currentlySetOne, int number) {
        if (currentlySetOne == true) {
            setOne[number - 2] = 0;
        } else {
            setTwo[number - 2] = 0;
        }
    }

    /**
     * Findet die Zahlen in setOne oder setTwo, die am nächsten an der uebergebenen
     * Zahl number sind. Diese Zahl, wenn die Zahl number selbst noch im array ist,
     * oder gegebenenfalls die naechsten Zahlen werden dann in einer Liste
     * zurueckgegeben. Fuer currentlySetOne == true wird in setOne gesucht, sonst in
     * setTwo.
     * 
     * @param currentlySetOne true falls in setOne gesucht werden soll, sonst in
     *                        setTwo.
     * @param number          Zahl, von der aus die naechste Zahl gesucht werden
     *                        soll.
     * @return Liste von Zahlen die am naechsten an der uebergebenen Zahl sind.
     */
    public List<Integer> chooseNextNumber(boolean currentlySetOne, int number) {
        int arrayIndex = number - 2;
        List<Integer> numberList = new ArrayList<Integer>();

        // Fuer Set eins
        // Falls die Zahl im Array ist.
        if (currentlySetOne) {
            if (setOne[arrayIndex] == number) {
                numberList.add(number);
                return numberList;
            }

            // Falls die Zahl nicht im Array ist.
            for (int i = 1; i < 7; i++) {
                boolean foundFirst = false;
                boolean foundSecond = false;
                if (!foundFirst && arrayIndex - i >= 0 && setOne[arrayIndex - i] != 0) {
                    numberList.add(number - i);
                    foundFirst = true;
                }
                if (!foundSecond && arrayIndex + i < 6 && setOne[arrayIndex + i] != 0) {
                    numberList.add(number + i);
                    foundSecond = true;
                }
                if (foundFirst == true && foundSecond == true) {
                    break;
                }
            }
            return numberList;
        }

        // Fuer Set zwei
        // Falls die Zahl im Array ist.
        if (setTwo[arrayIndex] == number) {
            numberList.add(number);
            return numberList;
        }

        // Falls die Zahl nicht im Array ist.
        for (int i = 1; i < 7; i++) {
            boolean foundFirst = false;
            boolean foundSecond = false;
            if (!foundFirst && arrayIndex - i >= 0 && setTwo[arrayIndex - i] != 0) {
                numberList.add(number - i);
                foundFirst = true;
            }
            if (!foundSecond && arrayIndex + i < 6 && setTwo[arrayIndex + i] != 0) {
                numberList.add(number + i);
                foundSecond = true;
            }
            if (foundFirst == true && foundSecond == true) {
                break;
            }
        }
        return numberList;
    }
}
