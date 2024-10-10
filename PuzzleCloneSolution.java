import java.util.*;

public class PuzzleCloneSolution {
    private TileNode goalState;
    private TileNode startState;
    private int depthLimit;

    public PuzzleCloneSolution(int[][] startBoard, int[][] goalBoard, int depthLimit) {
        this.startState = new TileNode(startBoard);
        this.goalState = new TileNode(goalBoard);
        this.depthLimit = depthLimit;
    }

    public TileNode iterativeDeepeningSearch() {
        int limit = 1;
        while (true) {
            TileNode result = depthLimitedSearch(startState.cloneNode(), limit);
            if (result != null) {
                return result;
            }
            limit++;
            if (limit > depthLimit)
                break;
        }
        return null;
    }

    public TileNode depthLimitedSearch(TileNode node, int limit) {
        Stack<TileNode> frontier = new Stack<>();
        Set<TileNode> visited = new HashSet<>();
        frontier.push(node);

        while (!frontier.isEmpty()) {
            TileNode currentNode = frontier.pop();

            if (currentNode.equals(goalState)) {
                return currentNode;
            }

            if (currentNode.getDepthLevel() < limit) {
                List<TileNode> possibleMoves = generateNeighborNodes(currentNode);

                for (TileNode neighbor : possibleMoves) {
                    if (!visited.contains(neighbor)) {
                        neighbor.setDepthLevel(currentNode.getDepthLevel() + 1);
                        visited.add(neighbor);
                        frontier.push(neighbor.cloneNode()); // Cópia do estado
                    }
                }
            }
        }
        return null;
    }

    private List<TileNode> generateNeighborNodes(TileNode node) {
        List<TileNode> neighbors = new ArrayList<>();
        int[][] state = node.getBoard();

        int zeroRow = -1, zeroCol = -1;
        for (int i = 0; i < state.length; i++) {
            for (int j = 0; j < state[i].length; j++) {
                if (state[i][j] == 0) {
                    zeroRow = i;
                    zeroCol = j;
                    break;
                }
            }
        }

        int[][] directions = {
                { -1, 0 },
                { 1, 0 },
                { 0, -1 },
                { 0, 1 }
        };

        for (int[] dir : directions) {
            int newRow = zeroRow + dir[0];
            int newCol = zeroCol + dir[1];

            if (newRow >= 0 && newRow < state.length && newCol >= 0 && newCol < state[0].length) {
                int[][] newState = cloneBoard(state);
                newState[zeroRow][zeroCol] = newState[newRow][newCol];
                newState[newRow][newCol] = 0;

                neighbors.add(new TileNode(newState));
            }
        }

        return neighbors;
    }

    private int[][] cloneBoard(int[][] original) {
        int[][] copy = new int[original.length][original[0].length];
        for (int i = 0; i < original.length; i++) {
            System.arraycopy(original[i], 0, copy[i], 0, original[i].length);
        }
        return copy;
    }

    public static void main(String[] args) {
        int[][] startBoard = {
            { 0, 1, 3 },
            { 4, 2, 5 },
            { 7, 8, 6 }
        };
        int[][] goalBoard = { 
            { 1, 2, 3 },
            { 4, 5, 6 },
            { 7, 8, 0 }
        };
        
        // Medindo o tempo de execução da versão com cópia
        long startTime = System.nanoTime();
        PuzzleCloneSolution puzzle = new PuzzleCloneSolution(startBoard, goalBoard, 20);
        TileNode solution = puzzle.iterativeDeepeningSearch();
        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / 1000000;  // Tempo em milissegundos
        
        // Exibindo os resultados
        System.out.println("Tempo de execução (com cópia): " + duration + " ms");
        
        if (solution != null) {
            System.out.println("Solução encontrada (com cópia):");
            solution.displayBoard();
        } else {
            System.out.println("Não foi possível encontrar uma solução (com cópia).");
        }
    }
}
