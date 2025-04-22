package org.example.Controlador.ochoReinas;

import org.example.Modelo.Ficha;
import java.util.ArrayList;
import java.util.List;

public class Reinas {
    private int n; // Board size
    private Ficha[][] tablero; // Board representation
    private List<List<Ficha>> soluciones; // List of solutions
    private List<Ficha> historialMovimientos; // Move history

    public Reinas(int n) {
        this.n = n;
        this.tablero = new Ficha[n][n];
        this.soluciones = new ArrayList<>();
        this.historialMovimientos = new ArrayList<>();
    }

    // Main method to solve the problem
    public void resolver(boolean todasLasSoluciones) {
        backtrack(0, todasLasSoluciones);
    }

    // Backtracking algorithm
    private boolean backtrack(int fila, boolean todasLasSoluciones) {
        if (fila == n) {
            guardarSolucion();
            return !todasLasSoluciones;
        }

        for (int columna = 0; columna < n; columna++) {
            if (esMovimientoValido(fila, columna)) {
                Ficha reina = new Ficha(Ficha.Tipo.REINA, fila, columna);
                tablero[fila][columna] = reina;
                historialMovimientos.add(reina);

                if (backtrack(fila + 1, todasLasSoluciones) && !todasLasSoluciones) {
                    return true;
                }

                tablero[fila][columna] = null;
                historialMovimientos.remove(historialMovimientos.size() - 1);
            }
        }
        return false;
    }

    // Check if a position is valid for a queen
    private boolean esMovimientoValido(int fila, int columna) {
        for (int i = 0; i < fila; i++) {
            if (tablero[i][columna] != null) return false;
        }

        for (int i = fila - 1, j = columna - 1; i >= 0 && j >= 0; i--, j--) {
            if (tablero[i][j] != null) return false;
        }

        for (int i = fila - 1, j = columna + 1; i >= 0 && j < n; i--, j++) {
            if (tablero[i][j] != null) return false;
        }

        return true;
    }

    // Save the current solution
    private void guardarSolucion() {
        List<Ficha> solucion = new ArrayList<>();
        for (Ficha reina : historialMovimientos) {
            solucion.add(new Ficha(Ficha.Tipo.REINA, reina.getFila(), reina.getColumna()));
        }
        soluciones.add(solucion);
    }

    // Get all solutions
    public List<List<Ficha>> getSoluciones() {
        return soluciones;
    }
}