package org.example.vistas;

import org.example.vistas.JuegoCaballo.panelCaballo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class mi_ventana extends JFrame {
    public mi_ventana() {
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Mi Ventana");

        // Create CardLayout and main panel
        CardLayout cardLayout = new CardLayout();
        JPanel mainPanel = new JPanel(cardLayout);

        // Create the Inicio panel
        JPanel panelInicio = new JPanel();
        panelInicio.setLayout(new BoxLayout(panelInicio, BoxLayout.Y_AXIS));
        JLabel labelInicio = new JLabel("Inicio");
        panelInicio.add(labelInicio);

        JPanel panelOpcion = new JPanel();
        panelOpcion.setLayout(new FlowLayout());

        JButton botonTorres = new JButton("Torres de Hanoi");
        JButton botonCaballo = new JButton("Caballo");
        JButton botonReinas = new JButton("Reinas");

        panelOpcion.add(botonTorres);
        panelOpcion.add(botonCaballo);
        panelOpcion.add(botonReinas);

        panelInicio.add(panelOpcion);

        // Add the Inicio panel to the main panel
        mainPanel.add(panelInicio, "Inicio");

        // Create and add the Caballo panel
        panelCaballo caballoPanel = new panelCaballo(cardLayout, mainPanel);
        mainPanel.add(caballoPanel, "Caballo");

        // Add action listener to switch to the Caballo panel
        botonCaballo.addActionListener(e -> cardLayout.show(mainPanel, "Caballo"));

        // Add the main panel to the frame
        add(mainPanel);
        setVisible(true);
    }
}