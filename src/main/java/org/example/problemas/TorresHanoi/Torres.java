package org.example.problemas.TorresHanoi;

import org.example.problemas.Ficha;
import java.util.ArrayList;
import java.util.List;

public class Torres {
    private int numDiscos;
    private List<String> movimientos;

    public Torres(int numDiscos) {
        this.numDiscos = numDiscos;
        this.movimientos = new ArrayList<>();
    }

    public void resolver() {
        movimientos.clear();
        moverDiscos(numDiscos, "A", "C", "B");
    }

    private void moverDiscos(int n, String origen, String destino, String auxiliar) {
        if (n == 1) {
            movimientos.add("Mover disco 1 de " + origen + " a " + destino);
        } else {
            moverDiscos(n - 1, origen, auxiliar, destino);
            movimientos.add("Mover disco " + n + " de " + origen + " a " + destino);
            moverDiscos(n - 1, auxiliar, destino, origen);
        }
    }

    public List<String> getMovimientos() {
        return movimientos;
    }
}