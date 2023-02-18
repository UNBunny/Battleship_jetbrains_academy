package battleship;

import java.nio.charset.CharsetEncoder;
import java.util.Arrays;
import java.util.Scanner;

public class Field {
    Scanner sc = new Scanner(System.in);
    private char[][] field;
    private char[][] fakeField;

    public Field() {
        field = new char[10][10];
        fakeField = new char[10][10];
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                field[i][j] = '~';
                fakeField[i][j] = '~';
            }
        }
    }

    public void setShip(Ship ship) {
        int[] coord = null;
        boolean isCoordValid = false;
        System.out.printf("Enter the coordinates of the %s (%d cells):\n",
                ship.getPrintName(), ship.getLength());
        while (!isCoordValid) {
            coord = toDigitCoord(sc.nextLine(), 2);
            try {
                isCoordValid = checkCoord(ship, coord);
            } catch (IllegalAccessException e) {
                System.out.println(e.getMessage() + " Try again:");
                continue;
            }
            for (int i = Math.min(coord[0], coord[2]); i <= Math.max(coord[0], coord[2]); i++) {
                for (int j = Math.min(coord[1], coord[3]); j <= Math.max(coord[1], coord[3]); j++) {
                    field[i][j] = 'O';
                }
            }
        }
    }

    public void TakeShot(Scanner sc) {
        System.out.println("\nTake a shot!\n");
        int[] coord = null;
        do {
            coord = toDigitCoord(sc.nextLine(), 1);
            try {
                if (field[coord[0]][coord[1]] == 'O') {
                    field[coord[0]][coord[1]] = 'X';
                    fakeField[coord[0]][coord[1]] = 'X';
                    if (checkGameOver()) {
                        printFakeField();
                        System.out.println("You sank the last ship. You won. Congratulations!");
                        return;
                    } else if (!checkSankShip(coord[0], coord[1])) {
                        printFakeField();
                        System.out.println("You sank a ship!");
                        break;
                    } else {
                        printFakeField();
                        System.out.println("You hit a ship!");
                        break;
                    }
                } else if (field[coord[0]][coord[1]] == 'X') {
                    printFakeField();
                    System.out.println("You hit a ship!");
                    break;
                } else {
                    field[coord[0]][coord[1]] = 'M';
                    fakeField[coord[0]][coord[1]] = 'M';
                    printFakeField();
                    System.out.println("You missed!");
                    break;
                }
//                field[coordinates[0]][coordinates[1]].setFog(false);
                //print();
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Error! You entered the wrong coordinates! Try again: ");
            }
        } while (true);
    }

    private int[] toDigitCoord(String coordinates, int countCoord) {
        int[] result = new int[countCoord * 2];
        if (countCoord == 1) {
            result[0] = coordinates.split(" ")[0].charAt(0) - 65;
            result[1] = Integer.parseInt(coordinates.split(" ")[0].substring(1)) - 1;
        }
        if (countCoord == 2) {
            result[0] = coordinates.split(" ")[0].charAt(0) - 65;
            result[1] = Integer.parseInt(coordinates.split(" ")[0].substring(1)) - 1;
            result[2] = coordinates.split(" ")[1].charAt(0) - 65;
            result[3] = Integer.parseInt(coordinates.split(" ")[1].substring(1)) - 1;
        }
        return result;
    }

    private boolean checkCoord(Ship ship, int[] coordinates) throws IllegalAccessException {
        boolean isValid = true;
        if ((coordinates[0] == coordinates[2]) == (coordinates[1] == coordinates[3])) {
            throw new IllegalAccessException("Error! Wrong ship location!");
        } else if (Math.abs((coordinates[0] - coordinates[2]) - (coordinates[1] - coordinates[3])) + 1 != ship.getLength()) {
            throw new IllegalAccessException("Error! Wrong length of the " + ship.getPrintName() + "!");
        }
        for (int i = Math.min(coordinates[0], coordinates[2]); i <= Math.max(coordinates[0], coordinates[2]); i++) {
            for (int j = Math.min(coordinates[1], coordinates[3]); j <= Math.max(coordinates[1], coordinates[3]); j++) {
                if (field[i][j] == 'O') {
                    throw new IllegalAccessException("Error! You placed it too close to another one.");
                }
                if (j > 0) {
                    if (field[i][j - 1] == 'O') {
                        throw new IllegalAccessException("Error! You placed it too close to another one.");
                    }
                    if (i > 0) {
                        if (field[i - 1][j] == 'O') {
                            throw new IllegalAccessException("Error! You placed it too close to another one.");
                        }
                        if (field[i - 1][j - 1] == 'O') {
                            throw new IllegalAccessException("Error! You placed it too close to another one.");
                        }
                    }
                    if (i < 9) {
                        if (field[i + 1][j] == 'O') {
                            throw new IllegalAccessException("Error! You placed it too close to another one.");
                        }
                        if (field[i + 1][j - 1] == 'O') {
                            throw new IllegalAccessException("Error! You placed it too close to another one.");
                        }
                    }
                }
                if (j < 9) {
                    if (field[i][j + 1] == 'O') {
                        throw new IllegalAccessException("Error! You placed it too close to another one.");
                    }
                    if (i > 0) {
                        if (field[i - 1][j] == 'O') {
                            throw new IllegalAccessException("Error! You placed it too close to another one.");
                        }
                        if (field[i - 1][j + 1] == 'O') {
                            throw new IllegalAccessException("Error! You placed it too close to another one.");
                        }
                    }
                    if (i < 9) {
                        if (field[i + 1][j] == 'O') {
                            throw new IllegalAccessException("Error! You placed it too close to another one.");
                        }
                        if (field[i + 1][j + 1] == 'O') {
                            throw new IllegalAccessException("Error! You placed it too close to another one.");
                        }
                    }
                }
            }
        }
        return isValid;
    }

    private boolean checkSankShip(int coord1, int coord2) {
        for (int i = coord1 - 1; i > 0 && field[i][coord2] != '~' &&
                field[i][coord2] != 'M'; i--) {
            if (field[i][coord2] == 'X') {
                return false;
            }
        }
        for (int i = coord1 + 1; i < 9 && field[i][coord2] != '~' &&
                field[i][coord2] != 'M'; i++) {
            if (field[i][coord2] == 'X') {
                return false;
            }
        }
        for (int i = coord2 - 1; i > 0 && field[coord1][i] != '~' &&
                field[coord1][i] != 'M'; i--) {
            if (field[coord1][i] == 'X') {
                return false;
            }
        }
        for (int i = coord2 + 1; i < 9 && field[coord1][i] != '~' &&
                field[coord1][i] != 'M'; i++) {
            if (field[coord1][i] == 'X') {
                return false;
            }
        }

        return true;
    }


    private boolean checkGameOver() {
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                if (field[i][j] == 'O') {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isGameOver() {
        return checkGameOver();
    }

    public void printField() {
        char letter = 'A';
        System.out.println("  1 2 3 4 5 6 7 8 9 10");
        for (int i = 0; i < field.length; i++) {
            System.out.print(letter++);
            for (int j = 0; j < field[i].length; j++) {
                System.out.print(" " + field[i][j]);
            }
            System.out.println();
        }
    }

    public void printFakeField() {
        char letter = 'A';
        System.out.println("  1 2 3 4 5 6 7 8 9 10");
        for (int i = 0; i < fakeField.length; i++) {
            System.out.print(letter++);
            for (int j = 0; j < fakeField[i].length; j++) {
                System.out.print(" " + fakeField[i][j]);
            }
            System.out.println();
        }
    }
}
