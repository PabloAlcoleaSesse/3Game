package org.example.problemas.TorresHanoi;

import org.example.problemas.Ficha;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Torres {
    private int numDiscos;
    private List<String> movimientos;
    private Stack<Ficha>[] torres;
    private List<Ficha> movimientosFichas;

    public Torres(int numDiscos) {
        this.numDiscos = numDiscos;
        this.movimientos = new ArrayList<>();
        this.movimientosFichas = new ArrayList<>();

        // Initialize the three towers
        this.torres = new Stack[3];
        for (int i = 0; i < 3; i++) {
            torres[i] = new Stack<>();
        }

        // Initialize the first tower with disks
        for (int i = numDiscos; i >= 1; i--) {
            torres[0].push(new Ficha(i));
        }
    }

    public void resolver() {
        movimientos.clear();
        movimientosFichas.clear();

        // Reset towers
        for (int i = 0; i < 3; i++) {
            torres[i].clear();
        }

        // Initialize the first tower with disks
        for (int i = numDiscos; i >= 1; i--) {
            torres[0].push(new Ficha(i));
        }

        moverDiscos(numDiscos, 0, 2, 1);
    }

    private void moverDiscos(int n, int origen, int destino, int auxiliar) {
        if (n == 1) {
            // Move disk and record the movement
            Ficha disco = torres[origen].pop();
            torres[destino].push(disco);

            // Add text representation for backward compatibility
            char torreOrigen = (char)('A' + origen);
            char torreDestino = (char)('A' + destino);
            movimientos.add("Mover disco " + disco.getTamano() + " de " + torreOrigen + " a " + torreDestino);

            // Add Ficha object to track movement
            movimientosFichas.add(disco);
        } else {
            moverDiscos(n - 1, origen, auxiliar, destino);
            moverDiscos(1, origen, destino, auxiliar);
            moverDiscos(n - 1, auxiliar, destino, origen);
        }
    }

    public List<String> getMovimientos() {
        return movimientos;
    }

    public List<Ficha> getMovimientosFichas() {
        return movimientosFichas;
    }
}