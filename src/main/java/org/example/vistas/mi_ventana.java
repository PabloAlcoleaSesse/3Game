package org.example.vistas;

import java.awt.*;
import javax.swing.*;
import org.example.vistas.JuegoCaballo.panelCaballo;
import org.example.vistas.JuegoReinas.panelReinas;
import org.example.vistas.TorresHanoi.panelHanoi;

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

        // Add action listener to switch to the Caballo panel
        botonCaballo.addActionListener(e -> {
            String input = JOptionPane.showInputDialog(this, "Enter board size (e.g., 8 for 8x8):", "Board Size", JOptionPane.QUESTION_MESSAGE);
            int boardSize;
            try {
                boardSize = Integer.parseInt(input);
                if (boardSize < 4) {
                    throw new IllegalArgumentException("Board size must be at least 4.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid input. Defaulting to 8x8.", "Error", JOptionPane.ERROR_MESSAGE);
                boardSize = 8; // Default size
            }

            panelCaballo caballoPanel = new panelCaballo(cardLayout, mainPanel, boardSize);
            mainPanel.add(caballoPanel, "Caballo");
            cardLayout.show(mainPanel, "Caballo");
        });

        // Add action listener to switch to the Reinas panel
        botonReinas.addActionListener(e -> {
            String input = JOptionPane.showInputDialog(this, "Enter board size (e.g., 8 for 8x8):", "Board Size", JOptionPane.QUESTION_MESSAGE);
            int boardSize;
            try {
                boardSize = Integer.parseInt(input);
                if (boardSize < 4) {
                    throw new IllegalArgumentException("Board size must be at least 4.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid input. Defaulting to 8x8.", "Error", JOptionPane.ERROR_MESSAGE);
                boardSize = 8; // Default size
            }

            panelReinas reinasPanel = new panelReinas(cardLayout, mainPanel, boardSize);
            mainPanel.add(reinasPanel, "Reinas");
            cardLayout.show(mainPanel, "Reinas");
        });

        botonTorres.addActionListener(e -> {
            String input = JOptionPane.showInputDialog(this, "Enter the number of disks:", "Number of Disks", JOptionPane.QUESTION_MESSAGE);
            int numDiscos;
            try {
                numDiscos = Integer.parseInt(input);
                if (numDiscos < 1) {
                    throw new IllegalArgumentException("Number of disks must be at least 1.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid input. Defaulting to 3 disks.", "Error", JOptionPane.ERROR_MESSAGE);
                numDiscos = 3; // Default number of disks
            }

            panelHanoi hanoiPanel = new panelHanoi(cardLayout, mainPanel, numDiscos);
            mainPanel.add(hanoiPanel, "Hanoi");
            cardLayout.show(mainPanel, "Hanoi");
        });

        // Add the main panel to the frame
        add(mainPanel);
        setVisible(true);
    }
}