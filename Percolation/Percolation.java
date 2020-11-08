import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private final int n;
    private final boolean[][] grid;
    private int openSitesCount;
    private final WeightedQuickUnionUF quickUnionUF;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("Parameter n cannot be less than 1");
        }

        this.n = n;
        grid = new boolean[n][n];
        quickUnionUF = new WeightedQuickUnionUF(n * n + 2); // grid + top'n'bottom elements (top - first, bottom - last)
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row < 1 || row > n || col < 1 || col > n) {
            throw new IllegalArgumentException("Parameters row and col cannot be less than 1 and more than n");
        }

        if (isOpen(row, col)) {
            return;
        }

        openSitesCount++;
        int elementNumber = getElementNum(row, col);
        grid[row - 1][col - 1] = true;

        // Join top and bottoms
        if (row == 1) {
            quickUnionUF.union(0, elementNumber); // join the top element
        } else if (row == n) {
            quickUnionUF.union(n * n + 1, elementNumber); // join the bottom element
        }

        // Check neighbours
        if (row > 1 && isOpen(row - 1, col)) {
            int neighbourNumber = ((row - 1) - 1) * n + col; // row - 1 = current row index, next - 1 = previous row
            quickUnionUF.union(neighbourNumber, elementNumber); // top
        }
        if (row < n && isOpen(row + 1, col)) {
            int neighbourNumber = ((row - 1) + 1) * n + col; // row - 1 = current row index, next + 1 = next row
            quickUnionUF.union(neighbourNumber, elementNumber); // bottom
        }
        if (col > 1 && isOpen(row, col - 1)) {
            int neighbourNumber = (row - 1) * n + col - 1; // row - 1 = current row index, col - 1 = previous column
            quickUnionUF.union(neighbourNumber, elementNumber); // left
        }
        if (col < n && isOpen(row, col + 1)) {
            int neighbourNumber = (row - 1) * n + col + 1; // row - 1 = current row index, col + 1 = next column
            quickUnionUF.union(neighbourNumber, elementNumber); // right
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row < 1 || row > n || col < 1 || col > n) {
            throw new IllegalArgumentException("Parameters row and col cannot be less than 1 and more than n");
        }

        return grid[row - 1][col - 1]; // locked sites is 0, opened sites contains their sequence number starting from 1
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row < 1 || row > n || col < 1 || col > n) {
            throw new IllegalArgumentException("Parameters row and col cannot be less than 1 and more than n");
        }

        return quickUnionUF.find(getElementNum(row, col)) == quickUnionUF.find(0); // this element is connected to the top element
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openSitesCount;
    }

    // does the system percolate?
    public boolean percolates() {
        return quickUnionUF.find(n * n + 1) == quickUnionUF.find(0); // the bottom element is connected to the top element
    }

    // additional method for element sequence number extraction
    private int getElementNum(int row, int col) {
        return (row - 1) * n + (col - 1) + 1; // start elements count from 1 in [0;0] position
    }

    // test client (optional)
    public static void main(String[] args) {
        // Params
        int n = 4;

        // Test matrix
        Percolation p = new Percolation(n);
        p.open(1, 2);
        p.open(2, 2);
        p.open(2, 4);
        p.open(2, 3);
        p.open(3, 3);
        p.open(4, 3);
        p.open(3, 1);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(p.grid[i][j] + " ");
            }
            System.out.println();
        }

        // Asserts
        if (!p.isOpen(4, 3)) System.err.println("Cell [3;2] must be opened.");
        if (p.isFull(3, 1)) System.err.println("Cell [2;0] must not be full.");
        if (!p.percolates()) System.err.println("Test matrix must be percolated");
    }

}
