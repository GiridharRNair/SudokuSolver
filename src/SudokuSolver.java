/**
 * This class represents a Sudoku solver.
 * Name: Giridhar Nair
 * Comments produced by https://syntaxwarrior30.github.io/Documentation-Wizard/
 */
public class SudokuSolver {
    private int[][] board; // the Sudoku puzzle to solve

    /**
     * Constructs a new Sudoku solver with the given board.
     * @param board the Sudoku puzzle to solve
     */
    public SudokuSolver(int[][] board) {
        this.board = board;
    }

    /**
     * Attempts to solve the Sudoku puzzle.
     * @return true if the puzzle is solved, false if it cannot be solved within 250 milliseconds
     */
    public boolean solve() {
        long start = System.currentTimeMillis();
        long end = start + 1 * 250;
        int row = -1;
        int col = -1;
        boolean isEmpty = true;
        // find the first empty cell in the board
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j] == 0) {
                    row = i;
                    col = j;
                    isEmpty = false;
                    break;
                }
                if (board[i][j] > 9 || board[i][j] < 0)
                	return false;
            }
            if (!isEmpty)
                break;
        }
        if (isEmpty) // the board is filled
            return true;
        // try every possible number at this cell
        for (int num = 1; num <= 9; num++) {
            if (System.currentTimeMillis() > end)
                break;
            if (isValid(row, col, num)) { // if the number is valid
                board[row][col] = num; // set the cell to the number
                if (solve())
                    return true; // recursively solve the board
                board[row][col] = 0; // backtrack
            }
        }
        return false; // cannot solve the board
    }

    /**
     * Checks if the given number can be placed at the given cell.
     * @param row the row of the cell
     * @param col the column of the cell
     * @param num the number to check
     * @return true if the number is valid for the cell, false otherwise
     */
    private boolean isValid(int row, int col, int num) {
        // check row
        for (int j = 0; j < 9; j++)
            if (board[row][j] == num)
                return false;
        // check column
        for (int i = 0; i < 9; i++)
            if (board[i][col] == num)
                return false;
        // check sub-grid
        int boxRow = row - row % 3;
        int boxCol = col - col % 3;
        for (int i = boxRow; i < boxRow + 3; i++)
            for (int j = boxCol; j < boxCol + 3; j++)
                if (board[i][j] == num)
                    return false;
        return true;
    }

    /**
     * Gets the value of the cell at the given coordinates.
     * @param x the row of the cell
     * @param y the column of the cell
     * @return the integer value of the cell
     */
    public int getValue(int x, int y) {
        return board[x][y];
    }
    
    /**
     * Gets the completed state of the Sudoku Board.
     * @return int matrix of the Sudoku Board
     */
    public int[][] getBoard() {
    	return board;
    }
}