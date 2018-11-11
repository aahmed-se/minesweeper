package minesweeper;

public class MineSweeper {

    private int numRows;
    private int numCols;
    private double bombProp;
    private int[][] gameGrid;
    private int[][] solVal;
    private boolean gameEnded;
    private boolean gameStarted;
    private int numOfBombs;

    public MineSweeper(int numRows, int numCols, double bombProp){
        this.numRows = numRows;
        this.numCols = numCols;
        this.bombProp = bombProp;
        this.gameGrid = new int[this.numRows + 2][this.numCols + 2];
        this.solVal = new int[this.numRows + 2][this.numCols + 2];
        this.gameStarted = true;

    }

    private void initBombs(int row, int col){

        for (int i = 1; i <= numRows; i++){
            for (int j = 1; j <= numCols; j++){

                if(i != row && j != col){
                    if(Math.random() < bombProp){
                        gameGrid[i][j] = -1;
                        numOfBombs++;
                    }
                }

            }

        }

        if(numOfBombs == 0 ){
            System.out.println("Error no bombs computed with probability");
            this.gameEnded = true;
        }

        for (int i = 1; i <= numRows; i++)
            for (int j = 1; j <= numCols; j++)
                for (int ii = i - 1; ii <= i + 1; ii++)
                    for (int jj = j - 1; jj <= j + 1; jj++)
                        if (gameGrid[ii][jj] < 0) solVal[i][j]++;

    }


    public void printGameState() {

        System.out.println();
        System.out.print("\t");
        for (int i = 1; i <= numCols; i++) {
            System.out.print(i + "\t");
        }

        System.out.println();

        for (int i = 1; i <= numRows; i++) {
            System.out.print(i + "\t");
            for (int j = 1; j <= numCols; j++) {
                if (gameGrid[i][j] == 0 || gameGrid[i][j] == -1) {
                    System.out.print("H");
                }
                else if (gameGrid[i][j] == -2) {
                    System.out.print("X");
                }
                else if (solVal[i][j] > 0){
                    System.out.print(solVal[i][j]);
                }
                else {
                    System.out.print(".");
                }
                System.out.print("\t");
            }
            System.out.println();
        }

    }

    public boolean gameEnded() {
        return gameEnded;
    }

    public void process(int row, int col) {

        if(row > numRows || col > numCols){
            printDebug();
            return;
        }

        if(gameStarted){
            initBombs(row, col);
            gameStarted = false;
        }

        checkMove(row, col);

    }

    private void printDebug() {
        System.out.println();
        System.out.print("\t");
        for (int i = 1; i <= numCols; i++) {
            System.out.print(i + "\t");
        }

        System.out.println();

        for (int i = 1; i <= numRows; i++) {
            System.out.print(i + "\t");
            for (int j = 1; j <= numCols; j++) {
                if (gameGrid[i][j] == -1) {
                    System.out.print("X");
                }
                else if (gameGrid[i][j] == -2) {
                    System.out.print(".");
                }
                System.out.print("\t");
            }
            System.out.println();
        }


    }

    private void checkMove(int row, int col) {

        if(gameGrid[row][col] == -1){
            gameGrid[row][col] = -2;
            System.out.println("Game Ended, Struck Bomb");
            printGameState();
            gameEnded = true;
        } else {
            gameGrid[row][col] = 1;
            neighbourFloodFill(row - 1, col);
            neighbourFloodFill(row + 1, col);
            neighbourFloodFill(row, col - 1);
            neighbourFloodFill(row, col + 1);
            checkWin();
        }

    }

    private void checkWin() {

        int leftCells = 0;

        for(int i = 1; i<= numRows; ++i) {
            for (int j = 1; i <= numCols; ++i) {
                if(gameGrid[i][j] == 0){
                    leftCells++;
                }
            }
        }

        if(leftCells == 0){
            System.out.println("Game Ended you won");
            this.printGameState();
            this.printDebug();
            gameEnded = true;
        }

    }

    private void neighbourFloodFill(int row, int col) {

        if(row >= 1 && row <= numRows && col >=1 && col <= numCols){

            if(gameGrid[row][col] != -1 && gameGrid[row][col] != 1){
                gameGrid[row][col] = 1;
                if(solVal[row][col] == 0){
                    neighbourFloodFill(row - 1, col);
                    neighbourFloodFill(row + 1, col);
                    neighbourFloodFill(row, col - 1);
                    neighbourFloodFill(row, col + 1);
                }
            }
        }

    }
}