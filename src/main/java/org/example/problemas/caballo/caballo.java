package org.example.problemas.caballo;

import org.example.problemas.Ficha;
import java.util.ArrayList;
import java.util.List;

public class caballo {
    private int N;
    private Ficha[][] board;
    private int[] dx = {2, 1, -1, -2, -2, -1, 1, 2};
    private int[] dy = {1, 2, 2, 1, -1, -2, -2, -1};
    private List<Ficha> moveHistory = new ArrayList<>();

    public caballo(int boardSize) {
        this.N = boardSize;
        board = new Ficha[N][N];
    }

    public boolean solve() {
        Ficha start = new Ficha(Ficha.Tipo.CABALLO, 0, 0);
        board[0][0] = start;
        moveHistory.clear();

        if (solveUtil(0, 0, 1)) {
            return true;
        } else {
            board[0][0] = null;
            moveHistory.clear();
            return false;
        }
    }

    private boolean solveUtil(int x, int y, int moveCount) {
        if (moveCount == N * N) {
            return true;
        }

        int[] moveOrder = getMoveOrder(x, y); // Get moves sorted by Warnsdorff's heuristic
        for (int i : moveOrder) {
            int nextX = x + dx[i];
            int nextY = y + dy[i];

            if (isValidMove(nextX, nextY)) {
                board[nextX][nextY] = new Ficha(Ficha.Tipo.CABALLO, nextX, nextY);
                moveHistory.add(new Ficha(Ficha.Tipo.CABALLO, nextX, nextY));

                if (solveUtil(nextX, nextY, moveCount + 1)) {
                    return true;
                }

                board[nextX][nextY] = null;
                moveHistory.remove(moveHistory.size() - 1);
            }
        }
        return false;
    }

    private int[] getMoveOrder(int x, int y) {
        int[] moveOrder = new int[8];
        int[] degree = new int[8];

        for (int i = 0; i < 8; i++) {
            int nextX = x + dx[i];
            int nextY = y + dy[i];
            degree[i] = countValidMoves(nextX, nextY);
            moveOrder[i] = i;
        }

        // Sort moves by degree (Warnsdorff's heuristic)
        for (int i = 0; i < 8; i++) {
            for (int j = i + 1; j < 8; j++) {
                if (degree[moveOrder[i]] > degree[moveOrder[j]]) {
                    int temp = moveOrder[i];
                    moveOrder[i] = moveOrder[j];
                    moveOrder[j] = temp;
                }
            }
        }

        return moveOrder;
    }

    private int countValidMoves(int x, int y) {
        int count = 0;
        for (int i = 0; i < 8; i++) {
            int nextX = x + dx[i];
            int nextY = y + dy[i];
            if (isValidMove(nextX, nextY)) {
                count++;
            }
        }
        return count;
    }

    private boolean isValidMove(int x, int y) {
        return (x >= 0 && x < N && y >= 0 && y < N && board[x][y] == null);
    }

    public List<Ficha> getMoveHistory() {
        return moveHistory;
    }
}