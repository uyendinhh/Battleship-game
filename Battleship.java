import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.*;

public class Battleship {

    public static char[][] initBoard(int n) {
        char[][] board = new char[2*n][n];
        for (int i = 0; i < 2*n; i++) {
            for (int j = 0; j < n; j++) {
                board[i][j] = '~';
            }
        }
        return board;
    }

    public static void printBoard(int playerTurn, char[][] board,
        int hitsLeft) {
        if (playerTurn % 2 != 0) { //print player 1's board
        System.out.println("Player 2 (" + hitsLeft + " hits left):");
            for (int i = 0; i < board.length/2 ; i++){
                for (int j = 0; j < board.length/2 ; j++){
                    System.out.print(board[i][j]);
                }
                System.out.println();
            }
        }
        else {//print player 2's board
            System.out.println("Player 1 (" + hitsLeft + " hits left):");
            for (int i = board.length/2 ; i < board.length; i++){
                for (int j = 0; j < board.length/2; j++){
                    System.out.print(board[i][j]);
                }
                System.out.println();
            }
        }
    }

    public static int fireMissile(char[][] board, String target,
        String[] shipLocations, int hitsLeft) {
        int[] loc_toChange = new int[2];
        loc_toChange = convertLocation(target);
        if (isShip(target, shipLocations) == true) {
            System.out.println("Hit!");
            board[loc_toChange[1]][loc_toChange[0]] = 'X';
            board[0][0] = 'x';
            System.out.println(board[0][0]);
        }
        else {
            System.out.println("Miss!");
            board[loc_toChange[1]][loc_toChange[0]] = 'O';   
        }
        return hitsLeft;
    }

    public static boolean isShip(String target, String[] shipLocations) {
        boolean areEqual;
        for (int i = 0; i < shipLocations.length; i++){
            areEqual = Arrays.equals(convertLocation(target), convertLocation(shipLocations[i]));
            if (areEqual == true){
                i = shipLocations.length;
                return true; 
            }
        }
        return false;
    }

    public static int[] convertLocation(String coordinate) {
        int[] location = new int[2];
        location[1] = coordinate.charAt(0) - 'a';
        location[0] = coordinate.charAt(1) - '1';
        return location;
    }

    public static void main(String[] args) {
        int fileInd = (args.length > 0) ? Integer.parseInt(args[0])
            : new Random().nextInt(4);

        String filename = "game1.txt";
        int playerTurn = 0;
        String input_str = "";
        try {
            Scanner fileReader = new Scanner(new File(filename));
            Scanner inputScanner = new Scanner(System.in);
            int dimension = fileReader.nextInt();

            char[][] board = initBoard(dimension);

            while (fileReader.hasNext()) {
                input_str = fileReader.nextLine() + " " + input_str;
            }
            String[] shipLocations = input_str.split(" ");
            int hitsLeft = (shipLocations.length)/2;
            if (hitsLeft >= 0){
                printBoard(playerTurn, board, hitsLeft);
                System.out.print("Player 1 enter missile location: ");
                String target = inputScanner.next();
                fireMissile(board,target,shipLocations,hitsLeft);
                hitsLeft--;
                printBoard(playerTurn,board,hitsLeft);
                System.out.println();
                System.out.println("----------");

                //System.out.println("Player 1(" + hitsLeft + " hitsleft):");

            }
            
        } catch (FileNotFoundException e) {
            System.out.println("Make sure that " + filename
                + " is in the current directory!");
        }

    }
}