package org.example.vistas.JuegoCaballo;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;
import javax.swing.*;
import org.example.problemas.Ficha;
import org.example.problemas.caballo.caballo;

public class panelCaballo extends JPanel {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private JPanel boardPanel;
    private JLabel[][] boardCells;
    private JTextArea moveHistoryArea;
    private int boardSize;

    public panelCaballo(CardLayout cardLayout, JPanel mainPanel, int boardSize) {
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
            solveButton.setEnabled(false); // Disable button during computation
            new Thread(() -> {
                caballo solver = new caballo(boardSize);
                boolean solved = solver.solve();

                SwingUtilities.invokeLater(() -> {
                    if (solved) {
                        List<Ficha> moveHistory = solver.getMoveHistory();
                        showMovesOnBoard(moveHistory);
                    } else {
                        JOptionPane.showMessageDialog(panelCaballo.this, "No solution exists for the knight's tour.");
                    }
                    solveButton.setEnabled(true); // Re-enable button
                });
            }).start();
        });

        // Back button action
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "Inicio"));
    }

    private void showMovesOnBoard(List<Ficha> moveHistory) {
        moveHistoryArea.setText(""); // Clear the move history area
        Timer timer = new Timer(500, new AbstractAction() { // Interval of 500 ms
            int index = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (index < moveHistory.size()) {
                    // Clear the board
                    for (int i = 0; i < boardSize; i++) {
                        for (int j = 0; j < boardSize; j++) {
                            if (boardCells[i][j].getIcon() != null) {
                                boardCells[i][j].setIcon(null);
                            }
                        }
                    }

                    // Highlight the current move
                    Ficha move = moveHistory.get(index);

                    if (index > 0) {
                        Ficha previousMove = moveHistory.get(index - 1);
                        boardCells[previousMove.getFila()][previousMove.getColumna()].setText(String.valueOf(index));
                        boardCells[previousMove.getFila()][previousMove.getColumna()].setFont(new Font("Poppins", Font.PLAIN, 20));
                        boardCells[previousMove.getFila()][previousMove.getColumna()].setForeground(Color.BLACK);
                    }

                    ImageIcon originalIcon = new ImageIcon("src/main/java/org/example/Recursos/img/Knight_Icon.png");
                    Image scaledImage = originalIcon.getImage().getScaledInstance(
                            boardCells[0][0].getWidth(),
                            boardCells[0][0].getHeight(),
                            Image.SCALE_SMOOTH
                    );
                    ImageIcon scaledIcon = new ImageIcon(scaledImage);

                    boardCells[move.getFila()][move.getColumna()].setIcon(scaledIcon);
                    boardCells[move.getFila()][move.getColumna()].setForeground(Color.RED);

                    moveHistoryArea.append("Move " + (index + 1) + ": (" + move.getFila() + ", " + move.getColumna() + ")\n");

                    index++;
                } else {
                    ((Timer) e.getSource()).stop();
                }
            }
        });
        timer.start();
    }
}