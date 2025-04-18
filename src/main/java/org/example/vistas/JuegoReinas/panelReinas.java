package org.example.vistas.JuegoReinas;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;
import javax.swing.*;
import org.example.problemas.Ficha;
import org.example.problemas.ochoReinas.Reinas;
import org.example.BD.BaseDeDatos;

public class panelReinas extends JPanel {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private JPanel boardPanel;
    private JLabel[][] boardCells;
    private JTextArea solutionArea;
    private int boardSize;

    public panelReinas(CardLayout cardLayout, JPanel mainPanel, int boardSize) {
        this.cardLayout = cardLayout;
        this.mainPanel = mainPanel;
        this.boardSize = boardSize;

        setLayout(new BorderLayout());

        // Create the board panel
        boardPanel = new JPanel(new GridLayout(boardSize, boardSize)) {
            @Override
            public Dimension getPreferredSize() {
                Dimension size = super.getPreferredSize();
                int side = Math.min(size.width, size.height);
                return new Dimension(side, side);
            }
        };
        boardCells = new JLabel[boardSize][boardSize];
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                boardCells[i][j] = new JLabel("", SwingConstants.CENTER);
                boardCells[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                boardCells[i][j].setOpaque(true);
                boardCells[i][j].setBackground((i + j) % 2 == 0 ? Color.WHITE : Color.GRAY);
                boardPanel.add(boardCells[i][j]);
            }
        }
        add(boardPanel, BorderLayout.CENTER);

        // Create the solution area
        solutionArea = new JTextArea(10, 20);
        solutionArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(solutionArea);
        add(scrollPane, BorderLayout.EAST);

        // Solve button
        JButton solveButton = new JButton("Solve N-Queens");
        add(solveButton, BorderLayout.NORTH);

        // Back button
        JButton backButton = new JButton("Back to Menu");
        add(backButton, BorderLayout.SOUTH);

        // Solve button action
        solveButton.addActionListener(e -> {
            solveButton.setEnabled(false); // Disable button during computation
            new Thread(() -> {
                Reinas solver = new Reinas(boardSize);
                solver.resolver(true); // Find all solutions

                // Inside solveButton.addActionListener after finding solutions
                BaseDeDatos db = new BaseDeDatos();
                db.recordQueensGame(boardSize, solver.getSoluciones());

                SwingUtilities.invokeLater(() -> {
                    List<List<Ficha>> solutions = solver.getSoluciones();
                    if (!solutions.isEmpty()) {
                        solutionArea.setText("Number of solutions: " + solutions.size() + "\n");
                        showSolutionOnBoard(solutions.get(0)); // Show the first solution
                    } else {
                        JOptionPane.showMessageDialog(panelReinas.this, "No solutions found.");
                    }
                    solveButton.setEnabled(true); // Re-enable button
                });
            }).start();
        });

        // Back button action
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "Inicio"));
    }

    private void showSolutionOnBoard(List<Ficha> solution) {
        // Clear the board
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                boardCells[i][j].setText("");
                boardCells[i][j].setIcon(null);
            }
        }

        // Place queens on the board
        for (Ficha queen : solution) {
            boardCells[queen.getFila()][queen.getColumna()].setText("Q");
            boardCells[queen.getFila()][queen.getColumna()].setFont(new Font("Poppins", Font.BOLD, 20));
            boardCells[queen.getFila()][queen.getColumna()].setForeground(Color.RED);
        }
    }
}