package org.example.problemas;

public class Ficha {

    // Enum para distinguir el uso de la ficha en cada problema.
    public enum Tipo {
        CABALLO,  // Utilizada en el problema del Caballo (p. ej.: tours o movimientos)
        REINA,    // Para el problema de las Ocho Reinas
        DISCO     // Para las torres de Hanoi
    }

    private Tipo tipo;
    // Para los problemas de tablero (caballo y reinas)
    private int fila;
    private int columna;
    // Para el problema de las torres de Hanoi: el tamaño define el disco.
    private int tamano;

    // Constructor para piezas del problema del caballo y de las reinas,
    // se asume que fila y columna son significativos.
    public Ficha(Tipo tipo, int fila, int columna) {
        if (tipo == Tipo.DISCO) {
            throw new IllegalArgumentException("Para tipo DISCO, use el constructor que define el tamaño.");
        }
        this.tipo = tipo;
        this.fila = fila;
        this.columna = columna;
        // No es relevante para problemas de tablero.
        this.tamano = -1;
    }

    // Constructor para discos en las torres de Hanoi.
    public Ficha(int tamano) {
        this.tipo = Tipo.DISCO;
        this.tamano = tamano;
        // Fila y columna no se usan en este contexto.
        this.fila = -1;
        this.columna = -1;
    }

    // Getters y setters.
    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public int getFila() {
        return fila;
    }

    public void setFila(int fila) {
        this.fila = fila;
    }

    public int getColumna() {
        return columna;
    }

    public void setColumna(int columna) {
        this.columna = columna;
    }

    public int getTamano() {
        return tamano;
    }

    public void setTamano(int tamano) {
        this.tamano = tamano;
    }

    // Para facilitar la depuración y visualización.
    @Override
    public String toString() {
        if (tipo == Tipo.DISCO) {
            return "Ficha [tipo=" + tipo + ", tamano=" + tamano + "]";
        } else {
            return "Ficha [tipo=" + tipo + ", fila=" + fila + ", columna=" + columna + "]";
        }
    }
}