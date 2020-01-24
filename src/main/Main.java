package main;

/**
 * Liam Estimos and Kevin Borja
 * A program that plays monopoly and battleships
 * June 16, 2017
 */

import java.util.*;

public class Main {
    public static void main(String[] args) {
        choice();
    }

    //Ask player what game he wants to play
    public static void choice() {
        Scanner scan = new Scanner(System.in);
        String input = "";

        do {
            System.out.println("Welcome to board game simulator");
            System.out.println("Type \"Monopoly\" if you want to play Monopoly.");
            System.out.println("Type \"BattleShip\" if you want to play BattleShip");
            input = scan.nextLine();

            if (input.equalsIgnoreCase("Monopoly"))
                Monopoly.main(null);
            else if (input.equalsIgnoreCase("BattleShip"))
                Battleship.main(null);

        } while(!input.equalsIgnoreCase("Monopoly") && !input.equalsIgnoreCase("Battleship"));
    }
}
