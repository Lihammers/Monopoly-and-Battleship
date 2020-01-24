package main;

import java.util.*;

public class Monopoly {
    public static Scanner scan = new Scanner(System.in);
    public static Scanner userIn = new Scanner(System.in);
    public static Random rand = new Random();
    private static double moneyP1 = 1500, moneyP2 = 1500, price =0;
    private static String botName, input = "", cpuName = "";
    private static int boardP1 = 0, boardP2 = 0, chest = 0, chance = 0, rent = 0, inputInt, sell, housePrice, houseSell, counter = 0;
    private static boolean winner = false, checkBuy = false, go = false, p1Turn = false, p2Turn = false;
    private static int[] dice = new int[10], areasOwned = new int[10], numHouse = new int[40];
    private static int transOwn1 = 0, transOwn2 = 4, serviceOwn1 = 0, serviceOwn2 = 0;
    private static ArrayList<String> arrayOwned = new ArrayList<>();
    private static ArrayList<String> botOwned = new ArrayList<>();

    public static void main(String[]args) {
        tutorial();
        monopoly();
    }

    //Print out the rules and concepts in Monopoly
    public static void tutorial()
    {
        String in;

        System.out.println("For the game Monopoly\nThe winner will be decided when either players has no more money and no properties owned");
        System.out.println("The game will print out the board twice");
        System.out.println("The first board shows the location");
        System.out.println("An \"A\" shows the location of the player, a \"B\" shows the location of the bot, and a \"C\" tells that both the player and the bot are at the same spot");
        System.out.println("The second board shows which properties are owned and how many houses are in a property");
        System.out.println("A \"O\" represents area you own and a \"X\" represents the area the bot owns");
        System.out.println("The stars in the second board will change to a number when there are houses on that property");
        System.out.println("Good luck and have a nice(few) hour(s) of playing Monopoly :)");
        System.out.println("Press enter if you understand the rules");
        in = scan.nextLine();

        //Create a fancy logo and ask for user input
        for (int i = 0;i<50;i++)
            System.out.print("=");
        System.out.println("\n\tWelcome to Monopoly Campion Edition");
        for (int i = 0;i<50;i++)
            System.out.print("=");
        time();

        //Ask the user to name the bot
        System.out.println("\nBefore we start, please enter a name for the bot");
        botName = scan.nextLine();
        time();
    }

