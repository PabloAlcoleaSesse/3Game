package org.example.problemas.ochoReinas;


import java.util.ArrayList;
import java.util.List;

public class OchoReinasSolver {
    public static final int N = 8;
    private List<Ficha> soluciones = new ArrayList<>();

    // Comprueba que la posición (fila, columna) sea segura considerando las reinas ya colocadas.
    private boolean esSeguro(List<Ficha> reinas, int fila, int columna) {
        for (Ficha reina : reinas) {
            int f = reina.getFila();
            int c = reina.getColumna();
            // Misma columna
            if (c == columna) return false;
            // Misma diagonal
            if (Math.abs(f - fila) == Math.abs(c - columna)) return false;
        }
        return true;
    }

    // Función recursiva que intenta colocar reinas fila por fila
    private boolean resolver(List<Ficha> reinas, int fila) {
        if (fila == N) {
            // Se encontró una solución completa
            soluciones.addAll(reinas);
            return true; // o retornar false si se desea buscar todas las soluciones
        }








        

        for (int col = 0; col < N; col++) {
            if (esSeguro(reinas, fila, col)) {
                // Crea una nueva ficha para la reina
                Ficha reina = new Ficha("reina", fila, col);
                reinas.add(reina);
                if (resolver(reinas, fila + 1)) {
                    return true;
                }
                reinas.remove(reinas.size() - 1); // backtracking
            }
        }
        return false;
    }

    public void resolverOchoReinas() {
        List<Ficha> reinas = new ArrayList<>();
        if (resolver(reinas, 0)) {
            System.out.println("Solución encontrada para las 8 reinas:");
            for (Ficha reina : reinas) {
                System.out.println(reina);
            }
        } else {
            System.out.println("No existe solución.");
        }
    }

    public static void main(String[] args) {
        OchoReinasSolver solver = new OchoReinasSolver();
        solver.resolverOchoReinas();
    }
}
