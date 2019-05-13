package algorithm.backtracking.eight_queens_puzzle;

public class EightQueensPuzzle {
    private int[] results = new int[8];

    public void placeQueenAtRow(int row) {
        if (row == 8) {
            print();
            return;
        }

        for (int col = 0; col < 8; col++) {
            if (isValidQueenAt(row, col)) {
                results[row] = col;
                placeQueenAtRow(row + 1);
            }
        }
    }

    private boolean isValidQueenAt(int row, int col) {
        int leftUp = col - 1;
        int rightUp = col + 1;

        for (int i = row - 1; i >= 0; i--) {
            if (results[i] == col) {
                return false;
            }

            if (leftUp >= 0) {
                if (results[i] == leftUp) {
                    return false;
                }
            }

            if (rightUp < 8) {
                if (results[i] == rightUp) {
                    return false;
                }
            }

            leftUp--;
            rightUp++;
        }

        return true;
    }

    private void print() {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if (results[row] == col) {
                    System.out.print("Q ");
                } else {
                    System.out.print("- ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void main(String[] args) {
        new EightQueensPuzzle().placeQueenAtRow(0);
    }
}
