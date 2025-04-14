package org.example.problemas.TorresHanoi;

 public class TorresHanoiSolver {
    // Se moverán n discos de la torre origen a la torre destino usando torre auxiliar.
    public void moverDiscos(int n, String origen, String destino, String auxiliar) {
        if (n == 1) {
            // Crear un objeto Ficha para representar el disco y cambiar su torre
            Ficha disco = new Ficha("disco", origen, 1);
            disco.cambiarTorre(destino);
            System.out.println("Mover disco " + 1 + " de " + origen + " a " + destino);
            return;
        }
        moverDiscos(n - 1, origen, auxiliar, destino);
        Ficha disco = new Ficha("disco", origen, n);
        disco.cambiarTorre(destino);
        System.out.println("Mover disco " + n + " de " + origen + " a " + destino);
        moverDiscos(n - 1, auxiliar, destino, origen);
    }

    public static void main(String[] args) {
        TorresHanoiSolver solver = new TorresHanoiSolver();
        int n = 3;  // Número de discos (puede ajustarse)
        solver.moverDiscos(n, "A", "C", "B");
    }
}
