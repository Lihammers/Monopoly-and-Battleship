package main;

import java.util.*;

public class Battleship {
    public static Scanner scan = new Scanner(System.in);
    private static int lose = 0, len = 0, boats = 0, win = 0;
    private static String cpuName = "";
    public static Random rand = new Random();

    public static void main(String[] args) {
        battleShip();
    }

    public static void battleShip()
    {
        System.out.println("====================================================================");
        System.out.println("                       Welcome to Battleship");
        System.out.println("====================================================================");
        System.out.println("The goal of this game is to destroy all ships hidden on the board before the CPU does.");

        space();

        //Begins Game
        user();
    }

    //Prints out a space between text lines
    public static void space()
    {
        System.out.println("");
    }

    //Prints out a line break
    public static void lineBreak()
    {
        System.out.println("____________________________________________________________________________________");
        System.out.println("");
    }

    //Method for User's turn
    public static void user()
    {
        //Declare Variables
        int count1 = 1, count2 = 1;

        //Ask user to name CPU
        System.out.println("Please enter a name for the CPU");
        cpuName = scan.nextLine();
        //Ask user to input game board dimensions
        do
        {
            System.out.println("Please enter a board size between 4-9");
            len = scan.nextInt();
        } while (len > 9 || len < 4);

        space();

        //Checks number of boats depending on board size
        if(len == 4)
        {
            boats = 1;
        }
        else if(len == 5)
        {
            boats = 2;
        }
        else if(len == 6)
        {
            boats = 3;
        }
        else if(len == 7)
        {
            boats = 4;
        }
        else if(len == 8)
        {
            boats = 5;
        }
        else if(len == 9)
        {
            boats = 6;
        }

        //Sets how much boats the CPU needs to sink to win
        lose = boats;

        //Creates 2D array that will draw game board
        char[][] grid = new char[len][len];

        System.out.println("This is the game board where you will enter coordiantes to sink ships");
        lineBreak();

        //Draws game board
        System.out.print(" ");
        for(int r = 0; r < len; r++)
        {
            System.out.print(" " + count1);
            count1++;
        }
        space();
        for (int row = 0; row < grid.length; row++)
        {
            System.out.print(count2 + "|");
            count2++;
            for (int col = 0; col < grid.length; col++)
            {

                grid[row][col] = 'O';
                System.out.print((grid[row][col]) + "|" );
            }
            space();
        }
        count2 = 1;
        lineBreak();

        //Run method for user's shot
        userShot();
    }

