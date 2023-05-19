import java.util.Random;

public class SudokuGenerator {
    public int[][] grid;
    public int size;
    public int sqrtSize;
    public int missingDigits;

    public SudokuGenerator(int size, int missingDigits) {
        this.size = size;
        this.sqrtSize = (int) Math.sqrt(size);
        this.missingDigits = missingDigits;
        this.grid = new int[size][size];
    }

    public void generate() {
        fillDiagonal();
        fillRemaining(0, sqrtSize);
        removeDigits();
    }

    private void fillDiagonal() {
        for (int i = 0; i < size; i += sqrtSize) {
            fillBox(i, i);
        }
    }

    private void fillBox(int row, int col) {
        int num;
        for (int i = 0; i < sqrtSize; i++) {
            for (int j = 0; j < sqrtSize; j++) {
                do {
                    num = (int) Math.floor((Math.random() * size + 1));
                } while (!isValid(row, col, num));
                grid[row + i][col + j] = num;
            }
        }
    }

    private boolean isValid(int row, int col, int num) {
        for (int i = 0; i < size; i++) {
            if (grid[row][i] == num || grid[i][col] == num) {
                return false;
            }
        }
        int startRow = row - row % sqrtSize;
        int startCol = col - col % sqrtSize;
        for (int i = 0; i < sqrtSize; i++) {
            for (int j = 0; j < sqrtSize; j++) {
                if (grid[startRow + i][startCol + j] == num) {
                    return false;
                }
            }
        }
        return true;
    }

    private void fillRemaining(int row, int col) {
        if (col >= size && row < size - 1) {
            row++;
            col = 0;
        }
        if (row >= size && col >= size) {
            return;
        }
        if (row < sqrtSize) {
            if (col < sqrtSize) {
                col = sqrtSize;
            }
        } else if (row < size - sqrtSize) {
            if (col == (row / sqrtSize) * sqrtSize) {
                col += sqrtSize;
            }
        } else {
            if (col == size - sqrtSize) {
                row++;
                col = 0;
                if (row >= size) {
                    return;
                }
            }
        }
        for (int num = 1; num <= size; num++) {
            if (isValid(row, col, num)) {
                grid[row][col] = num;
                fillRemaining(row, col + 1);
                if (isGridFilled()) {
                    return;
                }
                grid[row][col] = 0;
            }
        }
    }

    private boolean isGridFilled() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (grid[i][j] == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    private void removeDigits() {
        int count = missingDigits;
        Random random = new Random();
        while (count > 0) {
            int row = random.nextInt(size);
            int col = random.nextInt(size);
            if (grid[row][col] != 0) {
                grid[row][col] = 0;
                count--;
            }
        }
    }
}