    //Method for the game
    public static void monopoly()
    {
        //Create a array for the places and a array to detect non-purchasable places
        String[] boardSpots = {"School Matrix","English Library","Campion Chest","Writer's Craft Book Store","Library Late Book Fees",
                "Student Council Taxi Service","History Museum","Free Credit Lane","Anthropology Zoo","Restaurant Francais",
                "Just Visiting The Office Spot","Art Avenue","Hockey Team Inc.","The Drama Cinema", "Music Opera House",
                "Art Club Campion City Tour Bus", "Gymmy Fitness Gym","Campion Chest","Weight Room Arena","Kinesiology National Park",
                "P.A Day Parking Lot", "Biology Medical School", "Free Credit Lane", "Chemistry Nuclear Plant", "Physics Space Station",
                "Science Club Rent-a-Car Service", "Data Management Twin Towers", "Advance Function State University", "BasketBall Team Co.",
                "Campion Institute of Calculus", "Go to the Office", "Marketing Tower", "Accounting Wall Street", "Campion Chest",
                "International Business Airport", "Computer Club Subway Station", "Free Credit Lane", "Graphic Design Printing Press",
                "Student Fees Collection Services","Mt. ICS"};
        int[] nonBuy = {0,2,4,7,10,17,20,22,30,33,36,38};
        //Declare local variables
        String roll = "";
        boolean jail1 = false, jail2 = false;
        int speeding1 = 0, speeding2 = 0,jailCount1 = 1, jailCount2 = 1, anotherBoard;
        //Array to see which properties the player or bot bought
        int[] areaOwned = new int[boardSpots.length];

        //Loop until their is winner
        while(winner == false)
        {
            //Loop only when the player rolls double
            do
            {

                //Use a boolean to detect if its the players turn
                p1Turn = true;

                //Declare the dices
                dice[0] = rand.nextInt(6)+1;
                dice[1] = rand.nextInt(6)+1;


                //Check if the player is not in jail
                if (jail1 == false)
                {
                    //Ask user for their next move
                    do
                    {
                        System.out.println("Please type \"roll\" to roll the dice or \"manage\" to manage your properties");
                        time();
                        roll = scan.nextLine();

                        //Check if the player wants to manage and go to the manage method
                        if (roll.equalsIgnoreCase("Manage")){
                            manage(boardSpots, areaOwned);
                        }

                        if (moneyP1 < 0) {
                            System.out.println("I'm afraid you won't be able to roll around the board unless you sell some properties or chairs");
                            time();
                        }
                    }while(!roll.equalsIgnoreCase("roll") || moneyP1 < 0);
                }

                //Check if the player is in jail
                if (jail1 == true)
                {
                    //Ask user for their next move
                    do
                    {
                        System.out.println("Try rolling doubles or pay EC50 to get out of jail. Type \"roll\" or \"pay\"");
                        roll = scan.nextLine();
                        time();

                        //Check if the player can pay EC50 to get out of jail
                        if (roll.equalsIgnoreCase("pay"))
                            break;
                        if (roll.equalsIgnoreCase("pay") && moneyP1 < 50) {
                            System.out.println("You don't have enough money to get out of jail");
                            time();
                        }
                    }while(!roll.equalsIgnoreCase("roll"));

                    //Bring the player to spot 10
                    boardP1 = 10;

                    //Check if the player is hasn't gone out of jail yet
                    if (jailCount1 != 3 && jail1 == true && dice[0] != dice[1] && !roll.equalsIgnoreCase("pay"))
                    {
                        //Tell them they are still in the office and add up the jail counter
                        jailCount1 ++;
                        System.out.println("You are in the office");
                        time();
                        System.out.println("You rolled a " + dice[0] + " and " + dice[1] + ", which are not doubles, so you ain't getting out yet");
                        time();
                    }
                    //Check if the player got out of jail
                    else if (jailCount1 == 3 || (dice[1] == dice[0]) || roll.equalsIgnoreCase("pay"))
                    {
                        //Check if the player rolled doubles
                        if (dice[1] == dice[0] && roll.equalsIgnoreCase("roll"))
                        {
                            System.out.println("You rolled doubles, so therefore");
                            time();
                        }

                        //Tell the user they're out of jail
                        System.out.println("You are out of the office");
                        time();

                        //Check if the player payed to get out of jail
                        if (roll.equalsIgnoreCase("pay") && moneyP1 <= 50)
                        {
                            //Subtract EC50 and show their money
                            moneyP1 -= 50;
                            System.out.println("You paid EC50 to get out of jail, you now have EC" + moneyP1);
                            time();
                        }

                        //Set jail to false and reset the jail counter
                        jail1 = false;
                        jailCount1 = 1;
                    }
                }
                //Check if the player is not in jail
                if (jail1 == false)
                {
                    //Add boardP1 so player 1 can move on the board
                    boardP1 += (dice[0] + dice[1]);

                    //Check if player 1's board spot is larger than 40 to reset and set go to true
                    if (boardP1 > 39)
                    {
                        boardP1 = boardP1 - 40;
                        go = true;
                    }

                    //Check if player 1 is in a non-purchasable place
                    for (int aNonBuy : nonBuy)
                    {
                        if (boardP1 == aNonBuy)
                        {
                            checkBuy = false;
                            break;
                        }
                        else
                        {
                            checkBuy = true;
                        }
                    }

                    //Print out where player 1 is and what dice they rolled
                    System.out.println("You rolled a " + dice[0] + " and a " + dice[1]);
                    time();
                    //Check if the player doesn't land in the "go to the office spot"
                    if (boardP1 != 30)
                    {

                        //Tell the player where he is
                        System.out.println("You are in spot " + boardP1 + ". Therefore you are in " + boardSpots[boardP1]);
                        time();

                        gridLocation();
                        passGo();

                        //Check if the player is in a non-purchasable place
                        if (checkBuy == false)
                        {
                            //Call in a method to print out specific things for non-purchasable places
                            anotherBoard = boardP1;
                            nonBuy(nonBuy, anotherBoard, checkBuy);
                        }
                        //Check if the user is in a purchasable place
                        if (checkBuy == true)
                        {
                            //Call in a method to change prices for each different purchasable place
                            anotherBoard = boardP1;
                            price = prices(price, anotherBoard);

                            //Check if a area is not owned
                            if (areaOwned[boardP1] == 0)
                            {
                                //Check if the players money is larger than the price of the property
                                if (moneyP1 >= price)
                                {
                                    //Loop only when the player doesn't type yes or no
                                    do
                                    {
                                        //Tell the player the cost of the property and ask if they want to buy
                                        System.out.println("This place cost EC" + String.format("%.2f",price) + ", would you like to buy this place");
                                        time();
                                        System.out.println("You have EC" + moneyP1 + " in your bank right now");
                                        System.out.println("Type \"yes\" to buy or type \"no\" to not buy this place");
                                        time();
                                        roll = scan.nextLine();

                                        //If the user buys the place, subtract money from them, tell them they own the property, and make them own that area
                                        if (roll.equalsIgnoreCase("yes"))
                                        {
                                            areaOwned[boardP1] = 1;
                                            moneyP1 -= price;
                                            arrayOwned.add(boardSpots[boardP1]);
                                            System.out.println("Congratulations you own " + boardSpots[boardP1]);
                                            time();
                                            ownedArea(areaOwned);

                                            //Check if the player is in a transportation or service property and add the counter up
                                            if (boardP1 == 5 || boardP1 == 15 || boardP1 == 25 || boardP1 == 35)
                                                transOwn1++;
                                            if (boardP1 == 12 || boardP1 == 28)
                                                serviceOwn1++;
                                        }
                                        //If the user doesn't buy the place, tell them they didn't buy the place
                                        else if (roll.equalsIgnoreCase("no"))
                                        {
                                            System.out.println("You passed on " + boardSpots[boardP1]);
                                            time();
                                        }
                                    }while(!roll.equalsIgnoreCase("yes") && !roll.equalsIgnoreCase("no"));
                                }
                                //Check if the player doesn't have enough money to buy the property and tell them they don't have enough money
                                else if (moneyP1 < price)
                                {
                                    System.out.println("You don't have enough money to buy this place");
                                    time();
                                }
                            }
                            //Check if the player already owns the property
                            else if (areaOwned[boardP1] == 1)
                            {
                                System.out.println("You landed on " + boardSpots[boardP1] + ", you already own this property");
                                time();
                            }
                            //Check if the A.I owns the property and make the player pay rent
                            else if (areaOwned[boardP1] == 2)
                            {
                                rentPrice(anotherBoard);
                                System.out.println(botName + " owns this property so you have to pay EC" + rent + " for rent");
                                time();
                                moneyP1 -= rent;
                                moneyP2 += rent;
                            }
                        }

                        //Show the player their money
                        System.out.println("You have EC" + moneyP1 + " in your bank");
                        time();

                        //Check if the player loses
                        if (moneyP1 < 0 && arrayOwned.size() == 0) {
                            winner = true;
                            winner();
                        }

                        //Print out what properties are owned
                        gridProperty(areaOwned);
                    }
                    //Check if the player is in the go to the office spot
                    if (boardP1 == 30)
                    {
                        //Set jail to true and tell the player they are going to jail
                        jail1 = true;
                        boardP1 = 10;

                        if (chest == 4 && p1Turn == true)
                            System.out.println("Go directly to the OFFICE");
                        else if (chest != 4 && p1Turn == true)
                            System.out.println("You landed on the \"go to the office spot\". SO GO TO THE OFFICE");
                        time();
                    }

                    //Check if the user rolled double
                    time();
                    if (dice[0] == dice[1] && boardP1 != 30)
                    {
                        //Add up the speeding counter
                        speeding1 ++;

                        //Check if the speeding counter does not equal 3
                        if (speeding1 != 3)
                        {
                            System.out.println("You rolled a double, Therefore, you get to roll again");
                        }
                        //Check if the speeding counter equals 3 and bring the player to jail
                        else if (speeding1 == 3)
                        {
                            System.out.println("You are speeding, therefore you have to go to jail.");
                            jail1 = true;
                            boardP1 = 10;
                            speeding1 = 0;
                        }

                        time();
                    }
                    //Check if the player doesn't roll doubles in a row and reset the speeding counter to 0
                    else if (dice[0] != dice[1])
                    {
                        speeding1 = 0;
                    }
                }

                //Use a boolean to check if the players turn ends
                p1Turn = false;

            }while(dice[0] == dice[1] && jail1 == false);

            //Loop only when the A.I rolls doubles
            do
            {
                //Use a boolean to check if its the bot's turn
                p2Turn = true;

                //Declare dice for the A.I
                dice[2] = rand.nextInt(6)+1;
                dice[3] = rand.nextInt(6)+1;

                //Check if the A.I is in jail
                if (jail2 == true)
                {
                    //Declare a dice and bring the bot to spot 10 in the board
                    dice[6] = rand.nextInt(11)+1;
                    boardP2 = 10;

                    //Check if the bot is still in jail and if the bot didn't roll doubles
                    if (jailCount2 != 3 && jail2 == true && dice[2] != dice[3] && dice[6] > 10)
                    {
                        //Print it out and add up the turns the bot spends in jail
                        jailCount2 ++;

                        System.err.println(botName + " is in the office");
                        time();
                        System.err.println(botName + " rolled a " + dice[3] + " and " + dice[2] + ", which are not doubles, so it ain't getting out yet");
                        time();
                    }
                    //Check if the bot did something to get out of jail
                    else if (jailCount2 == 3 || (dice[2] == dice[3]) || (dice[6] <= 9 && moneyP2 >= 55))
                    {
                        //Check if the bot rolled doubles
                        if (dice[2] == dice[3])
                        {
                            System.err.println(botName + " rolled doubles, so therefore");
                            time();
                        }

                        //Check if a R.N.G dice is less than 10 to make to the bot pay to get out of jai
                        if (dice[6] <= 9 && dice[2] != dice[3])
                        {
                            moneyP2 -= 50;
                            System.err.println(botName + " payed EC50 to get out of jail");
                            time();
                        }

                        //Print out the bot is out of jail,set jail to false and reset the jail counter
                        System.err.println(botName + " is out of office");
                        time();
                        jail2 = false;
                        jailCount2 = 1;
                    }
                }

                //Check if the bot is not in jail
                if (jail2 == false)
                {
                    //Add spots for the A.I so it can move spots
                    boardP2 += (dice[2] + dice[3]);

                    //Check if the A.I has a larger board spot than 40 and set go to true
                    if (boardP2 > 39)
                    {
                        boardP2 = boardP2 - 40;
                        go = true;
                    }

                    //Check if the bot is not in a non-purchasable place
                    for (int aNonBuy : nonBuy)
                    {
                        if (boardP2 == aNonBuy)
                        {
                            checkBuy = false;
                            break;
                        }
                        else
                        {
                            checkBuy = true;
                        }
                    }

                    //Print out the numbers the bot rolled
                    System.err.println( botName + " rolled a " + dice[2] + " and a " + dice[3]);
                    time();

                    //Check if the the board spot is not in spot 30
                    if (boardP2 != 30)
                    {
                        //Print out which spot the bot is in
                        System.err.println( botName + " is in spot " + boardP2 + ". Therefore he is in " + boardSpots[boardP2]);
                        time();

                        //Go to the method passGo to see if the matrix was passed by and print out where the grid to show the location
                        gridLocation();
                        passGo();

                        //Check if the bot is in a non-purchasable place
                        if (checkBuy == false)
                        {
                            //Go to method nonBuy and carry the variable anotherboard
                            anotherBoard = boardP2;
                            nonBuy(nonBuy, anotherBoard, checkBuy);
                        }
                        //Check if the bot is in a purchasable place and tell them the cost of the place
                        if (checkBuy == true)
                        {
                            dice[4] = rand.nextInt(30)+1;
                            anotherBoard = boardP2;
                            price = prices(price, anotherBoard);

                            //Check if the area is not owned and have a random chance that the bot buys the area
                            if (areaOwned[boardP2] == 0)
                            {
                                dice[5] = rand.nextInt(31)+45;

                                //If the random chance happens make the bot buy the area if it has enough money
                                if ((dice[4] < 29) && (moneyP2 > (dice[5] + price)))
                                {
                                    if (moneyP2 >= price)
                                    {
                                        System.err.println("This place cost EC" + String.format("%.2f",price));
                                        time();
                                        System.err.println(botName + " bought this place");
                                        time();

                                        moneyP2 -= price;
                                        areaOwned[boardP2] = 2;
                                        botOwned.add(boardSpots[boardP2]);

                                        //Check if the bot is in a transportation or service property and add the counter up
                                        if (boardP2 == 5 || boardP2 == 15 || boardP2 == 25 || boardP2 == 35)
                                            transOwn2++;
                                        if (boardP2 == 12 || boardP2 == 28)
                                            serviceOwn2++;
                                    }
                                    else if (moneyP2 < price)
                                    {
                                        System.err.println(botName + " doesn't have enough money to buy this place");
                                        time();
                                    }
                                }
                                //If the random chance doesn't happen and the money is lower than a certain price, the bot should not buy the property
                                else if (dice[4] >= 29 || (moneyP2 < (dice[5] + price)))
                                {
                                    System.err.println(botName + " didn't buy the place");
                                    time();
                                }
                            }
                            //Check if the bot owns the property he is in
                            else if (areaOwned[boardP2] == 2)
                            {
                                System.err.println(botName + " owns this place");
                                time();
                            }
                            //Check if the bot is in a area owned by you and make the bot pay rent
                            else if (areaOwned[boardP2] == 1)
                            {
                                rentPrice(anotherBoard);
                                System.err.println(botName + " has to pay you EC" + rent + " for rent");
                                time();
                                moneyP2 -= rent;
                                moneyP1 += rent;

                            }
                        }

                        //Print the out the properties owned
                        gridProperty(areaOwned);

                        //Show the A.I the money he has
                        System.err.println(botName + " has EC" + moneyP2 + " in its bank");
                        time();

                        //Check if the bot is going to lose
                        if ((botOwned.size() == 0 && moneyP2 < 0))
                            winner();

                        //Check if the player rolls doubles
                        if (dice[2] == dice[3])
                        {
                            //Add up the speeding counter
                            speeding2++;

                            //Check if the speeding does not equal 3
                            if (speeding2 != 3)
                            {
                                System.err.println(botName + " rolled doubles, therefore he goes again");
                                time();
                            }
                            //Check if the speeding counter is 3 and send the bot to jail
                            else if (speeding2 == 3)
                            {
                                System.err.println(botName + " is speeding therefore he goes to the office");
                                time();
                                jail2 = true;
                                boardP2 = 10;
                                speeding2 =0;
                            }
                        }
                        //If the two dices does not equal each other, reset the speeding counter
                        else if (dice[2] != dice[3])
                        {
                            speeding2 = 0;
                        }
                    }
                    //Check if the bot is in the go to the office spot and tell them
                    if (boardP2 == 30)
                    {
                        if (chest == 4 && p2Turn == true)
                            System.err.println(botName + " has to go to the office");
                        else if (chest != 4 && p2Turn == true)
                            System.err.println(botName + " landed on the \"go to the office spot\". SO IT NEEDS TO GO THE OFFICE");
                        time();

                        boardP2 = 10;
                        jail2 = true;
                    }
                }

                //Check if the bot has some property owned and go to a method to buy or sell things
                if (botOwned.size() != 0)
                    botManage(boardSpots, areaOwned);

                //This boolean detects if the bot's turn is over
                p2Turn = false;
            }while(dice[2] == dice[3] && jail2 == false);
        }

    }