    //shot
    public static void userShot()
    {
        //Declare Variables
        int shotX, shotY, count1 = 1, count2 = 1, cheat, boatx = 99, boaty = 99, boatValid = 0, direction, boatCount = 0;

        //Create 2D array that will store ship location on board
        int[][]board = new int[len][len];

        //Creates 2D array to draw board
        char[][] grid = new char[len][len];

        //Set values of board to an empty spot
        for (int y = 0; y < board.length; y++)
        {
            for (int x = 0; x < board.length; x++)
            {
                board[y][x] = 1;
            }
        }

        //Places ships on the board, will loop until number of ships required is met
        do
        {
            //Randomly generate whether the boat will be placed horizontal or vertical
            direction = rand.nextInt(2);

            //Resets value
            boatValid = 0;

            //For vertical ships
            if (direction == 0)
            {
                do
                {
                    //Generate random x and y coordinates
                    boatx = rand.nextInt(len);
                    boaty = rand.nextInt(len - 3);

                    //Place ship
                    for(int i = 0; i < 3; i++)
                    {
                        //If ship overlaps with another, find new spot to place ship
                        if(board[boaty + i][boatx] == 0)
                        {
                            if(i == 0)
                            {
                                boatValid = 1;
                                break;
                            }
                            else if(i == 1)
                            {
                                //Revert changed values of invalid ship back to empty
                                board[boaty][boatx] = 1;
                                boatValid = 1;
                                break;
                            }

                            else if(i == 2)
                            {
                                //Revert changed values of invalid ship back to empty
                                board[boaty][boatx] = 1;
                                board[boaty + 1][boatx] = 1;
                                boatValid = 1;
                                break;
                            }
                        }
                        //If ship placement is valid, place ship
                        else
                        {
                            board[boaty + i][boatx] = 0;
                            boatValid = 0;
                        }
                    }
                }while(boatValid == 1);
            }

            //For horizontal ships
            else if (direction == 1)
            {
                do
                {
                    //generate random x and y coordinate
                    boatx = rand.nextInt(len - 3);
                    boaty = rand.nextInt(len );

                    //Place ship
                    for(int i = 0; i < 3; i++)
                    {
                        //If ship overlaps with another, find new spot to place ship
                        if(board[boaty][boatx + i] == 0)
                        {
                            if(i == 0)
                            {
                                boatValid = 1;
                                break;
                            }
                            else if(i == 1)
                            {
                                //Revert changed values of invalid ship back to empty
                                board[boaty][boatx] = 1;
                                boatValid = 1;
                                break;
                            }

                            else if(i == 2)
                            {
                                //Revert changed values of invalid ship back to empty
                                board[boaty][boatx] = 1;
                                board[boaty][boatx + 1] = 1;
                                boatValid = 1;
                                break;
                            }
                        }

                        //If ship placement is valid, place ship part
                        else
                        {
                            board[boaty][boatx + i] = 0;
                            boatValid = 0;
                        }

                    }

                }while(boatValid == 1);
            }
            //Increase boat count by one
            boatCount++;
        }while (boatCount < boats);




        //Determine number of parts of ships on board that needs to be destroyed to win
        for(int j = 0; j < len; j++)
        {
            for (int k = 0; k < len; k++)
            {
                if (board[j][k] == 0)
                {
                    win += 1;
                }
            }
        }

        space();
        System.out.println("You must destroy " + boats + " ships to win");
        space();

        //Ask user if they want to enable cheat
        do
        {
            System.err.println("Do you want to enable the cheat? 1 - No  2 - Yes");
            cheat = scan.nextInt();
            space();

            //Enable cheat
            if (cheat == 2)
            {
                System.out.println("This is board shows the positions of the boats");
                System.out.println("Each 0 is a boat, and each 1 is an empty space");
                space();
                lineBreak();
                //Prints out cheat board
                for(int j = 0; j < len; j++)
                {
                    for (int k = 0; k < len; k++)
                    {
                        System.out.print("|" + board[j][k]);
                    }
                    System.out.println("|");

                }
                lineBreak();
            }
        }while (cheat > 2 || cheat < 1);

        //Sets game board to blank
        for (int i = 0; i <len; i++)
        {
            for (int y = 0; y < len; y++)
            {
                grid[i][y] = 'O';
            }
        }

        //Loop to execute user's turn
        while(true)
        {
            //Ask user for X coordinates, repeat if input is invalid
            do
            {
                space();
                System.out.println("Please enter in the x-coordinate between 1 and " + len);
                shotX= scan.nextInt();

            }while(shotX > len || shotX < 0);

            //Ask user for Y coordinates, repeat if input is invalid
            do
            {
                System.out.println("Please enter in the y-coordinate between 1 and " + len);
                shotY = scan.nextInt();

            }while(shotY > len || shotY < 0);

            //Check position of coordinates for a hit or miss
            if(board[shotY - 1][shotX- 1] == 0)
            {
                //Tell user that a part of a ship was hit
                space();
                System.out.println("HIT!");

                //Set shot value to -1 to make sure user does not reshoot spot
                board[shotY - 1][shotX - 1] = -1;

                //Reduce number of parts of ships needed to be sunk by one
                win -= 1;
                System.out.println("You have " + win + " parts of ships left to destroy.");
                space();

                //Checks if all boats are destroyed for user win
                if(win == 0)
                {
                    lineBreak();
                    //Draw board numbering
                    System.out.print(" ");
                    for(int r = 0; r < len; r++)
                    {
                        System.out.print(" " + count1);
                        count1++;
                    }
                    count1 = 1;
                    space();

                    //Draw game board
                    for (int row = 0; row < grid.length; row++)
                    {
                        System.out.print(count2 + " ");
                        count2++;

                        for (int col = 0; col < grid.length; col++)
                        {
                            grid[shotY - 1][shotX - 1] = 'X';
                            System.out.print((grid[row][col]) + " " );
                        }
                        space();
                    }
                    count2 = 1;
                    lineBreak();

                    //Execute user's win
                    win();
                }

                //If ships still remain, continue user's turn
                //Draw numbering of board
                lineBreak();
                System.out.print(" ");
                for(int r = 0; r < len; r++)
                {
                    System.out.print(" " + count1);
                    count1++;
                }
                count1 = 1;
                space();

                //Draw game board
                for (int row = 0; row < grid.length; row++)
                {
                    System.out.print(count2 + "|");
                    count2++;

                    for (int col = 0; col < grid.length; col++)
                    {
                        //Shows where user hit
                        grid[shotY - 1][shotX - 1] = 'X';
                        System.out.print((grid[row][col]) + "|" );
                    }
                    space();
                }
                count2 = 1;
                lineBreak();
            }

            //Checks if user already shot at location and will make user choose new location
            else if(board[shotY - 1][shotX - 1] == -1)
            {
                space();
                System.out.println("You already shot there. Try again.");
                space();

                //Draw numbering of board
                lineBreak();
                System.out.print(" ");
                for(int r = 0; r < len; r++)
                {
                    System.out.print(" " + count1);
                    count1++;
                }

                count1 = 1;
                space();

                //Draw game board
                for (int row = 0; row < grid.length; row++)
                {
                    System.out.print(count2 + "|");
                    count2++;

                    for (int col = 0; col < grid.length; col++)
                    {
                        System.out.print((grid[row][col]) + "|" );
                    }
                    space();
                }
                count2 = 1;
                lineBreak();
            }

            //Checks if user did not hit a boat
            else
            {
                space();
                System.out.println("MISS!");
                System.out.println("You still have " + win + " parts of ships left to destroy.");
                space();

                //Set shot value to -1 to make sure user does not reshoot spot
                board[shotY - 1][shotX- 1] = -1;

                //Draw numbering of board
                lineBreak();
                System.out.print(" ");
                for(int r = 0; r < len; r++)
                {
                    System.out.print(" " + count1);
                    count1++;
                }
                count1 = 1;
                space();

                //Draw game board
                for (int row = 0; row < grid.length; row++)
                {
                    System.out.print(count2 + "|");
                    count2++;
                    for (int col = 0; col < grid.length; col++)
                    {
                        //Shows where user missed
                        grid[shotY-1][shotX-1] = 'M';

                        System.out.print((grid[row][col]) + "|" );
                    }
                    space();
                }
                count2 = 1;
                lineBreak();

                space();

                //End of user's turn, CPU's turn start
                System.err.println(cpuName + "'s Turn");
                cpuShot();

            }
        }

    }

