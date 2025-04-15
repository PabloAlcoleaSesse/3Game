package org.example.problemas.caballo;

import org.example.problemas.Ficha;
import java.util.ArrayList;
import java.util.List;

public class caballo {

    private static final int N = 8;
    private Ficha[][] board;
    private int[] dx = {2, 1, -1, -2, -2, -1, 1, 2};
    private int[] dy = {1, 2, 2, 1, -1, -2, -2, -1};
    private List<Ficha> moveHistory = new ArrayList<>();

    public caballo() {
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

        for (int i = 0; i < 8; i++) {
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

    private boolean isValidMove(int x, int y) {
        return (x >= 0 && x < N && y >= 0 && y < N && board[x][y] == null);
    }

    public String getSolutionString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                sb.append((board[i][j] != null ? "1" : "0") + " ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public String getHistoryString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Historial de movimientos:\n");
        for (int i = 0; i < moveHistory.size(); i++) {
            Ficha movimiento = moveHistory.get(i);
            sb.append("Movimiento " + (i + 1) + ": (" + movimiento.getFila() + ", " + movimiento.getColumna() + ")\n");
        }
        return sb.toString();
    }
}