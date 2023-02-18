package battleship;

import java.util.Scanner;

public class Main {
    static final Scanner sc = new Scanner(System.in);
    static String currentPlayer = "Player 1";
    public static void main(String[] args) {
        Field field1 = new Field();
        Field field2 = new Field();

        System.out.println(currentPlayer + ", place your ships to the game field");
        field1.printField();
        for (Ship sh : Ship.values()) {
            field1.setShip(sh);
            field1.printField();
        }
        changePlayer();

        System.out.println(currentPlayer + ", place your ships to the game field");
        field2.printField();
        for (Ship sh : Ship.values()) {
            field2.setShip(sh);
            field2.printField();
        }
        changePlayer();

        System.out.println("\nThe game starts!\n");
        while(!field1.isGameOver() && !field2.isGameOver()){
            System.out.println(currentPlayer + ", it's your turn:");
            if (currentPlayer.equals("Player 1")) {
                field2.printFakeField();
                System.out.println("---------------------");
                field1.printField();
                field2.TakeShot(sc);
            }
            else {
                field1.printFakeField();
                System.out.println("---------------------");
                field2.printField();
                field1.TakeShot(sc);
            }
            if (!field1.isGameOver() && !field2.isGameOver()) {
                changePlayer();
            }
        }
        sc.close();
    }

    public static void changePlayer(){
        System.out.println("Press Enter and pass the move to another player");
        sc.nextLine();
        currentPlayer = currentPlayer.equals("Player 1") ? "Player 2" : "Player 1";
    }
}