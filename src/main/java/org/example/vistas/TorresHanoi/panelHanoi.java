// File: src/main/java/org/example/vistas/TorresHanoi/panelHanoi.java
package org.example.vistas.TorresHanoi;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import org.example.Controlador.BaseDeDatosControlador;

public class panelHanoi extends JPanel {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private JPanel[] towers;
    private JTextArea moveHistoryArea;
    private int numDiscos;
    private List<String> movimientos;

    public panelHanoi(CardLayout cardLayout, JPanel mainPanel, int numDiscos) {
        this.cardLayout = cardLayout;
        this.mainPanel = mainPanel;
        this.numDiscos = numDiscos;

        setLayout(new BorderLayout());

        JPanel towersPanel = new JPanel(new GridLayout(1, 3, 10, 0));
        towersPanel.setPreferredSize(new Dimension(600, 300));
        towersPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        towers = new JPanel[3];
        for (int i = 0; i < 3; i++) {
            towers[i] = new JPanel();
            towers[i].setLayout(new BoxLayout(towers[i], BoxLayout.Y_AXIS));
            towers[i].setBorder(BorderFactory.createTitledBorder("Tower " + (char)('A' + i)));
            towers[i].setBackground(Color.LIGHT_GRAY);
            towers[i].add(Box.createVerticalGlue());
            towersPanel.add(towers[i]);
        }
        add(towersPanel, BorderLayout.CENTER);

        moveHistoryArea = new JTextArea(10, 20);
        moveHistoryArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(moveHistoryArea);
        add(scrollPane, BorderLayout.EAST);

        JButton solveButton = new JButton("Solve Towers of Hanoi");
        add(solveButton, BorderLayout.NORTH);

        JButton backButton = new JButton("Back to Menu");
        add(backButton, BorderLayout.SOUTH);

        initializeDisks();

        solveButton.addActionListener(e -> {
            solveButton.setEnabled(false);
            moveHistoryArea.setText(""); // Clear previous moves

            // Reset towers
            for (JPanel tower : towers) {
                while (tower.getComponentCount() > 1) { // Keep the glue component
                    tower.remove(tower.getComponentCount() - 1);
                }
            }
            initializeDisks();

            // Solve the Towers of Hanoi problem
            Torres solver = new Torres(numDiscos);
            solver.resolver();
            movimientos = solver.getMovimientos();

            // Record the game after solving
            BaseDeDatosControlador dbControlador = new BaseDeDatosControlador();
            dbControlador.recordTowerOfHanoiGame(numDiscos, movimientos);

            // Animate the solution
            animateSolution();
            solveButton.setEnabled(true);
        });

        backButton.addActionListener(e -> cardLayout.show(mainPanel, "Inicio"));
    }

    private void initializeDisks() {
        for (int i = numDiscos; i >= 1; i--) {
            JPanel disk = createDisk(i);
            towers[0].add(disk, 1);
        }
        for (JPanel tower : towers) {
            tower.revalidate();
            tower.repaint();
        }
    }

    private JPanel createDisk(int size) {
        JPanel disk = new JPanel();
        disk.setMaximumSize(new Dimension(20 + size * 20, 20));
        disk.setPreferredSize(new Dimension(20 + size * 20, 20));
        disk.setMinimumSize(new Dimension(20 + size * 20, 20));
        disk.setBackground(getDiskColor(size));
        disk.setBorder(BorderFactory.createRaisedBevelBorder());
        disk.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel label = new JLabel(String.valueOf(size));
        label.setForeground(Color.WHITE);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        disk.add(label);
        return disk;
    }

    private Color getDiskColor(int size) {
        float hue = (float) size / numDiscos;
        return Color.getHSBColor(hue, 0.8f, 0.8f);
    }

    private void animateSolution() {
        Timer timer = new Timer(1000, null); // 1-second interval
        final int[] moveIndex = {0};

        timer.addActionListener(e -> {
            if (moveIndex[0] < movimientos.size()) {
                String move = movimientos.get(moveIndex[0]);
                moveHistoryArea.append("Move " + (moveIndex[0] + 1) + ": " + move + "\n");
                executeMove(move); // Execute the move
                moveHistoryArea.setCaretPosition(moveHistoryArea.getDocument().getLength());
                moveIndex[0]++;
            } else {
                ((Timer) e.getSource()).stop(); // Stop the timer when all moves are executed
            }
        });

        timer.start();
    }

    private void executeMove(String move) {
        try {
            // Parse the move string
            String[] parts = move.split(" ");
            int diskNum = Integer.parseInt(parts[1]); // Disk number is the second word
            int fromTower = parts[3].charAt(0) - 'A'; // From tower is the fourth word
            int toTower = parts[5].charAt(0) - 'A';   // To tower is the sixth word

            // Locate the disk in the fromTower
            Component diskComponent = null;
            for (Component comp : towers[fromTower].getComponents()) {
                if (comp instanceof JPanel) {
                    JPanel panel = (JPanel) comp;
                    JLabel label = (JLabel) panel.getComponent(0); // Get the label inside the disk
                    if (label.getText().equals(String.valueOf(diskNum))) {
                        diskComponent = panel;
                        break;
                    }
                }
            }

            // Move the disk to the toTower
            if (diskComponent != null) {
                towers[fromTower].remove(diskComponent);
                towers[toTower].add(diskComponent, 1); // Add the disk to the top of the toTower
                towers[fromTower].revalidate();
                towers[fromTower].repaint();
                towers[toTower].revalidate();
                towers[toTower].repaint();
            }
        } catch (Exception ex) {
            System.err.println("Error executing move: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}

class Torres {
    private List<String> movimientos;
    private int numDiscos;

    public Torres(int numDiscos) {
        this.numDiscos = numDiscos;
        movimientos = new ArrayList<>();
    }

    public void resolver() {
        moverDiscos(numDiscos, 'A', 'C', 'B');
    }

    private void moverDiscos(int n, char origen, char destino, char auxiliar) {
        if (n == 1) {
            movimientos.add("Move 1 from " + origen + " to " + destino);
        } else {
            moverDiscos(n - 1, origen, auxiliar, destino);
            movimientos.add("Move " + n + " from " + origen + " to " + destino);
            moverDiscos(n - 1, auxiliar, destino, origen);
        }
    }

    public List<String> getMovimientos() {
        return movimientos;
    }
}