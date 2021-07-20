

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private boolean[][] ground;
    private WeightedQuickUnionUF wqGrid;
    /**
     * Separate grid to determine full because
     * in case model percolates, bottom and top are connected
     * so it could gives us wrong information by connecting to bottom
     */

    private WeightedQuickUnionUF wqFull;
    private int openSites;
    private int virtualTop;
    private int virtualBottom;
    private int gridSize;

    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n cannot be less than zero " + n);
        }
        gridSize = n;
        ground = new boolean[gridSize][gridSize];
        openSites = 0;

        int gridFlatLength = gridSize * gridSize;
        wqGrid = new WeightedQuickUnionUF(gridFlatLength + 2);
        wqFull = new WeightedQuickUnionUF(gridFlatLength + 1);
        virtualTop = gridFlatLength;
        virtualBottom = gridFlatLength + 1;

    }

    public void open(int row, int col) {
        checkRowColInBounds(row, col);
        if (isOpen(row, col)) {
            return;
        }
        int shiftedRow = row - 1;
        int shiftedCol = col - 1;
        ground[shiftedRow][shiftedCol] = true;
        openSites++;

        int flatIndex = flattenGridIndex(row, col);
        if (row == 1) {
            wqGrid.union(virtualTop, flatIndex);
            wqFull.union(virtualTop, flatIndex);
        }
        if (row == gridSize) {
            wqGrid.union(virtualBottom, flatIndex);
        }

        // check up is on grid and open
        if (isOnGrid(row - 1, col) && isOpen(row - 1, col)) {
            wqGrid.union(flatIndex, flattenGridIndex(row - 1, col));
            wqFull.union(flatIndex, flattenGridIndex(row - 1, col));
        }

        // check bottom is on grid and open
        if (isOnGrid(row + 1, col) && isOpen(row + 1, col)) {
            wqGrid.union(flatIndex, flattenGridIndex(row + 1, col));
            wqFull.union(flatIndex, flattenGridIndex(row + 1, col));
        }

        // check left is on grid and open
        if (isOnGrid(row, col - 1) && isOpen(row, col - 1)) {
            wqGrid.union(flatIndex, flattenGridIndex(row, col - 1));
            wqFull.union(flatIndex, flattenGridIndex(row, col - 1));
        }

        // check right is on grid and open
        if (isOnGrid(row, col + 1) && isOpen(row, col + 1)) {
            wqGrid.union(flatIndex, flattenGridIndex(row, col + 1));
            wqFull.union(flatIndex, flattenGridIndex(row, col + 1));
        }
    }

    public boolean isOpen(int row, int col) {
        int shiftedRow = row - 1;
        int shiftedCol = col - 1;
        checkRowColInBounds(row, col);
        return ground[shiftedRow][shiftedCol];
    }

    private void checkRowColInBounds(int row, int col) {
        if (!isOnGrid(row, col)) {
            throw new IllegalArgumentException(
                    "row or column are out of bounds " + row + ", " + col);
        }
    }

    // flattens the grid indexes according to 1D array also takes care of shifting as well
    private int flattenGridIndex(int row, int col) {
        return (gridSize * (row - 1) + col) - 1;
    }

    private boolean isOnGrid(int row, int col) {
        int shiftRow = row - 1;
        int shiftCol = col - 1;
        return (shiftRow >= 0 && shiftCol >= 0 && shiftRow < ground.length
                && shiftCol < ground[0].length);
    }

    public boolean percolates() {
        return isConnectedGrid(virtualTop, virtualBottom);
    }

    public int numberOfOpenSites() {
        return openSites;
    }

    public boolean isFull(int row, int col) {
        checkRowColInBounds(row, col);
        return isConnectedFull(virtualTop, flattenGridIndex(row, col));
    }

    private boolean isConnectedGrid(int p, int q) {
        return wqGrid.find(p) == wqGrid.find(q);
    }

    private boolean isConnectedFull(int p, int q) {
        return wqFull.find(p) == wqFull.find(q);
    }


}
