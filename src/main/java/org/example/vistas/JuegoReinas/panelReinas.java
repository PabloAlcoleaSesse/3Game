// File: src/main/java/org/example/vistas/JuegoReinas/panelReinas.java
package org.example.vistas.JuegoReinas;

import java.awt.*;
import java.util.List;
import javax.swing.*;
import org.example.Modelo.Ficha;
import org.example.Controlador.ochoReinas.Reinas;
import org.example.Controlador.BaseDeDatosControlador;

public class panelReinas extends JPanel {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private JPanel boardPanel;
    private JLabel[][] boardCells;
    private JTextArea solutionArea;
    private int boardSize;
    private List<List<Ficha>> allSolutions;
    private int currentSolutionIndex = 0;
    private JButton nextSolutionButton;

    public panelReinas(CardLayout cardLayout, JPanel mainPanel, int boardSize) {
        this.cardLayout = cardLayout;
        this.mainPanel = mainPanel;
        this.boardSize = boardSize;
        setLayout(new BorderLayout());

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

        solutionArea = new JTextArea(10, 20);
        solutionArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(solutionArea);
        add(scrollPane, BorderLayout.EAST);

        JPanel topPanel = new JPanel(new FlowLayout());
        JButton solveButton = new JButton("Solve N-Queens");
        topPanel.add(solveButton);
        nextSolutionButton = new JButton("Next Solution");
        nextSolutionButton.setEnabled(false);
        topPanel.add(nextSolutionButton);
        add(topPanel, BorderLayout.NORTH);

        JButton backButton = new JButton("Back to Menu");
        add(backButton, BorderLayout.SOUTH);

        solveButton.addActionListener(e -> {
            solveButton.setEnabled(false);
            nextSolutionButton.setEnabled(false);
            currentSolutionIndex = 0;

            new Thread(() -> {
                Reinas solver = new Reinas(boardSize);
                solver.resolver(true);

                SwingUtilities.invokeLater(() -> {
                    allSolutions = solver.getSoluciones();
                    if (!allSolutions.isEmpty()) {
                        solutionArea.setText("Solution " + (currentSolutionIndex + 1) + " of " +
                                allSolutions.size() + "\n");
                        showSolutionOnBoard(allSolutions.get(currentSolutionIndex));

                        nextSolutionButton.setEnabled(allSolutions.size() > 1);

                        // Record queens solutions in the database
                        BaseDeDatosControlador dbControlador = new BaseDeDatosControlador();
                        dbControlador.recordQueenGameSolutions(boardSize, allSolutions);
                    } else {
                        JOptionPane.showMessageDialog(panelReinas.this, "No solutions found.");
                    }
                    solveButton.setEnabled(true);
                });
            }).start();
        });

        nextSolutionButton.addActionListener(e -> {
            if (allSolutions != null && !allSolutions.isEmpty()) {
                currentSolutionIndex = (currentSolutionIndex + 1) % allSolutions.size();
                solutionArea.setText("Solution " + (currentSolutionIndex + 1) + " of " +
                        allSolutions.size() + "\n");
                showSolutionOnBoard(allSolutions.get(currentSolutionIndex));
            }
        });

        backButton.addActionListener(e -> cardLayout.show(mainPanel, "Inicio"));
    }

    private void showSolutionOnBoard(List<Ficha> solution) {
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                boardCells[i][j].setText("");
                boardCells[i][j].setIcon(null);
            }
        }
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
        }
    }
}