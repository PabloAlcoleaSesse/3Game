package org.example.vistas.JuegoCaballo;

import org.example.problemas.caballo.caballo;
import org.example.problemas.Ficha;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class panelCaballo extends JPanel {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private JPanel boardPanel;
    private JLabel[][] boardCells;
    private JTextArea moveHistoryArea;

    public panelCaballo(CardLayout cardLayout, JPanel mainPanel) {
        this.cardLayout = cardLayout;
        this.mainPanel = mainPanel;
        setLayout(new BorderLayout());

        // Create the board panel
        boardPanel = new JPanel(new GridLayout(8, 8));
        boardCells = new JLabel[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                boardCells[i][j] = new JLabel("", SwingConstants.CENTER);
                boardCells[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                boardCells[i][j].setOpaque(true);
                boardCells[i][j].setBackground((i + j) % 2 == 0 ? Color.WHITE : Color.GRAY);
                boardPanel.add(boardCells[i][j]);
            }
        }
        add(boardPanel, BorderLayout.CENTER);

        // Create the move history area
        moveHistoryArea = new JTextArea(10, 20);
        moveHistoryArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(moveHistoryArea);
        add(scrollPane, BorderLayout.EAST);

        // Solve button
        JButton solveButton = new JButton("Solve Knight's Tour");
        add(solveButton, BorderLayout.NORTH);

        // Back button
        JButton backButton = new JButton("Back to Menu");
        add(backButton, BorderLayout.SOUTH);

        // Solve button action
        solveButton.addActionListener(e -> {
            caballo solver = new caballo();
            boolean solved = solver.solve();

            if (solved) {
                List<Ficha> moveHistory = solver.getMoveHistory();
                showMovesOnBoard(moveHistory);
            } else {
                JOptionPane.showMessageDialog(panelCaballo.this, "No existe solución para el recorrido.");
            }
        });

        // Back button action
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "Inicio"));
    }

    private void showMovesOnBoard(List<Ficha> moveHistory) {
        moveHistoryArea.setText(""); // Clear the move history area
        Timer timer = new Timer(500, new ActionListener() { // Interval of 500 ms
            int index = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (index < moveHistory.size()) {
                    // Clear the board
                    for (int i = 0; i < 8; i++) {
                        for (int j = 0; j < 8; j++) {
                            boardCells[i][j].setText("");
                        }
                    }

                    // Highlight the current move
                    Ficha move = moveHistory.get(index);
                    boardCells[move.getFila()][move.getColumna()].setText("♞");
                    boardCells[move.getFila()][move.getColumna()].setForeground(Color.RED);

                    // Update the move history
                    moveHistoryArea.append("Move " + (index + 1) + ": (" + move.getFila() + ", " + move.getColumna() + ")\n");

                    index++;
                } else {
                    ((Timer) e.getSource()).stop(); // Stop the timer when all moves are shown
                }
            }
        });
        timer.start();
    }
}