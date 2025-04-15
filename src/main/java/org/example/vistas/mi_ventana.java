package org.example.vistas;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import org.example.vistas.JuegoCaballo.panelCaballo;

public class mi_ventana extends JFrame {
    public mi_ventana() {
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(800, 600));
        setTitle("Mi Ventana");

        // Create CardLayout and main panel
        CardLayout cardLayout = new CardLayout();
        JPanel mainPanel = new JPanel(cardLayout);

        // Create the Inicio panel
        JPanel panelInicio = new JPanel(new BorderLayout());
        panelInicio.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Add padding

        // Title at the top
        JLabel labelInicio = new JLabel("Inicio", SwingConstants.CENTER);
        labelInicio.setFont(new Font("Arial", Font.BOLD, 24));
        panelInicio.add(labelInicio, BorderLayout.NORTH);

        // Buttons in the center
        JPanel panelOpcion = new JPanel(new GridBagLayout());
        panelOpcion.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0)); // Add spacing between buttons
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Add spacing around buttons
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JButton botonTorres = new JButton("Torres de Hanoi");
        JButton botonCaballo = new JButton("Caballo");
        JButton botonReinas = new JButton("Reinas");

        gbc.gridx = 0;
        gbc.gridy = 0;
        panelOpcion.add(botonTorres, gbc);

        gbc.gridy = 1;
        panelOpcion.add(botonCaballo, gbc);

        gbc.gridy = 2;
        panelOpcion.add(botonReinas, gbc);

        panelInicio.add(panelOpcion, BorderLayout.CENTER);

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