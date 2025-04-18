package org.example.vistas.TorresHanoi;

import java.awt.*;
import java.util.List;
import javax.swing.*;
import org.example.problemas.Ficha;
import org.example.problemas.TorresHanoi.Torres;
import org.example.BD.BaseDeDatos;

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

        // Create the towers panel with fixed height for better disk visualization
        JPanel towersPanel = new JPanel(new GridLayout(1, 3, 10, 0));
        towersPanel.setPreferredSize(new Dimension(600, 300));
        towersPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        towers = new JPanel[3];
        for (int i = 0; i < 3; i++) {
            towers[i] = new JPanel();
            towers[i].setLayout(new BoxLayout(towers[i], BoxLayout.Y_AXIS));
            towers[i].setBorder(BorderFactory.createTitledBorder("Tower " + (char)('A' + i)));
            towers[i].setBackground(Color.LIGHT_GRAY);

            // Add a filler component at the top to push disks to the bottom
            towers[i].add(Box.createVerticalGlue());

            towersPanel.add(towers[i]);
        }
        add(towersPanel, BorderLayout.CENTER);

        // Create the move history area
        moveHistoryArea = new JTextArea(10, 20);
        moveHistoryArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(moveHistoryArea);
        add(scrollPane, BorderLayout.EAST);

        // Solve button
        JButton solveButton = new JButton("Solve Towers of Hanoi");
        add(solveButton, BorderLayout.NORTH);

        // Back button
        JButton backButton = new JButton("Back to Menu");
        add(backButton, BorderLayout.SOUTH);

        // Initialize the disks on the first tower
        initializeDisks();

        // Solve button action
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

            Torres solver = new Torres(numDiscos);
            solver.resolver();
            movimientos = solver.getMovimientos();

            // Inside solveButton.addActionListener
            BaseDeDatos db = new BaseDeDatos();
            db.recordHanoiGame(numDiscos, solver.getMovimientos());

            animateSolution();
            solveButton.setEnabled(true);
        });

        // Back button action
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "Inicio"));
    }

    private void initializeDisks() {
        // Add disks to the first tower (largest at the bottom)
        for (int i = numDiscos; i >= 1; i--) {
            // Create disk with size i (largest to smallest)
            JPanel disk = createDisk(i);
            // Add disk to the first tower after the glue component
            towers[0].add(disk, 1);  // Index 1 is right after the glue component
        }

        // Update UI
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

        // Add a label with the disk size for clarity
        JLabel label = new JLabel(String.valueOf(size));
        label.setForeground(Color.WHITE);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        disk.add(label);

        return disk;
    }

    private Color getDiskColor(int size) {
        // Generate different colors for different disk sizes
        float hue = (float) size / numDiscos;
        return Color.getHSBColor(hue, 0.8f, 0.8f);
    }

    private void animateSolution() {
        Timer timer = new Timer(1000, null); // 1-second interval
        final int[] moveIndex = {0};

        timer.addActionListener(e -> {
            if (moveIndex[0] < movimientos.size()) {
                String move = movimientos.get(moveIndex[0]);
                // Add move number to the history
                moveHistoryArea.append("Move " + (moveIndex[0] + 1) + ": " + move + "\n");
                executeMove(move);
                moveHistoryArea.setCaretPosition(moveHistoryArea.getDocument().getLength());
                moveIndex[0]++;
            } else {
                ((Timer) e.getSource()).stop();
            }
        });

        timer.start();
    }

    private void executeMove(String move) {
        try {
            String[] parts = move.split(" ");
            int diskNum = Integer.parseInt(parts[2]);
            int fromTower = parts[4].charAt(0) - 'A';
            int toTower = parts[6].charAt(0) - 'A';

            // Find the disk with the specified size
            Component diskComponent = null;
            for (Component comp : towers[fromTower].getComponents()) {
                if (comp instanceof JPanel) {
                    JPanel panel = (JPanel) comp;
                    // Check if this is the right disk by finding its label
                    for (Component c : panel.getComponents()) {
                        if (c instanceof JLabel && ((JLabel) c).getText().equals(String.valueOf(diskNum))) {
                            diskComponent = panel;
                            break;
                        }
                    }
                    if (diskComponent != null) break;
                }
            }

            if (diskComponent != null) {
                // Remove the disk from the source tower
                towers[fromTower].remove(diskComponent);

                // Add the disk to the destination tower after the glue component but before any other disks
                // This ensures the disks stack properly with largest at the bottom
                towers[toTower].add(diskComponent, 1);

                // Ensure the disk is displayed correctly
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