    //Method to change the price for a property
    public static double prices (double price, int boardSpots)
    {
        //Check if the player or bot is at a certain spot to change the price of the property
        if (boardSpots == 1 || boardSpots == 3)
            price = 60;
        else if (boardSpots == 6 || boardSpots == 8)
            price = 100;
        else if (boardSpots == 9)
            price = 120;
        else if (boardSpots == 11 || boardSpots == 13)
            price = 140;
        else if (boardSpots == 14)
            price = 160;
        else if (boardSpots == 16 || boardSpots == 18)
            price = 180;
        else if (boardSpots == 19)
            price = 200;
        else if (boardSpots == 21 || boardSpots == 23)
            price = 220;
        else if (boardSpots == 24)
            price = 240;
        else if (boardSpots == 26 || boardSpots == 27)
            price = 260;
        else if (boardSpots == 29)
            price = 280;
        else if (boardSpots == 31 || boardSpots == 32)
            price = 300;
        else if (boardSpots == 34)
            price = 320;
        else if (boardSpots == 37)
            price = 350;
        else if (boardSpots == 39)
            price = 400;
        else if (boardSpots == 5 || boardSpots == 15 || boardSpots == 25 || boardSpots == 35)
            price = 200;
        else if (boardSpots == 12 || boardSpots == 28)
            price = 150;


        return price;
    }

    //Method to change rent price at a certain spot
    public static void rentPrice(int boardSpot)
    {
        //If the bot or player is at a certain spot and there are a certain amount of houses, change the price of rent
        if (boardSpot == 1) {
            if (numHouse[1] == 0)
                rent = 2;
            else if (numHouse[1] == 1)
                rent = 10;
            else if (numHouse[1] == 2)
                rent = 30;
            else if (numHouse[1] == 3)
                rent = 90;
            else if (numHouse[1] == 4)
                rent = 160;
            else if (numHouse[1] == 5)
                rent = 250;
        }
        else if (boardSpot == 3){
            if (numHouse[3] == 0)
                rent = 4;
            else if (numHouse[3] == 1)
                rent = 20;
            else if (numHouse[3] == 2)
                rent = 60;
            else if (numHouse[3] == 3)
                rent = 180;
            else if (numHouse[3] == 4)
                rent = 320;
            else if (numHouse[3] == 5)
                rent = 450;
        }
        else if (boardSpot == 6) {
            if (numHouse[6] == 0)
                rent = 6;
            else if (numHouse[6] == 1)
                rent = 30;
            else if (numHouse[6] == 2)
                rent = 90;
            else if (numHouse[6] == 3)
                rent = 270;
            else if (numHouse[6] == 4)
                rent = 400;
            else if (numHouse[6] == 5)
                rent = 550;
        }
        else if (boardSpot == 8){
            if (numHouse[8] == 0)
                rent = 6;
            else if (numHouse[8] == 1)
                rent = 30;
            else if (numHouse[8] == 2)
                rent = 90;
            else if (numHouse[8] == 3)
                rent = 270;
            else if (numHouse[8] == 4)
                rent = 400;
            else if (numHouse[8] == 5)
                rent = 550;
        }
        else if (boardSpot == 9){
            if (numHouse[9] == 0)
                rent = 8;
            else if (numHouse[9] == 1)
                rent = 40;
            else if (numHouse[9] == 2)
                rent = 100;
            else if (numHouse[9] == 3)
                rent = 300;
            else if (numHouse[9] == 4)
                rent = 450;
            else if (numHouse[9] == 5)
                rent = 600;
        }
        else if (boardSpot == 11){
            if (numHouse[11] == 0)
                rent = 10;
            else if (numHouse[11] == 1)
                rent = 50;
            else if (numHouse[11] == 2)
                rent = 150;
            else if (numHouse[11] == 3)
                rent = 450;
            else if (numHouse[11] == 4)
                rent = 625;
            else if (numHouse[11] == 5)
                rent = 750;
        }
        else if (boardSpot == 13) {
            if (numHouse[13] == 0)
                rent = 10;
            else if (numHouse[13] == 1)
                rent = 50;
            else if (numHouse[13] == 2)
                rent = 150;
            else if (numHouse[13] == 3)
                rent = 450;
            else if (numHouse[13] == 4)
                rent = 625;
            else if (numHouse[13] == 5)
                rent = 750;
        }
        else if (boardSpot == 14){
            if (numHouse[14] == 0)
                rent = 12;
            else if (numHouse[14] == 1)
                rent = 60;
            else if (numHouse[14] == 2)
                rent = 180;
            else if (numHouse[14] == 3)
                rent = 500;
            else if (numHouse[14] == 4)
                rent = 700;
            else if (numHouse[14] == 5)
                rent = 900;
        }
        else if (boardSpot == 16 || boardSpot == 18){
            if (numHouse[16] == 0)
                rent = 14;
            else if (numHouse[16] == 1)
                rent = 70;
            else if (numHouse[16] == 2)
                rent = 200;
            else if (numHouse[16] == 3)
                rent = 550;
            else if (numHouse[16] == 4)
                rent = 700;
            else if (numHouse[16] == 5)
                rent = 900;
        }
        else if (boardSpot == 18) {
            if (numHouse[16] == 0)
                rent = 14;
            else if (numHouse[18] == 1)
                rent = 70;
            else if (numHouse[18] == 2)
                rent = 200;
            else if (numHouse[18] == 3)
                rent = 550;
            else if (numHouse[18] == 4)
                rent = 700;
            else if (numHouse[18] == 5)
                rent = 950;
        }
        else if (boardSpot == 19){
            if (numHouse[19] == 0)
                rent = 16;
            else if (numHouse[19] == 1)
                rent = 80;
            else if (numHouse[19] == 2)
                rent = 220;
            else if (numHouse[19] == 3)
                rent = 600;
            else if (numHouse[19] == 4)
                rent = 800;
            else if (numHouse[19] == 5)
                rent = 1000;
        }
        else if (boardSpot == 21){
            if (numHouse[21] == 0)
                rent = 18;
            else if (numHouse[21] == 1)
                rent = 90;
            else if (numHouse[21] == 2)
                rent = 250;
            else if (numHouse[21] == 3)
                rent = 700;
            else if (numHouse[21] == 4)
                rent = 875;
            else if (numHouse[21] == 5)
                rent = 1050;
        }
        else if (boardSpot == 23){
            if (numHouse[23] == 0)
                rent = 18;
            else if (numHouse[23] == 1)
                rent = 90;
            else if (numHouse[23] == 2)
                rent = 250;
            else if (numHouse[23] == 3)
                rent = 700;
            else if (numHouse[23] == 4)
                rent = 875;
            else if (numHouse[23] == 5)
                rent = 1050;
        }
        else if (boardSpot == 24){
            if (numHouse[24] == 0)
                rent = 20;
            else if (numHouse[24] == 1)
                rent = 100;
            else if (numHouse[24] == 2)
                rent = 300;
            else if (numHouse[24] == 3)
                rent = 750;
            else if (numHouse[24] == 4)
                rent = 925;
            else if (numHouse[24] == 5)
                rent = 1100;
        }
        else if (boardSpot == 26){
            if (numHouse[26] == 0)
                rent = 22;
            else if (numHouse[26] == 1)
                rent = 110;
            else if (numHouse[26] == 2)
                rent = 330;
            else if (numHouse[26] == 3)
                rent = 800;
            else if (numHouse[26] == 4)
                rent = 975;
            else if (numHouse[26] == 5)
                rent = 1150;
        }
        else if (boardSpot == 27){
            if (numHouse[27] == 0)
                rent = 22;
            else if (numHouse[27] == 1)
                rent = 110;
            else if (numHouse[27] == 2)
                rent = 330;
            else if (numHouse[27] == 3)
                rent = 800;
            else if (numHouse[27] == 4)
                rent = 975;
            else if (numHouse[27] == 5)
                rent = 1150;
        }
        else if (boardSpot == 29){
            if (numHouse[29] == 0)
                rent = 24;
            else if (numHouse[29] == 1)
                rent = 120;
            else if (numHouse[29] == 2)
                rent = 360;
            else if (numHouse[29] == 3)
                rent = 850;
            else if (numHouse[29] == 4)
                rent = 1025;
            else if (numHouse[29] == 5)
                rent = 1200;
        }
        else if (boardSpot == 31){
            if (numHouse[31] == 0)
                rent = 26;
            else if (numHouse[31] == 1)
                rent = 130;
            else if (numHouse[31] == 2)
                rent = 390;
            else if (numHouse[31] == 3)
                rent = 900;
            else if (numHouse[31] == 4)
                rent = 1100;
            else if (numHouse[31] == 5)
                rent = 1275;
        }
        else if (boardSpot == 32){
            if (numHouse[32] == 0)
                rent = 26;
            else if (numHouse[32] == 1)
                rent = 130;
            else if (numHouse[32] == 2)
                rent = 390;
            else if (numHouse[32] == 3)
                rent = 900;
            else if (numHouse[32] == 4)
                rent = 1100;
            else if (numHouse[32] == 5)
                rent = 1275;
        }
        else if (boardSpot == 34){
            if (numHouse[34] == 0)
                rent = 28;
            else if (numHouse[34] == 1)
                rent = 150;
            else if (numHouse[34] == 2)
                rent = 450;
            else if (numHouse[34] == 3)
                rent = 1000;
            else if (numHouse[34] == 4)
                rent = 1200;
            else if (numHouse[34] == 5)
                rent = 1400;
        }
        else if (boardSpot == 37){
            if (numHouse[37] == 0)
                rent = 35;
            else if (numHouse[37] == 1)
                rent = 175;
            else if (numHouse[37] == 2)
                rent = 500;
            else if (numHouse[37] == 3)
                rent = 1100;
            else if (numHouse[37] == 4)
                rent = 1300;
            else if (numHouse[37] == 5)
                rent = 1500;
        }
        else if (boardSpot == 39){
            if (numHouse[39] == 0)
                rent = 50;
            else if (numHouse[39] == 1)
                rent = 200;
            else if (numHouse[39] == 2)
                rent = 600;
            else if (numHouse[39] == 3)
                rent = 1400;
            else if (numHouse[39] == 4)
                rent = 1700;
            else if (numHouse[39] == 5)
                rent = 2000;
        }
        //If the player or bot is in a transportation or service spot, rent will be based on how much of those types of properties are owned
        else if (boardSpot == 5 || boardSpot == 15 || boardSpot == 25 || boardSpot == 35){
            if (p1Turn == true) {
                if (transOwn2 == 1)
                    rent = 25;
                else if (transOwn2 == 2)
                    rent = 50;
                else if (transOwn2 == 3)
                    rent = 100;
                else if (transOwn2 == 4)
                    rent = 200;
            }
            else if (p2Turn == true){
                if (transOwn1 == 1)
                    rent = 25;
                else if (transOwn1 == 2)
                    rent = 50;
                else if (transOwn1 == 3)
                    rent = 100;
                else if (transOwn1 == 4)
                    rent = 200;
            }
        }
        else if (boardSpot == 12 || boardSpot == 28){
            if (p1Turn == true){
                if (serviceOwn2 == 1)
                    rent = ((dice[0]+dice[1])*4);
                else if (serviceOwn2 == 2)
                    rent = ((dice[0]+dice[1])*10);
            }
            else if (p2Turn == true){
                if (serviceOwn1 == 1)
                    rent = ((dice[2]+dice[3])*4);
                else if (serviceOwn1 == 2)
                    rent = ((dice[2]+dice[3])*10);
            }
        }

        return;
    }

