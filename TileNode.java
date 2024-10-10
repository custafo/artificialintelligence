import java.util.Arrays;

public class TileNode {
    private int[][] board;
    private int depthLevel;

    public TileNode(int[][] board) {
        this.board = new int[board.length][board[0].length];
        for (int i = 0; i < board.length; i++) {
            System.arraycopy(board[i], 0, this.board[i], 0, board[i].length);
        }
        this.depthLevel = 0;  // Profundidade inicial
    }

    public int[][] getBoard() {
        return board;
    }

    public int getDepthLevel() {
        return depthLevel;
    }

    public void setDepthLevel(int depth) {
        this.depthLevel = depth;
    }

    public TileNode cloneNode() {
        return new TileNode(this.board);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        TileNode other = (TileNode) obj;
        return Arrays.deepEquals(this.board, other.board);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(board);
    }

    public void displayBoard() {
        for (int[] row : board) {
            for (int col : row) {
                System.out.print(col + " ");
            }
            System.out.println();
        }
    }
}
