package org.example.vistas.JuegoCaballo;

import org.example.problemas.caballo.caballo;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class panelCaballo extends JPanel {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private JTextArea textArea;

    public panelCaballo(CardLayout cardLayout, JPanel mainPanel) {
        this.cardLayout = cardLayout;
        this.mainPanel = mainPanel;
        setLayout(new BorderLayout());

        // Botón para iniciar la solución
        JButton solveButton = new JButton("Solve Knight's Tour");
        add(solveButton, BorderLayout.NORTH);

        // Área de texto para mostrar la solución y el historial
        textArea = new JTextArea(20, 40);
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        add(scrollPane, BorderLayout.CENTER);

        // Botón para volver al menú principal
        JButton backButton = new JButton("Back to Menu");
        add(backButton, BorderLayout.SOUTH);

        // Acción al presionar el botón de solución
        solveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                caballo solver = new caballo();
                boolean solved = solver.solve();

                StringBuilder output = new StringBuilder();
                if (solved) {
                    output.append("¡Recorrido del caballo encontrado!\n\n");
                    output.append("Tablero:\n");
                    output.append(solver.getSolutionString());
                    output.append("\n");
                    output.append(solver.getHistoryString());
                } else {
                    output.append("No existe solución para el recorrido.\n");
                }
                textArea.setText(output.toString());
            }
        });

        // Acción al presionar el botón de volver al menú
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "Inicio"));
    }
}