package org.example.vistas;

import javax.swing.*;
import java.awt.*;

public class mi_ventana extends javax.swing.JFrame {
    public mi_ventana() {
        setTitle("Inicio");
        setSize(800, 600);
        setMinimumSize(new java.awt.Dimension(600, 500));
        


        JLabel title = new JLabel("¿A que juego quieres jugar?", SwingConstants.CENTER);
        title.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 24));
        add(title, java.awt.BorderLayout.CENTER);

        JButton boton_caballo = new JButton("Caballo");
        boton_caballo.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 18));
        boton_caballo.setPreferredSize(new Dimension(150, 50)); // Adjusted size

        // Wrap the button in a JPanel with FlowLayout
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(boton_caballo);

        add(buttonPanel, java.awt.BorderLayout.WEST); // Add the panel instead of the button
    }
}
