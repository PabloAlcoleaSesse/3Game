package org.example;

import javax.swing.SwingUtilities;

import org.example.vistas.mi_ventana;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        //new mi_ventana().setVisible(true);
        SwingUtilities.invokeLater(mi_ventana::new);
    }

}