    //Method to check if the matrix spot was passed by and add money
    public static void passGo()
    {
        //Check if the player or bot passes the matrix
        if (go == true)
        {
            //Tell the player or bot they passed the go/matrix spot, increase the money and reset the go counter
            if (p1Turn == true)
            {
                moneyP1 += 200;
                System.out.println("You are on or passed the matrix spot. You get EC200");
            }
            else if (p2Turn == true)
            {
                moneyP2 += 200;
                System.err.println("You are on or passed the matrix spot. You get EC200");
            }
            time();

            //Reset go to false
            go = false;
        }

        return;
    }

    //Method to manage the players properties
    public static void manage(String[] spotName, int[] boardNum)
    {
        //Declare local variables
        int c = 0, houseWithNums, detectBuy = 0;
        boolean check = false, service = false;

        //Print out the properties the players own
        for (int i = 0;i < arrayOwned.size();i++)
        {
            int j = i+1;
            System.out.println(j + ") " + arrayOwned.get(i));
        }

        //Check if the player owns no property
        if (arrayOwned.size() == 0)
        {
            System.out.println("You own no property");
            time();
        }
        //Check if the player does own property
        else if (arrayOwned.size() >= 1)
        {
            //Loop while the player owns property and while the player doesn't type "back
            do
            {

                //Ask user for input
                System.out.println("Please type the EXACT name of the place you want to manage, if you don't want to manage anything, just type \"back\"");
                input = scan.nextLine();
                time();

                //Call in this method to check if certain areas are owned to detect which areas are allowed to have houses bought
                ownedArea(boardNum);

                //Loop the size of how many properties you own
                for (int i = 0;i <arrayOwned.size();i++)
                {

                    //Check if the player types the exact name of a property
                    if (input.equalsIgnoreCase(arrayOwned.get(i)))
                    {

                        //Loop the size of the board
                        for (int a = 0; a < spotName.length;a++)
                        {

                            //Check if the exact name equals something in a area
                            if (spotName[a].equalsIgnoreCase(arrayOwned.get(i)))
                            {

                                //Check if the board spot is owned by the player
                                if (boardNum[a] == 1)
                                {
                                    //Check if a certain combinations of areas are owned and use a boolean to detect if houses can be bought
                                    if ((a == 1 || a == 3) && areasOwned[0] == 1)
                                        check = true;
                                    else if ((a == 6 || a == 8 || a == 9) && areasOwned[1] == 1)
                                        check = true;
                                    else if ((a == 11 || a == 13 || a == 14) && areasOwned[2] == 1)
                                        check = true;
                                    else if ((a == 16 || a == 18 || a == 19) && areasOwned[3] == 1)
                                        check = true;
                                    else if ((a == 21 || a == 23 || a == 24) && areasOwned[4] == 1)
                                        check = true;
                                    else if ((a == 26 || a == 27 || a == 29) && areasOwned[5] == 1)
                                        check = true;
                                    else if ((a == 31 || a == 33 || a == 34) && areasOwned[6] == 1)
                                        check = true;
                                    else if ((a == 37 || a == 39) && areasOwned[7] == 1)
                                        check = true;
                                        //The service used to check if its a transportation or service property, where no houses can be bought on
                                    else if ((a == 5 || a == 15 || a == 25 || a == 35) && areasOwned[8] == 1)
                                    {
                                        check = true;
                                        service = true;
                                    }
                                    else if ((a == 12 || a == 28) && areasOwned[9] == 1)
                                    {
                                        check = true;
                                        service = true;
                                    }
                                }

                                //Break the for-loop
                                c = a;
                                break;
                            }
                        }

                        System.out.println("What would you like to do this property");
                        time();

                        //Keep looping until the player wants to go another property
                        do
                        {

                            //Enter user input and tell the things which a player can type
                            System.out.println("Type \"sell\" to sell the property or some chairs,  or \"buy\" to buy chairs and tables for the property");
                            time();
                            System.out.println("Type \"back\" to go to another property");
                            time();
                            System.out.println("If you own some houses in a property typing \"sell\" will sell all the houses in the propoerty first");
                            time();
                            System.out.println("If you own no houses in a property, the only thing being sold is the property and your confindence of winning the game");
                            input = scan.nextLine();
                            time();

                            //Check if the player wants to buy
                            if (input.equalsIgnoreCase("buy") && service == false && check == true)
                            {

                                //Loop only when a player doesn't input a proper number
                                do
                                {

                                    //Method to detect the cost of houses
                                    houseCost(c);

                                    //Ask user input
                                    System.out.println("How many chairs would you like to buy");
                                    time();
                                    System.out.println("The cost for each chair is EC" + housePrice + ". Right now you have EC" + moneyP1 + " in your bank");
                                    time();
                                    System.out.println("Max amount is 5 and minimum amount is 1");
                                    inputInt = userIn.nextInt();
                                    time();

                                    //Check if the input is between 1 and 5
                                    if (inputInt > 0 && inputInt <= 5)
                                    {
                                        //Create equations for the cost of houses
                                        houseWithNums = housePrice*inputInt;
                                        detectBuy = 5 - numHouse[c];

                                        //Check if the player has enough money
                                        if (moneyP1 < houseWithNums)
                                            System.out.println("Im afraid you don't have enough money to buy that much chairs, total cost of the houses are EC" + houseWithNums);
                                        else if (moneyP1 >= houseWithNums && inputInt <= detectBuy)
                                        {
                                            System.out.println("You bought " + inputInt + " chair(s) for " + arrayOwned.get(i) + ", those chairs cost EC" + houseWithNums);
                                            moneyP1 -= houseWithNums;
                                            numHouse[c] += inputInt;
                                        }
                                        //Check if the player buys over the house capacity for each area
                                        //For example when a player previously bought 3 houses and he wants and buy 3 more
                                        //This will say that they cannot buy over the capacity
                                        else if (moneyP1 >= houseWithNums && inputInt > detectBuy)
                                        {
                                            System.out.println("You cannot buy " + inputInt + " chair(s) because you can only buy 5 chairs per property");
                                            time();
                                            System.out.println("You have " + numHouse[c] + " in this property");
                                            if (numHouse[c] == 5)
                                                break;
                                        }
                                        time();
                                    }

                                } while (inputInt <= 0 || inputInt > detectBuy);
                            }
                            //Check if the player wants to buy in a transportation or service area
                            else if (input.equalsIgnoreCase("buy") && service == true)
                            {System.out.println("You can not buy chairs on this property as they are a company or transportation service"); time();}
                            //Check if the player can't buy houses on a area yet
                            else if (input.equalsIgnoreCase("buy") && check == false)
                            {System.out.println("You can't buy any chairs for this property yet as you need some other properties"); time();}
                            //Check if the player wants to sell
                            else if (input.equalsIgnoreCase("sell"))
                            {
                                //Go to a method to check the selling price of each property
                                sellingPrice(c);

                                //Check if the player has 0 houses on a property and sell the property
                                if (numHouse[c] == 0)
                                {
                                    System.out.println("You sold " + arrayOwned.get(i) + " for EC" + sell);
                                    time();
                                    System.out.println("You have EC" + moneyP1 + " in your bank");
                                    time();
                                    arrayOwned.remove(i);
                                    boardNum[c] = 0;
                                    moneyP1 += sell;
                                    manage(spotName, boardNum);
                                }
                                //Check if the player has 1 or more houses in a property, and then sell all houses on a property
                                else if (numHouse[c] > 0)
                                {
                                    moneyP1 += (numHouse[c]*houseSell);
                                    System.out.println("You sold " + numHouse[c] + " chairs in " + arrayOwned.get(i) + " for EC" + (numHouse[c]*houseSell));
                                    time();
                                    numHouse[c] -= numHouse[c];
                                    System.out.println("You have EC" + moneyP1 + " in your bank");
                                    time();
                                }

                                if (arrayOwned.size() == 0 && moneyP1 < 0) {
                                    winner = true;
                                    winner();
                                }
                            }


                        }while(!input.equalsIgnoreCase("back") && arrayOwned.size() != 0);

                        //Break the loop
                        break;
                    }
                }

            }while(!input.equalsIgnoreCase("back") && arrayOwned.size() != 0);
        }
        return;
    }

