package org.example.problemas;

public class Ficha {
    private String tipo;        // "reina", "caballo", "disco", etc.
    private int fila;           // Para tablero (opcional según uso)
    private int columna;        // Para tablero (opcional según uso)
    private String torre;       // Para Torres de Hanoi
    private int id;             // Identificador (útil en discos)

    public Ficha(String tipo, int fila, int columna, String torre, int id) {
        this.tipo = tipo;
        this.fila = fila;
        this.columna = columna;
        this.torre = torre;
        this.id = id;
    }

    // Constructores sobrecargados según necesidad
    public Ficha(String tipo, int fila, int columna) {
        this(tipo, fila, columna, null, -1);
    }

    public Ficha(String tipo, String torre, int id) {
        this(tipo, -1, -1, torre, id);
    }

    // Getters y Setters
    public String getTipo() {
        return tipo;
    }

    public int getFila() {
        return fila;
    }

    public int getColumna() {
        return columna;
    }

    public String getTorre() {
        return torre;
    }

    public int getId() {
        return id;
    }

    public void moverA(int nuevaFila, int nuevaColumna) {
        this.fila = nuevaFila;
        this.columna = nuevaColumna;
    }

    public void cambiarTorre(String nuevaTorre) {
        this.torre = nuevaTorre;
    }

    @Override
    public String toString() {
        return "Ficha{" +
                "tipo='" + tipo + '\'' +
                ", fila=" + fila +
                ", columna=" + columna +
                ", torre='" + torre + '\'' +
                ", id=" + id +
                '}';
    }
}