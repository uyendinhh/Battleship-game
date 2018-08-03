import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.util.Random;

/**
 *  Build a Battleship game in which player's task
 *  is to sink the other's ship.
 *  Players can only have one guess per turn
 *  to find the opposing player's ships.
 *  The game ends when one player find all of their
 *  opponent's ships
 *  @author udinh3
 *  @version 2
 */

public class Battleship {

    /**
     * Create a initBoard that take n as a parameter
     * the board will have size n x n
     * @param n the dimension
      of the players' board
     *
     * @return board a 2D array
     *
     */

    public static char[][] initBoard(int n) {
        char[][] board = new char[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                board[i][j] = '~';
            }
        }
        return board;
    }

    /**
     * Create a printBoard to print the players' boards
     *
     * @param playerTurn an integer to decide which player is playing
     * @param board the 2D array of the player
     * @param hitsLeft the integer indicates the amount of ships
     *        the player has to guess in order to win
     *
     */
    public static void printBoard(int playerTurn, char[][] board,
        int hitsLeft) {
        if (playerTurn % 2 == 0) { //print player 1's board
            System.out.println("Player 1 (" + hitsLeft + " hits left):");
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board.length; j++) {
                    System.out.print(board[i][j] + " ");
                }
                System.out.println();
            }
        } else { //print player 2's board
            System.out.println("Player 2 (" + hitsLeft + " hits left):");
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board.length; j++) {
                    System.out.print(board[i][j] + " ");
                }
                System.out.println();
            }
        }
    }

    /**
     * Create a fireMissile to decide whether the guess of
     * the player is right or wrong
     *
     * @param board a 2D array of the current player
     * @param target the current guess of the player
     * @param shipLocations the String contains
     *        the opposing ship locations
     * @param hitsLeft an integer indicates
     *        the number of ships the player has to guess
     *
     * @return hitsLeft an integer
     */
    public static int fireMissile(char[][] board, String target,
        String[] shipLocations, int hitsLeft) {
        int[] loctoChange = new int[2];
        loctoChange = convertLocation(target);
        if (isShip(target, shipLocations)) {
            System.out.println("Hit!");
            board[loctoChange[1]][loctoChange[0]] = 'X';
            hitsLeft--;
            return hitsLeft;
        } else {
            System.out.println("Miss!");
            board[loctoChange[1]][loctoChange[0]] = 'O';
            return hitsLeft;
        }
    }

    /**
     * Create an isShip method to decide if the current guess is right
     *
     * @param target the current guess of the player
     * @param shipLocations the String contains the opposing ship locations
     *
     * @return boolean true if the guess is correct
     *         false if the guess is incorrect
     */
    public static boolean isShip(String target, String[] shipLocations) {
        boolean areEqual;
        for (int i = 0; i < shipLocations.length; i++) {
            areEqual = convertLocation(target)[0]
                == convertLocation(shipLocations[i])[0]
                    && convertLocation(target)[1]
                        == convertLocation(shipLocations[i])[1];
            if (areEqual) {
                return true;
            }
        }
        return false;
    }

    /**
     * Create a convertLocation to convert the location to an array integer
     * @param coordinate the String contains the locations in letter and number
     *
     * @return location an integer array
     *
     */

    public static int[] convertLocation(String coordinate) {
        int[] location = new int[2];
        location[1] = coordinate.charAt(0) - 'a';
        location[0] = coordinate.charAt(1) - '1';
        return location;
    }

    /**
     * Create a main method to obtain the input guesses,
     * each player will have certain amount of hits
     * @param args an argument takes game1.txt ... as parameter
     *
     */

    public static void main(String[] args) {
        int fileInd = (args.length > 0) ? Integer.parseInt(args[0])
            : new Random().nextInt(4);

        String filename = "game" + fileInd + ".txt";
        int playerTurn = 0;
        String inputString = "";
        StringBuilder inputAr1 = new StringBuilder();
        StringBuilder inputAr2 = new StringBuilder();
        try {
            Scanner fileReader = new Scanner(new File(filename));
            Scanner inputScanner = new Scanner(System.in);
            int dimension = fileReader.nextInt();
            char[][] board1 = initBoard(dimension);
            char[][] board2 = initBoard(dimension);

            while (fileReader.hasNext()) {
                inputString = inputString + fileReader.next() + " ";
            }
            String[] shipLocations = inputString.split(" ");
            int hitsLeft1 = (shipLocations.length) / 2;
            int hitsLeft2 = (shipLocations.length) / 2;
            String[] subArr = new String[shipLocations.length / 2];

            while (hitsLeft1 > 0 && hitsLeft2 > 0) { //if one player finds
                                                     //all the ships,
                                                     // the game will end
                if (playerTurn % 2 == 0) { //player 1 turn
                    printBoard(playerTurn, board1, hitsLeft1);
                    System.out.print("Enter missile location: ");
                    String target = inputScanner.next().toLowerCase();
                    for (int i = 0; i < subArr.length; i++) {
                        subArr[i] = shipLocations[i];
                    }
                    if (inputAr1.indexOf(target) >= 0) {
                        System.out.println(target + " has already been chosen");
                    } else {
                        hitsLeft1 = fireMissile(board1, target,
                            subArr, hitsLeft1);
                    }
                    inputAr1.append(target);
                    printBoard(playerTurn, board1, hitsLeft1);
                } else { //player 2's turn
                    printBoard(playerTurn, board2, hitsLeft2);
                    System.out.print("Enter missile location: ");
                    String target = inputScanner.next().toLowerCase();
                    for (int i = 0; i < subArr.length; i++) {
                        subArr[i] = shipLocations[subArr.length + i];
                    }
                    if (inputAr2.indexOf(target) >= 0) {
                        System.out.println(target + " has already been chosen");
                    } else {
                        hitsLeft2 = fireMissile(board2,
                            target, subArr, hitsLeft2);
                    }
                    inputAr2.append(target);
                    printBoard(playerTurn, board2, hitsLeft2);
                }
                playerTurn++;
                System.out.println();
                System.out.println();
                System.out.println("----------");
                System.out.println();
                System.out.println();
            }
            if (hitsLeft1 < hitsLeft2) {
                System.out.println("The winner is Player 1");
            } else {
                System.out.println("The winner is Player 2");
            }
        } catch (FileNotFoundException e) {
            System.out.println("Make sure that " + filename
                + " is in the current directory!");
        }
    }
}
