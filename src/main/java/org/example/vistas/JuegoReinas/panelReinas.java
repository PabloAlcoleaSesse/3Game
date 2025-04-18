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
    private List<List<Ficha>> allSolutions; // Store all solutions
    private int currentSolutionIndex = 0; // Track current solution being shown
    private JButton nextSolutionButton; // Button to show next solution

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

        // Create a panel for buttons at the top
        JPanel topPanel = new JPanel(new FlowLayout());

        // Solve button
        JButton solveButton = new JButton("Solve N-Queens");
        topPanel.add(solveButton);

        // Next solution button (initially disabled)
        nextSolutionButton = new JButton("Next Solution");
        nextSolutionButton.setEnabled(false);
        topPanel.add(nextSolutionButton);

        add(topPanel, BorderLayout.NORTH);

        // Back button
        JButton backButton = new JButton("Back to Menu");
        add(backButton, BorderLayout.SOUTH);

        // Solve button action
        solveButton.addActionListener(e -> {
            solveButton.setEnabled(false); // Disable button during computation
            nextSolutionButton.setEnabled(false); // Disable next solution button
            currentSolutionIndex = 0; // Reset solution index

            new Thread(() -> {
                Reinas solver = new Reinas(boardSize);
                solver.resolver(true); // Find all solutions

                // Inside solveButton.addActionListener after finding solutions
                BaseDeDatos db = new BaseDeDatos();
                db.recordQueensGame(boardSize, solver.getSoluciones());

                SwingUtilities.invokeLater(() -> {
                    allSolutions = solver.getSoluciones();
                    if (!allSolutions.isEmpty()) {
                        solutionArea.setText("Solution " + (currentSolutionIndex + 1) + " of " +
                                allSolutions.size() + "\n");
                        showSolutionOnBoard(allSolutions.get(currentSolutionIndex)); // Show the first solution

                        // Enable next solution button if there's more than one solution
                        nextSolutionButton.setEnabled(allSolutions.size() > 1);
                    } else {
                        JOptionPane.showMessageDialog(panelReinas.this, "No solutions found.");
                    }
                    solveButton.setEnabled(true); // Re-enable button
                });
            }).start();
        });

        // Next solution button action
        nextSolutionButton.addActionListener(e -> {
            if (allSolutions != null && !allSolutions.isEmpty()) {
                currentSolutionIndex = (currentSolutionIndex + 1) % allSolutions.size();
                solutionArea.setText("Solution " + (currentSolutionIndex + 1) + " of " +
                        allSolutions.size() + "\n");
                showSolutionOnBoard(allSolutions.get(currentSolutionIndex));
            }
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
            ImageIcon originalIcon = new ImageIcon("src/main/java/org/example/Recursos/img/Queen.png");
            Image scaledImage = originalIcon.getImage().getScaledInstance(
                    boardCells[queen.getFila()][queen.getColumna()].getWidth(),
                    boardCells[queen.getFila()][queen.getColumna()].getHeight(),
                    Image.SCALE_SMOOTH
            );
            ImageIcon scaledIcon = new ImageIcon(scaledImage);

            boardCells[queen.getFila()][queen.getColumna()].setIcon(scaledIcon);
            boardCells[queen.getFila()][queen.getColumna()].setForeground(Color.RED);
            boardCells[queen.getFila()][queen.getColumna()].setFont(new Font("Poppins", Font.BOLD, 20));
            boardCells[queen.getFila()][queen.getColumna()].setForeground(Color.RED);
        }
    }
}