    //Method for the bot to manage its properties on
    public static void botManage(String[] boardName, int[] boardOwned)
    {
        //Declare local variables
        int c, dice2, costOfHouses, dice3, overCap, botCall = 0;
        boolean detect = false, placeNoHouse = false;
        dice2 = rand.nextInt(5)+1;

        //Check if the bot owns more than 1 property
        if (botOwned.size() >= 1)
        {

            //Declare dice
            int dice1 = rand.nextInt(botOwned.size())+1;

            //Loop 40 times and check if which area is being managed by checking the names of the area
            for (int a = 0; a < boardName.length; a++)
            {
                if (boardName[a].equalsIgnoreCase(botOwned.get(dice1-1)))
                {

                    //Call in this method to check if certain areas are owned, you may buy houses at those certain areas
                    ownedArea(boardOwned);
                    c = a;

                    //Check if the area is owned by the bot
                    if (boardOwned[a] == 2)
                    {
                        //Check the certain area combinations to determine which property are allowed to have houses
                        if ((a == 1 || a == 3) && areasOwned[0] == 2)
                            detect = true;
                        else if ((a == 6 || a == 8 || a == 9) && areasOwned[1] == 2)
                            detect = true;
                        else if ((a == 11 || a == 13 || a == 14) && areasOwned[2] == 2)
                            detect = true;
                        else if ((a == 16 || a == 18 || a == 19) && areasOwned[3] == 2)
                            detect = true;
                        else if ((a == 21 || a == 23 || a == 24) && areasOwned[4] == 2)
                            detect = true;
                        else if ((a == 26 || a == 27 || a == 29) && areasOwned[5] == 2)
                            detect = true;
                        else if ((a == 31 || a == 33 || a == 34) && areasOwned[6] == 2)
                            detect = true;
                        else if ((a == 37 || a == 39) && areasOwned[7] == 2)
                            detect = true;
                            //Check if the area is a transportation or service area by a boolean
                        else if ((a == 5 || a == 15 || a == 25 || a == 35) && areasOwned[8] == 2)
                        {
                            detect = true;
                            placeNoHouse = true;
                        }
                        else if ((a == 12 || a == 28) && areasOwned[9] == 2)
                        {
                            detect = true;
                            placeNoHouse = true;
                        }

                        //Call in a method to change the cost of houses per area
                        houseCost(c);

                        //Calculations
                        costOfHouses = housePrice * dice2;
                        overCap = 5 - dice2;

                        //If the bot has money, make the bot buy houses at the area
                        if (detect == true && moneyP2 > (costOfHouses + 150) && placeNoHouse == false && dice2 <= overCap)
                        {
                            moneyP2 -= costOfHouses;
                            numHouse[a] += dice2;
                            System.err.println(botName + " bought " + dice2 + " chair(s) for " + boardName[a] + " for a price of EC" + costOfHouses);
                            time();
                            System.err.println(botName + " has EC" + moneyP2 + " in its bank");
                            time();

                            dice3 = rand.nextInt(5) + 1;

                            //If the R.N.G is right, call the method again to buy more houses for a area
                            if (dice3 <= 2)
                                botManage(boardName, boardOwned);
                        }

                        //Check if the bot's money is less than 0
                        if (moneyP2 < 0 && botOwned.size() != 0)
                        {
                            //Call in a method to change the price for selling a property or hosue
                            sellingPrice(c);

                            //If there are houses on a property sell those houses first
                            if (numHouse[a] > 0)
                            {
                                moneyP2 += (houseSell * dice2);
                                numHouse[a] -= numHouse[a];
                                System.err.println(botName + " sold " + dice2 + " chairs for EC" + (houseSell * dice2) + " on " + boardName[a]);
                                time();
                                System.err.println(botName + " has " + moneyP2 + " its bank");
                                time();
                            }
                            //If there no houses on a property, sell the entire property
                            else if (numHouse[a] == 0 && botOwned.size() != 0)
                            {
                                boardOwned[a] = 0;
                                moneyP2 += sell;
                                System.err.println(botName + " sold the property " + boardName[a] + " for EC" + sell);
                                time();
                                System.err.println(botName + " has EC" + moneyP2 + " in his bank");
                                time();
                                botOwned.remove(dice1-1);
                            }

                            //Keep calling this method until the bot's money is at a positive integer
                            if (botOwned.size() > 0 && moneyP2 < 0)
                            {
                                botManage(boardName, boardOwned);
                            }
                        }

                    }
                    //Break the loop incase of a out of bounds error when removing an area from the array list
                    break;
                }
            }
        }

        return;
    }

    //Method to assign which areas can buy houses
    public static void ownedArea(int[] ownedBoard)
    {
        //Check for combination of properties owned and assign values to the each area to determine whether a bot or player can buy houses on those area
        if (ownedBoard[1] == 1 && ownedBoard[3] == 1)
            areasOwned[0] = 1;
        else if (ownedBoard[1] == 2 && ownedBoard[3] == 2)
            areasOwned[0] = 2;
        else
            areasOwned[0] = 0;
        if (ownedBoard[6] == 1 && ownedBoard[8] == 1 && ownedBoard[9] == 1)
            areasOwned[1] = 1;
        else if (ownedBoard[6] == 2 && ownedBoard[8] == 2 && ownedBoard[9] == 2)
            areasOwned[1] = 2;
        else
            areasOwned[1] = 0;
        if (ownedBoard[11] == 1 && ownedBoard[13] == 1 && ownedBoard[14] == 1)
            areasOwned[2] = 1;
        else if (ownedBoard[11] == 2 && ownedBoard[13] == 2 && ownedBoard[14] == 2)
            areasOwned[2] = 2;
        else
            areasOwned[2] = 0;
        if (ownedBoard[16] == 1 && ownedBoard[18] == 1 && ownedBoard[19] == 1)
            areasOwned[3] = 1;
        else if (ownedBoard[16] == 2 && ownedBoard[18] == 2 && ownedBoard[19] == 2)
            areasOwned[3] = 2;
        else
            areasOwned[3] = 0;
        if (ownedBoard[21] == 1 && ownedBoard[23] == 1 && ownedBoard[24] == 1)
            areasOwned[4] = 1;
        else if (ownedBoard[21] == 2 && ownedBoard[23] == 2 && ownedBoard[24] == 2)
            areasOwned[4] = 2;
        else
            areasOwned[4] = 0;
        if (ownedBoard[26] == 1 && ownedBoard[27] == 1 && ownedBoard[29] == 1)
            areasOwned[5] = 1;
        else if (ownedBoard[26] == 2 && ownedBoard[27] == 2 && ownedBoard[29] == 2)
            areasOwned[5] = 2;
        else
            areasOwned[5] = 0;
        if (ownedBoard[31] == 1 && ownedBoard[33] == 1 && ownedBoard[34] == 1)
            areasOwned[6] = 1;
        else if (ownedBoard[31] == 2 && ownedBoard[33] == 2 && ownedBoard[34] == 2)
            areasOwned[6] = 2;
        else
            areasOwned[6] = 0;
        if (ownedBoard[37] == 1 && ownedBoard[39] == 1)
            areasOwned[7] = 1;
        else if (ownedBoard[37] == 2 && ownedBoard[39] == 2)
            areasOwned[7] = 2;
        else
            areasOwned[7] = 0;
        if (ownedBoard[5] == 1 && ownedBoard[15] == 1 && ownedBoard[25] == 1 && ownedBoard[35] == 1)
            areasOwned[8] = 1;
        else if (ownedBoard[5] == 2 && ownedBoard[15] == 2 && ownedBoard[25] == 2 && ownedBoard[35] == 2)
            areasOwned[8] = 2;
        else
            areasOwned[8] = 0;
        if (ownedBoard[12] == 1 && ownedBoard[28] == 1)
            areasOwned[9] = 1;
        else if (ownedBoard[12] == 2 && ownedBoard[28] == 2)
            areasOwned[9] = 2;
        else
            areasOwned[9] = 0;

        return;
    }

    //Method to change cost of house at a certain board spot
    public static void houseCost(int c)
    {
        //Check the spot either player is in and change the cost of the house
        if (c == 1 || c == 3 || c == 6 || c == 8 || c == 9)
            housePrice = 50;
        else if (c == 11 || c == 13 || c == 14 || c == 16 || c == 17 || c == 19)
            housePrice = 100;
        else if (c == 21 || c == 23 || c == 24 || c == 26 || c == 27 || c == 29)
            housePrice = 150;
        else if (c == 31 || c == 32 || c == 34 || c == 37 || c == 39)
            housePrice = 200;

        return;
    }

    //Method to change the selling price and house selling at a certain area
    public static void sellingPrice(int c)
    {
        //Check what area either player is in, and change the selling price or house selling price
        if (c == 1 || c == 3 || c == 6 || c == 8 || c == 9)
        {
            sell = 45;
            houseSell = 25;
        }
        else if (c == 11 || c == 13 || c == 14 || c == 16 || c == 17 || c == 19)
        {
            sell = 85;
            houseSell = 50;
        }
        else if (c == 21 || c == 23 || c == 24 || c == 26 || c == 27 || c == 29)
        {
            sell = 125;
            houseSell = 75;
        }
        else if (c == 31 || c == 32 || c == 34 || c == 37 || c == 39)
        {
            sell = 165;
            houseSell = 100;
        }
        else if (c == 5 || c == 15 || c == 25 || c == 35)
            sell = 100;
        else if (c == 12 || c == 28)
            sell = 75;

        return;
    }

