package minesweeper;

import java.util.Scanner;

public class Driver {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("\nEnter numRows : ");
        int numRows = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter numColumns : ");
        int numCols = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter bomb probability : ");
        double bombProb = Double.parseDouble(scanner.nextLine());

        //Game loop
        MineSweeper mineSweeper = new MineSweeper(numRows, numCols, bombProb);

        while(!mineSweeper.gameEnded()){
            mineSweeper.printGameState();
            System.out.print("\nEnter a row: ");
            int row = Integer.parseInt(scanner.nextLine());
            System.out.print("Enter a column: ");
            int col = Integer.parseInt(scanner.nextLine());
            mineSweeper.process(row, col);
        }

    }

}
