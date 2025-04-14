package org.example.problemas.caballo;
import java.util.Arrays;
import java.util.Comparator;

public class CaballoTourSolver {
    public static final int N = 8;
    // Movimientos posibles del caballo (8 direcciones)
    private static final int[] dx = {2, 1, -1, -2, -2, -1, 1, 2};
    private static final int[] dy = {1, 2, 2, 1, -1, -2, -2, -1};

    // Tablero: 0 si no visitado, o número de movimiento en el recorrido
    private int[][] tablero;

    public CaballoTourSolver() {
        tablero = new int[N][N];
        for (int[] row : tablero) {
            Arrays.fill(row, -1);
        }
    }

    // Comprueba que (x,y) esté dentro de límites y no visitado
    private boolean esValido(int x, int y) {
        return (x >= 0 && x < N && y >= 0 && y < N && tablero[x][y] == -1);
    }

    // Cuenta el número de movimientos válidos desde (x, y)
    private int contarMovimientos(int x, int y) {
        int count = 0;
        for (int i = 0; i < 8; i++) {
            int nx = x + dx[i], ny = y + dy[i];
            if (esValido(nx, ny)) count++;
        }
        return count;
    }

    // Utiliza la heurística de Warnsdorff para seleccionar el siguiente movimiento
    private boolean resolverTour(int x, int y, int moveCount) {
        tablero[x][y] = moveCount;

        if (moveCount == N * N - 1) {
            return true; // recorrido completo
        }

        // Lista de movimientos posibles ordenados por la cantidad de salidas válidas
        Integer[] indices = new Integer[8];
        for (int i = 0; i < 8; i++) {
            indices[i] = i;
        }
        Arrays.sort(indices, Comparator.comparingInt(i -> {
            int nx = x + dx[i], ny = y + dy[i];
            return esValido(nx, ny) ? contarMovimientos(nx, ny) : Integer.MAX_VALUE;
        }));

        // Probar movimientos en el orden establecido
        for (int i : indices) {
            int nx = x + dx[i], ny = y + dy[i];
            if (esValido(nx, ny)) {
                if (resolverTour(nx, ny, moveCount + 1)) {
                    return true;
                }
            }
        }
        // Retroceso: desmarcar la casilla
        tablero[x][y] = -1;
        return false;
    }

    public void resolverCaballoTour(int startX, int startY) {
        if (resolverTour(startX, startY, 0)) {
            System.out.println("Recorrido del Caballo encontrado:");
            // Representamos el recorrido con objetos Ficha (cada ficha tiene su posición y el número de paso)
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    System.out.printf("%2d ", tablero[i][j]);
                }
                System.out.println();
            }
        } else {
            System.out.println("No se encontró solución para el recorrido del Caballo.");
        }
    }

    public static void main(String[] args) {
        CaballoTourSolver solver = new CaballoTourSolver();
        // Puedes elegir la posición de inicio, por ejemplo (0,0)
        solver.resolverCaballoTour(0, 0);
    }
}