    //Method to print out where the bot and player is
    public static void gridLocation()
    {
        //Declare array
        String[] boardLocation = new String[40];

        //If the player or bot is at a certain spot, assign a value to that spot
        for (int i = 0; i < boardLocation.length;i++)
        {
            if (boardP1 == i && boardP2 != i)
                boardLocation[i] = "A";
            else if (boardP2 == i && boardP1 != i)
                boardLocation[i] = "B";
            else if (boardP2 == i && boardP1 == i)
                boardLocation[i] = "C";
            else
                boardLocation[i] = "*";
        }

        //Print out the location of the player and bot
        if (p1Turn == true) {
            System.out.println(" " + boardLocation[10] + " " + boardLocation[11] + " " + boardLocation[12] + " " + boardLocation[13] + " " + boardLocation[14] + " " + boardLocation[15] + " " + boardLocation[16] + " " + boardLocation[17] + " " + boardLocation[18] + " " + boardLocation[19] + " " + boardLocation[20]);
            System.out.println(" " + boardLocation[9] + " . . . . . . . . . " + boardLocation[21]);
            System.out.println(" " + boardLocation[8] + " . . . . . . . . . " + boardLocation[22]);
            System.out.println(" " + boardLocation[7] + " . . . . . . . . . " + boardLocation[23]);
            System.out.println(" " + boardLocation[6] + " . . . . . . . . . " + boardLocation[24]);
            System.out.println(" " + boardLocation[5] + " . . . . . . . . . " + boardLocation[25]);
            System.out.println(" " + boardLocation[4] + " . . . . . . . . . " + boardLocation[26]);
            System.out.println(" " + boardLocation[3] + " . . . . . . . . . " + boardLocation[27]);
            System.out.println(" " + boardLocation[2] + " . . . . . . . . . " + boardLocation[28]);
            System.out.println(" " + boardLocation[1] + " . . . . . . . . . " + boardLocation[29]);
            System.out.println(" " + boardLocation[0] + " " + boardLocation[39] + " " + boardLocation[38] + " " + boardLocation[37] + " " + boardLocation[36] + " " + boardLocation[35] + " " + boardLocation[34] + " " + boardLocation[33] + " " + boardLocation[32] + " " + boardLocation[31] + " " + boardLocation[30]);
        }
        else if (p2Turn == true){
            System.err.println(" " + boardLocation[10] + " " + boardLocation[11] + " " + boardLocation[12] + " " + boardLocation[13] + " " + boardLocation[14] + " " + boardLocation[15] + " " + boardLocation[16] + " " + boardLocation[17] + " " + boardLocation[18] + " " + boardLocation[19] + " " + boardLocation[20]);
            System.err.println(" " + boardLocation[9] + " . . . . . . . . . " + boardLocation[21]);
            System.err.println(" " + boardLocation[8] + " . . . . . . . . . " + boardLocation[22]);
            System.err.println(" " + boardLocation[7] + " . . . . . . . . . " + boardLocation[23]);
            System.err.println(" " + boardLocation[6] + " . . . . . . . . . " + boardLocation[24]);
            System.err.println(" " + boardLocation[5] + " . . . . . . . . . " + boardLocation[25]);
            System.err.println(" " + boardLocation[4] + " . . . . . . . . . " + boardLocation[26]);
            System.err.println(" " + boardLocation[3] + " . . . . . . . . . " + boardLocation[27]);
            System.err.println(" " + boardLocation[2] + " . . . . . . . . . " + boardLocation[28]);
            System.err.println(" " + boardLocation[1] + " . . . . . . . . . " + boardLocation[29]);
            System.err.println(" " + boardLocation[0] + " " + boardLocation[39] + " " + boardLocation[38] + " " + boardLocation[37] + " " + boardLocation[36] + " " + boardLocation[35] + " " + boardLocation[34] + " " + boardLocation[33] + " " + boardLocation[32] + " " + boardLocation[31] + " " + boardLocation[30]);

        }
        time();

        return;
    }

    //Method to show which properties are owned and how houses are in a property
    public static void gridProperty(int[] properties)
    {

        //Declare arrays
        String[]propOwned = new String[40];
        String[] houseString = new String[40];

        //Check if a property at a certain is owned and change the symbol by using a for loop
        //Check how many houses are at a certain property and change the symbol by using a for loop
        for (int i = 0;i < propOwned.length;i++)
        {
            if (properties[i] == 0)
                propOwned[i] = " ";
            else if (properties[i] == 1)
                propOwned[i] = "O";
            else if (properties[i] == 2)
                propOwned[i] = "X";

            if (numHouse[i] == 0)
                houseString[i] = "*";
            else if (numHouse[i] == 1)
                houseString[i] = "1";
            else if (numHouse[i] == 2)
                houseString[i] = "2";
            else if (numHouse[i] == 3)
                houseString[i] = "3";
            else if (numHouse[i] == 4)
                houseString[i] = "4";
            else if (numHouse[i] == 5)
                houseString[i] = "5";
        }

        if (p1Turn == true) {
            System.out.println(" " + propOwned[10] + " " + propOwned[11] + " " + propOwned[12] + " " + propOwned[13] + " " + propOwned[14] + " " + propOwned[15] + " " + propOwned[16] + " " + propOwned[17] + " " + propOwned[18] + " " + propOwned[19]);
            System.out.println(" " + houseString[10] + " " + houseString[11] + " " + houseString[12] + " " + houseString[13] + " " + houseString[14] + " " + houseString[15] + " " + houseString[16] + " " + houseString[17] + " " + houseString[18] + " " + houseString[19] + " " + houseString[20]);
            System.out.println(propOwned[9] + houseString[9] + " . . . . . . . . . " + houseString[21] + propOwned[21]);
            System.out.println(propOwned[8] + houseString[8] + " . . . . . . . . . " + houseString[22]);
            System.out.println(" " + houseString[7] + " . . . . . . . . . " + houseString[23] + propOwned[23]);
            System.out.println(propOwned[6] + houseString[6] + " . . . . . . . . . " + houseString[24] + propOwned[24]);
            System.out.println(propOwned[5] + houseString[5] + " . . . . . . . . . " + houseString[25] + propOwned[25]);
            System.out.println(" " + houseString[4] + " . . . . . . . . . " + houseString[26] + propOwned[26]);
            System.out.println(propOwned[3] + houseString[3] + " . . . . . . . . . " + houseString[27] + propOwned[27]);
            System.out.println(" " + houseString[2] + " . . . . . . . . . " + houseString[28] + propOwned[28]);
            System.out.println(propOwned[1] + houseString[1] + " . . . . . . . . . " + houseString[29] + propOwned[29]);
            System.out.println(" " + houseString[0] + " " + houseString[39] + " " + houseString[38] + " " + houseString[37] + " " + houseString[36] + " " + houseString[35] + " " + houseString[34] + " " + houseString[33] + " " + houseString[32] + " " + houseString[31] + " " + houseString[30]);
            System.out.println(" " + propOwned[0] + " " + propOwned[39] + " " + propOwned[38] + " " + propOwned[37] + " " + propOwned[36] + " " + propOwned[35] + " " + propOwned[34] + " " + propOwned[33] + " " + propOwned[32] + " " + propOwned[31]);
        }
        else if (p2Turn == true){
            System.err.println(" " + propOwned[10] + " " + propOwned[11] + " " + propOwned[12] + " " + propOwned[13] + " " + propOwned[14] + " " + propOwned[15] + " " + propOwned[16] + " " + propOwned[17] + " " + propOwned[18] + " " + propOwned[19]);
            System.err.println(" " + houseString[10] + " " + houseString[11] + " " + houseString[12] + " " + houseString[13] + " " + houseString[14] + " " + houseString[15] + " " + houseString[16] + " " + houseString[17] + " " + houseString[18] + " " + houseString[19] + " " + houseString[20]);
            System.err.println(propOwned[9] + houseString[9] + " . . . . . . . . . " + houseString[21] + propOwned[21]);
            System.err.println(propOwned[8] + houseString[8] + " . . . . . . . . . " + houseString[22]);
            System.err.println(" " + houseString[7] + " . . . . . . . . . " + houseString[23] + propOwned[23]);
            System.err.println(propOwned[6] + houseString[6] + " . . . . . . . . . " + houseString[24] + propOwned[24]);
            System.err.println(propOwned[5] + houseString[5] + " . . . . . . . . . " + houseString[25] + propOwned[25]);
            System.err.println(" " + houseString[4] + " . . . . . . . . . " + houseString[26] + propOwned[26]);
            System.err.println(propOwned[3] + houseString[3] + " . . . . . . . . . " + houseString[27] + propOwned[27]);
            System.err.println(" " + houseString[2] + " . . . . . . . . . " + houseString[28] + propOwned[28]);
            System.err.println(propOwned[1] + houseString[1] + " . . . . . . . . . " + houseString[29] + propOwned[29]);
            System.err.println(" " + houseString[0] + " " + houseString[39] + " " + houseString[38] + " " + houseString[37] + " " + houseString[36] + " " + houseString[35] + " " + houseString[34] + " " + houseString[33] + " " + houseString[32] + " " + houseString[31] + " " + houseString[30]);
            System.err.println(" " + propOwned[0] + " " + propOwned[39] + " " + propOwned[38] + " " + propOwned[37] + " " + propOwned[36] + " " + propOwned[35] + " " + propOwned[34] + " " + propOwned[33] + " " + propOwned[32] + " " + propOwned[31]);
        }
        time();
    }