    //Executes CPU's shot
    public static void cpuShot()
    {
        //Declare variables
        int shot;


        //Loop to execute CPU's shot
        while(true)
        {
            //Randomly generate number, 25% chance that CPU sinks a boat
            shot = rand.nextInt(4);

            //Check if shot hit a boat
            if (shot == 0)
            {
                lose -= 1;
                System.err.println(cpuName + " sunk a ship!");

                //Check if the CPU wins
                if (lose == 0)
                {
                    System.err.println(cpuName + " sunk all their ships!");
                    //Execute user's loss
                    loser();
                }
                else
                {
                    //If CPU does not win, continue turn
                    System.err.println(cpuName + " has to sink " + lose + " more ships");
                    System.err.println(cpuName + " goes again");
                }
            }

            //Check if CPU missed, will be user's turn next
            else
            {
                System.err.println(cpuName + " MISSED");
                System.err.println("User's Turn");
                return;
                //  break;
            }

        }
    }

    //Method for end of game if user lost
    public static void loser()
    {
        //Declare variable
        int play = 0;

        //Print out to user that they lost
        space();
        System.err.println("You lose");
        System.err.println("Game Over");

        //Ask if user wants to play again
        do
        {
            System.err.println("Do you want to play again? 1 - Yes  2 - No");
            play = scan.nextInt();

        }while(play < 1 || play > 2);

        //If the user wants to play again, start user's turn
        if (play == 1)
        {
            space();
            user();
        }

        //Ends program
        else
        {
            System.err.println("End of Program");
            System.exit(0);
        }
    }

    //Method for end of game if user wins
    public static void win()
    {
        //Declare Variable
        int play = 0;

        space();
        //Tell user they won
        System.err.println("You Win");
        System.err.println("Game Over");

        //Ask if user wants to play again
        do
        {
            System.err.println("Do you want to play again? 1 - Yes  2 - No");
            play = scan.nextInt();

        }while(play < 1 || play > 2);

        //If the user wants to play again, start user's turn
        if (play == 1)
        {
            space();
            user();
        }

        //Ends program
        else
        {
            System.err.println("End of Program");
            Monopoly.time();
            Monopoly.time();

            System.exit(0);

        }
    }

}
