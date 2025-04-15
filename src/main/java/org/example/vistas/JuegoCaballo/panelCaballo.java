package org.example.vistas.JuegoCaballo;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import org.example.problemas.Ficha;
import org.example.problemas.caballo.caballo;

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
        boardPanel = new JPanel(new GridLayout(8, 8)) {
            @Override
            public Dimension getPreferredSize() {
                // Ensure the board is square
                Dimension size = super.getPreferredSize();
                int side = Math.min(size.width, size.height);
                return new Dimension(side, side);
            }
        };
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
                            if (boardCells[i][j].getIcon() != null) {
                                // Keep the number of the movement for previous moves
                                boardCells[i][j].setIcon(null);
                            }
                        }
                    }

                    // Highlight the current move
                    Ficha move = moveHistory.get(index);
                

                    // Place the number of the movement on the previous cell
                    if (index > 0) {
                        Ficha previousMove = moveHistory.get(index - 1);
                        boardCells[previousMove.getFila()][previousMove.getColumna()].setText(String.valueOf(index));
                        boardCells[previousMove.getFila()][previousMove.getColumna()].setFont(new Font("Poppins", Font.PLAIN, 20));
                        boardCells[previousMove.getFila()][previousMove.getColumna()].setForeground(Color.BLACK);

                    }

                    // Load and scale the knight icon for the current move
                    ImageIcon originalIcon = new ImageIcon("src/main/java/org/example/Recursos/img/Knight_Icon.png");
                    Image scaledImage = originalIcon.getImage().getScaledInstance(
                        boardCells[0][0].getWidth(), // Use the cell's width
                        boardCells[0][0].getHeight(), // Use the cell's height
                        Image.SCALE_SMOOTH
                    );
                    ImageIcon scaledIcon = new ImageIcon(scaledImage);

                    boardCells[move.getFila()][move.getColumna()].setIcon(scaledIcon);
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