    //Method to determine which community chest card a player or bot gets
    public static void communityChest(int[] noBuy, boolean buy)
    {
        //Declare the chest as a random integer
        chest = rand.nextInt(11)+1;

        //Switch statement to detect which card should be pulled out
        switch(chest)
        {
            case 1:
                //Bring the player or bot to the matrix spot
                if ((boardP1 == noBuy[1] || boardP1 == noBuy[5] || boardP1 == noBuy[9]) && p2Turn == false)
                {
                    System.out.println("Advance to the School Matrix and collect EC200");
                    time();
                    boardP1 = 0;
                    go = true;
                    passGo();
                }
                else if ((boardP2 == noBuy[1] || boardP2 == noBuy[5] || boardP2 == noBuy[9]) && p1Turn == false)
                {
                    System.err.println("Advance to the School Matrix and collect EC200");
                    time();
                    boardP2  =0;
                    go = true;
                    passGo();
                }
                break;
            case 2:
                //Deduct EC75 from the player or bot
                if ((boardP1 == noBuy[1] || boardP1 == noBuy[5] || boardP1 == noBuy[9]) && p2Turn == false)
                {
                    System.out.println("The Almighty Conway's magestic face is being carved into Mt.ICS. Pay EC75 to your coding god");
                    moneyP1 -= 75;
                }
                else if ((boardP2 == noBuy[1] || boardP2 == noBuy[5] || boardP2 == noBuy[9]) && p1Turn == false)
                {
                    System.err.println("The Almighty Conway's majestic face is being carved into Mt.ICS. Pay EC75 to your coding god");
                    moneyP2 -= 75;
                }
                time();
                break;
            case 3:
                //Add EC50 from the player or bot
                if ((boardP1 == noBuy[1] || boardP1 == noBuy[5] || boardP1 == noBuy[9]) && p2Turn == false)
                {
                    System.out.println("You got honor roll. Get EC50.00 from everyone");
                    moneyP1 += 50;
                }
                else if ((boardP2 == noBuy[1] || boardP2 == noBuy[5] || boardP2 == noBuy[9]) && p1Turn == false)
                {
                    System.err.println("You got honor roll, Get EC50.00 from everyone");
                    moneyP2 += 50;
                }
                time();
                break;
            case 4:
                //Make the player or bot go directly to jail
                if ((boardP1 == noBuy[1] || boardP1 == noBuy[5] || boardP1 == noBuy[9]) && p2Turn == false)
                {
                    System.out.println("Go directly to the office, don't collect EC200 if you pass the matrix, just go to the office");
                    boardP1 = 30;
                }
                else if ((boardP2 == noBuy[1] || boardP2 == noBuy[5] || boardP2 == noBuy[9]) && p1Turn == false)
                {
                    System.err.println("Go directly to the office, don't collect EC200 if you pass the matrix, just go to the office");
                    boardP1 = 30;
                }
                time();
                break;
            case 5:
                //Give the player or bot EC200
                if ((boardP1 == noBuy[1] || boardP1 == noBuy[5] || boardP1 == noBuy[9]) && p2Turn == false)
                {
                    System.out.println("Test marking error collect EC200.00");
                    moneyP1 += 200;
                }
                else if ((boardP2 == noBuy[1] || boardP2 == noBuy[5] || boardP2 == noBuy[9]) && p1Turn == false)
                {
                    System.err.println("Test marking error collect EC200.00");
                    moneyP2 += 200;
                }
                time();
                break;
            case 6:
                //Deduct EC150 from the player or bot
                if ((boardP1 == noBuy[1] || boardP1 == noBuy[5] || boardP1 == noBuy[9]) && !p2Turn)
                {
                    System.out.println("Pay student fees of EC150");
                    moneyP1 -= 150;
                }
                else if ((boardP2 == noBuy[1] || boardP2 == noBuy[5] || boardP2 == noBuy[9]) && !p1Turn)
                {
                    System.err.println("Pay student fees of EC150");
                    moneyP2 -= 150;
                }
                break;
            case 7:
                //Add EC15 to the player or bot's bank
                if ((boardP1 == noBuy[1] || boardP1 == noBuy[5] || boardP1 == noBuy[9]) && p2Turn == false)
                {
                    System.out.println("You won second prize in a Campion Talent Show, collect EC15");
                    moneyP1 += 15;
                }
                else if ((boardP2 == noBuy[1] || boardP2 == noBuy[5] || boardP2 == noBuy[9]) && p1Turn == false)
                {
                    System.err.println("You won second prize in a Campion Talent Show, collect EC15");
                    moneyP2 += 15;
                }
                break;
            case 8:
                //Add EC100 from the player's or bot bank
                if ((boardP1 == noBuy[1] || boardP1 == noBuy[5] || boardP1 == noBuy[9]) && p2Turn == false)
                {
                    System.out.println("Campion insurance matures, collect EC100");
                    moneyP1 += 100;
                }
                else if ((boardP2 == noBuy[1]) || (boardP2 == noBuy[5]) || (boardP2 == noBuy[9]) && p1Turn == false)
                {
                    System.err.println("Campion insurance matures, collect EC100");
                    moneyP2 += 100;
                }
                time();
                break;
            case 9:
                //Make the player or bot pay how the repairs for each house by using a for loop to add up the total houses and then multiplying by that by 25
                int totalCost = 0;

                if ((boardP1 == noBuy[1] || boardP1 == noBuy[5] || boardP1 == noBuy[9]) && p1Turn == true)
                {
                    System.out.println("You are assesed for classroom repairs. Pay EC25 for each chair owned by you and " + botName);

                    for (int i = 0;i < numHouse.length;i++)
                        totalCost += numHouse[i];

                    moneyP1 -= (25*totalCost);
                    totalCost = 0;
                }
                else if ((boardP2 == noBuy[1] || boardP2 == noBuy[5] || boardP2 == noBuy[9]) && p2Turn == true)
                {
                    System.err.println("You are assesed for classroom repairs. Pay EC25 for each chair owned by you and the player");

                    for (int i = 0;i < numHouse.length;i++)
                        totalCost += numHouse[i];

                    moneyP2 += 0;
                    totalCost = 0;
                }
                time();
                break;
            case 10:
                //Deduct EC100 from the player or bot
                if ((boardP1 == noBuy[1] || boardP1 == noBuy[5] || boardP1 == noBuy[9]) && p1Turn == true)
                {
                    System.out.println("Students council is caught for being really corrupt, you are the scape goat so pay fines of EC100");
                    moneyP1 -= 100;
                }
                else if ((boardP2 == noBuy[1] || boardP2 == noBuy[5] || boardP2 == noBuy[9]) && p2Turn == true)
                {
                    System.err.println("Student council is caught for being really corrupt, you are the scape goat so pay fines of EC100");
                    moneyP2 -= 100;
                }
                time();
                break;
            case 11:
                //Deduct EC60 from the player or bot
                if ((boardP1 == noBuy[1] || boardP1 == noBuy[5] || boardP1 == noBuy[9]) && p1Turn == true)
                {
                    System.out.println("The Church of Conway is suing you for saying \"The Conway is the Fake way\", pay EC60");
                    moneyP1 -= 60;
                }
                else if ((boardP2 == noBuy[1] || boardP2 == noBuy[5] || boardP2 == noBuy[9]) && p2Turn == true)
                {
                    System.err.println("The Church of Conway is suing you for saying \"The Conway is the Fake way\", pay EC60");
                    moneyP2 -= 60;
                }
                time();
                break;
        }

        return;
    }

