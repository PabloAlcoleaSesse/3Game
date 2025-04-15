package org.example.vistas.JuegoCaballo;

import javax.swing.*;
import java.awt.*;

public class panelCaballo extends JPanel {
    private static final long serialVersionUID = 1L;

    public panelCaballo(CardLayout layout, JPanel mainPanel) {
        setLayout(new BorderLayout());

        // Board placeholder
        JPanel board = new JPanel(new GridLayout(8, 8));
        board.add(new JLabel("Reinas Board Placeholder"));

        // Move history
        JTextArea moveHistory = new JTextArea("Move History");

        // Back to menu button
        JButton backButton = new JButton("Volver al menú");
        backButton.addActionListener(e -> layout.show(mainPanel, "Menu"));

        add(board, BorderLayout.CENTER);
        add(moveHistory, BorderLayout.EAST);
        add(backButton, BorderLayout.SOUTH);
    }
}