    public static void chance(int[] fakes, boolean buy)
    {
        chance = rand.nextInt(11)+1;

        switch(chance)
        {
            case 1:
                //Deduct EC100 from the player or bot
                if ((boardP1 == fakes[3] || boardP1 == fakes[7] || boardP1 == fakes[10]) && (p1Turn == true))
                {
                    System.out.println("Oh no! The Data Twin Towers just crashed, pay EC100");
                    moneyP1 -= 100;
                }
                else if ((boardP2 == fakes[3] || boardP2 == fakes[7] || boardP2 == fakes[10]) && (p2Turn == true))
                {
                    System.err.println("Oh no! The Data Twin Towers just crashed, pay EC100");
                    moneyP2 -= 100;
                }
                time();
                break;
            case 2:
                //Make the player or bot move 3 spaces back
                if ((boardP1 == fakes[3] || boardP1 == fakes[7] || boardP1 == fakes[10]) && p1Turn == true)
                {
                    //Deduct 3 spaces from the players current spot
                    boardP1 -= 3;

                    System.out.println("Go back 3 places");
                    time();

                    //Call in the nonBuy method as the player goes to the income tax spot
                    if (boardP1 == (fakes[3]-3))
                        nonBuy(fakes, boardP1, buy);
                        //Make check buy true to make the player pay rent or buy the property
                    else if (boardP1 == (fakes[7]-3))
                    {
                        System.out.println("You are in  Kinesiology Park");
                        time();
                        checkBuy = true;
                    }
                    //Call in the nonBuy method as the player goes to a community chest spot
                    else if (boardP1 == (fakes[10]-3))
                        nonBuy(fakes, boardP1, buy);


                }
                //Same comments from player is also the same for the bot
                else if ((boardP2 == fakes[3] || boardP2 == fakes[7] || boardP2 == fakes[10]) && p2Turn == true)
                {
                    boardP2 -= 3;
                    System.err.println("Go back 3 places");
                    time();

                    if (boardP2 == (fakes[3]-3))
                        nonBuy(fakes, boardP2, buy);
                    else if (boardP2 == (fakes[7]-3))
                    {
                        System.err.println("You are in  Kinesiology Park");
                        time();
                        checkBuy = true;
                    }
                    else if (boardP2 == (fakes[10]-3))
                        nonBuy(fakes, boardP2, buy);
                }
                break;
            case 3:
                //Deduct and add money depending on who gets this chacnce card
                if ((boardP1 == fakes[3] || boardP1 == fakes[7] || boardP1 == fakes[10]) && p1Turn == true)
                {
                    System.out.println("You have been elected as the EC student council. pay the enemy EC150");
                    moneyP1 -= 150;
                    moneyP2 += 150;
                }
                else if ((boardP2 == fakes[3] || boardP2 == fakes[7] || boardP2 == fakes[10]) && p2Turn == true)
                {
                    System.err.println("You have been elected as the EC student council. pay the enemy EC150");
                    moneyP2 -= 150;
                    moneyP1 += 150;
                }
                time();
                break;
            case 4:
                //Bring the player or bot to the computer club subway station. Make them collect EC200 by going to the method passGo
                if ((boardP1 == fakes[3] || boardP1 == fakes[7] || boardP1 == fakes[10]) && p1Turn == true)
                {
                    System.out.println("Go to Computer Club Subway Station, if you pass go collect EC200");
                    time();
                    if (boardP1 == fakes[10])
                    {
                        go = true;
                        passGo();
                    }
                    boardP1 = 35;
                    checkBuy = true;
                }
                else if ((boardP2 == fakes[3] || boardP2 == fakes[7] || boardP2== fakes[10]) && p2Turn == true) {
                    System.err.println("Go to Computer Club Subway Station, if you pass go collect EC200");
                    time();
                    boardP2 = 35;
                    if (boardP2 == fakes[10])
                    {
                        go = true;
                        passGo();
                    }
                    boardP2 = 35;
                    checkBuy = true;
                }
                break;
            case 5:
                //bring the player or bot to mt. ICS
                if ((boardP1 == fakes[3] || boardP1 == fakes[7] || boardP1 == fakes[10]) && p1Turn == true)
                {
                    System.out.println("Advance to Mt. ICS");
                    boardP1 = 39;
                    checkBuy = true;
                }
                else if ((boardP2 == fakes[3] || boardP2 == fakes[7] || boardP2 == fakes[10]) && p2Turn == true)
                {
                    System.err.println("Advance to Mt. ICS");
                    boardP2 = 39;
                    checkBuy = true;
                }
                time();
                break;
            case 6:
                //Bring the player or bot to the nearest transportation spot
                if ((boardP1 == fakes[3] || boardP1 == fakes[7] || boardP1 == fakes[10]) && p1Turn == true)
                {
                    System.out.println("Go to the nearest transportation service. If it is unowned, you may buy the service");
                    time();
                    if (boardP1 == fakes[3])
                    {
                        System.out.println("You are in the Art Club Campion City Tour Bus Service");
                        boardP1 = 15;
                    }
                    else if (boardP1 == fakes[7])
                    {
                        System.out.println("You are in the Science Club Rent-a-car Service");
                        boardP1 = 25;
                    }
                    else if (boardP1 == fakes[10])
                    {
                        go = true;
                        passGo();
                        System.out.println("You are in the Student Council Taxi Service");
                        boardP1 = 5;
                    }
                    time();
                    checkBuy = true;
                }
                else if ((boardP2 == fakes[3] || boardP2 == fakes[7] || boardP2 == fakes[10]) && p2Turn == true)
                {
                    System.err.println("Go to the nearest transportation service. If it is unowned, you may buy the service");
                    time();
                    if (boardP2 == fakes[3])
                    {
                        System.err.println("You are in the Art Club Campion City Tour Bus Service");
                        boardP2 = 15;
                    }
                    else if (boardP2 == fakes[7])
                    {
                        System.err.println("You are in the Science Club Rent-a-car Service");
                        boardP2 = 25;
                    }
                    else if (boardP2 == fakes[10])
                    {
                        go = true;
                        passGo();
                        System.err.println("You are in the Student Council Taxi Service");
                        boardP2 = 5;
                    }
                    time();
                    checkBuy = true;
                }
                break;
            case 7:
                //Add money to player or bot
                if ((boardP1 == fakes[3] || boardP1 == fakes[7] || boardP1 == fakes[10]) && p1Turn == true)
                {
                    System.out.println("The Guidance accidentally accused you of causing a stabbing. Collect EC100");
                    moneyP1 += 100;
                }
                else if ((boardP2 == fakes[3] || boardP2 == fakes[7] || boardP2 == fakes[10]) && p2Turn == true)
                {
                    System.err.println("The Guidance accidentally accused you of causing a stabbing. Collect EC100");
                    moneyP2 += 100;
                }
                break;
            case 8:
                //Deduct money from the player or bot
                if ((boardP1 == fakes[3] || boardP1 == fakes[7] || boardP1 == fakes[10]) && p1Turn == true)
                {
                    System.out.println("Pay taxes of EC15 for failing a course");
                    moneyP1 -= 15;
                }
                else if ((boardP2 == fakes[3] || boardP2 == fakes[7] || boardP2 == fakes[10]) && p2Turn == true)
                {
                    System.err.println("Pay taxes of EC15 for failing a course");
                    moneyP2 -= 15;
                }
                break;
            case 9:
                //Bring the player or bot to the nearest service station
                if ((boardP1 == fakes[3] || boardP1 == fakes[7] || boardP1 == fakes[10]) && p1Turn == true)
                {
                    System.out.println("Go to the nearest Sports company. If it is unowned, you may buy the company");
                    time();
                    if (boardP1 == fakes[3])
                    {
                        System.out.println("You are in Hockey Team Inc");
                        boardP1 = 12;
                    }
                    else if (boardP1 == fakes[7])
                    {
                        System.out.println("You are in BasketBall Team Co.");
                        boardP1 = 28;
                    }
                    else if (boardP1 == fakes[10])
                    {
                        go = true;
                        passGo();
                        boardP1 = 12;
                        System.out.println("You are in Hockey Team Inc");
                    }
                    time();
                    checkBuy = true;
                }
                else if ((boardP2 == fakes[3] || boardP2 == fakes[7] || boardP2 == fakes[10]) && p2Turn == true)
                {
                    System.err.println("Go to the nearest Sports company. If it is unowned, you may buy the company");
                    time();
                    if (boardP2 == fakes[3])
                    {
                        System.err.println("You are in Hockey Team Inc");
                        boardP2 = 12;
                    }
                    else if (boardP2 == fakes[7])
                    {
                        System.err.println("You are in BasketBall Team Co.");
                        boardP2 = 28;
                    }
                    else if (boardP1 == fakes[10])
                    {
                        go = true;
                        passGo();
                        System.err.println("You are in Hockey Team Inc");
                        boardP2 = 12;
                    }
                    time();
                    checkBuy = true;
                }
                break;
            case 10:
                //Bring the player or bot to the weight room arena
                if ((boardP1 == fakes[3] || boardP1 == fakes[7] || boardP1 == fakes[10]) && p1Turn == true)
                {
                    System.out.println("Advance to the Weight Room Arena, collect EC200 if you pass the matrix");
                    time();
                    boardP1 = 18;
                    if (boardP1 != fakes[3])
                    {
                        go = true;
                        passGo();
                    }
                }
                else if ((boardP2 == fakes[3] || boardP2 == fakes[7] || boardP2 == fakes[10]) && p2Turn == true)
                {
                    System.err.println("Advance to the Weight Room Arena, collect EC200 if you pass the matrix");
                    time();
                    boardP2 = 18;
                    if (boardP2 != fakes[3])
                    {
                        go = true;
                        passGo();
                    }
                }
                checkBuy = true;
                break;
            case 11:
                //Add money to the player or bot
                if ((boardP1 == fakes[3] || boardP1 == fakes[7] || boardP1 == fakes[10]) && p1Turn == true)
                {
                    System.out.println("The Almighty Conway has come to bless you, you get EC250");
                    moneyP1 += 250;
                }
                else if ((boardP2 == fakes[3] || boardP2 == fakes[7] || boardP2 == fakes[10]) && p2Turn == true)
                {
                    System.err.println("The Almighty Conway has come to bless you, you get EC250");
                    moneyP2 += 250;
                }
                time();
                break;
        }

        return;
    }

    //Method to detect if the player or bot is in a non-purchasable spot
    public static void nonBuy(int[]fakes, int boardSpot, boolean buy)
    {
        //Check if the spot is in the matrix
        if (boardSpot == fakes[0])
        {
            if (boardP1 == fakes[0] && p1Turn == true)
                System.out.println("You are in the matrix");
            else if (boardP2 == fakes[0] && p2Turn == true)
                System.err.println("You are in the matrix");
            time();
        }
        //Check if the spot is in a community chest spot and call in the community chest method
        else if (boardSpot == fakes[1] || boardSpot == fakes[5] || boardSpot == fakes[9])
        {
            if ((boardP1 == fakes[1] || boardP1 == fakes[5] || boardP1 == fakes[9]) && p1Turn == true)
                System.out.println("You landed on the Campion community chest, so pick a random community chest card");
            else if ((boardP2 == fakes[1] || boardP2 == fakes[5] || boardP2 == fakes[9]) && p2Turn == true)
                System.err.println("You landed on the Campion community chest, so pick a random community chest card");
            time();

            communityChest(fakes, buy);
        }
        //Deduct money if they are in this spot
        else if (boardSpot == fakes[2])
        {
            if (boardP1 == fakes[2] && p1Turn == true)
            {
                System.out.println("Forgot to return books in time, pay a fine of EC200");
                moneyP1 -= 200;
            }
            else if (boardP2 == fakes[2] && p2Turn == true)
            {
                System.err.println("Forgot to return books in time, pay a fine of EC200");
                moneyP2 -= 200;
            }
            time();
        }
        //Go to chance method if they are in this spot
        else if (boardSpot == fakes[3] || boardSpot == fakes[7] || boardSpot == fakes[10])
        {
            if ((boardP1 == fakes[3] || boardP1 == fakes[7] || boardP1 == fakes[10]) && p1Turn == true)
                System.out.println("You landed on a free credit lane, so pick a random chance card");
            else if ((boardP2 == fakes[3] || boardP2 == fakes[7] || boardP2 == fakes[10]) && p2Turn == true)
                System.err.println("You landed on a free credit lane, so pick a random chance card");
            time();

            chance(fakes, buy);
        }
        //Just tell the player or bot they just visiting the office
        else if (boardSpot == fakes[4])
        {
            if (boardP1 == fakes[4] && p1Turn == true)
                System.out.println("You are visiting the office");
            else if (boardP2 == fakes[4] && p2Turn == true)
                System.err.println("You are visiting the office");
            time();
        }
        //Tell the player or bot they're in a parking lot
        else if (boardSpot == fakes[6])
        {
            if (boardP1 == fakes[6] && p1Turn == true)
                System.out.println("P.A day parking lot, just relax and don't pay rent at all");
            else if (boardP2 == fakes[6] && p2Turn == true)
                System.err.println("P.A day parking lot, just relax and don't pay rent at all");
            time();
        }
        //Deduct money from the player or bot
        else if (boardSpot == fakes[11])
        {
            if (boardP1 == fakes[11] && p1Turn == true)
            {
                System.out.println("Pay EC100 for not paying student services");
                moneyP1 -= 100;
            }
            else if (boardP2 == fakes[11] && p2Turn == true)
            {
                System.err.println("Pay EC100 for not paying student services");
                moneyP2 -= 100;
            }
            time();
        }

        return;
    }

    //Method to determine the winner of the game
    public static void winner()
    {
        //Check if the bot loss
        if ((botOwned.size() == 0 && moneyP2 < 0))
            winner = true;

        //Check if winner is true  and set a counter so things won't print out twice
        if (winner == true && counter == 0)
        {

            //Print out the winner of the game
            if (moneyP1 < 0 && arrayOwned.size() == 0)
            {
                System.out.println("You lost the game, "  + botName + " won the game");
            }
            else if (moneyP2 < 0 && !botName.equalsIgnoreCase("Conway") && botOwned.size() == 0)
            {
                System.err.println("YOU WON THE GAME CONGRATULATIONS. WANT A PAT IN THE BACK");
            }
            //Easter Egg
            else if (botName.equalsIgnoreCase("Conway"))
            {
                System.out.println("The game is over its a given fact that there is no way, that there will ever be a day that the Conway may lose its way");
            }

            time();
            time();
            time();
        }

        counter = 1;
        System.exit(0);
    }

    //Timer method. Each time you see the time(), it calls this method so the program can sleep for 0.5 a second
    public static void time()
    {
        try
        {
            Thread.sleep(500);
        }
        catch (Exception e)
        {

        }
    }
}
