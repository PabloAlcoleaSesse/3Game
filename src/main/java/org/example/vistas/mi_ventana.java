package org.example.vistas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class mi_ventana extends JFrame {
    public mi_ventana() {
        setTitle("Inicio");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setMinimumSize(new java.awt.Dimension(600, 500));

        // Panel para la creacion de UI personalizada
        JPanel panel = new JPanel(new BorderLayout());
        getContentPane().add(panel);

        // Título
        JLabel titulo = new JLabel("Selecciona una Opcion");
        titulo.setFont(new Font("Poppins", Font.BOLD, 24));
        titulo.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        panel.add(titulo, BorderLayout.NORTH);

        // Panel para las tarjetas
        JPanel cardPanel = new JPanel();
        cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS)); // Use vertical layout for cards
        cardPanel.add(crearTarjeta("Opcion 1", "../Recursos/img/knight.png"));
        cardPanel.add(Box.createVerticalStrut(20));
        cardPanel.add(crearTarjeta("Opción 2", "ruta/a/imagen2.png"));
        cardPanel.add(Box.createVerticalStrut(20));
        cardPanel.add(crearTarjeta("Opción 3", "ruta/a/imagen3.png"));

        // Add the cardPanel to the main panel
        panel.add(cardPanel, BorderLayout.CENTER);

        setVisible(true);
    }  
    private JPanel crearTarjeta(String titulo, String rutaImagen) {
        // Define the colors as final variables
        final Color base = new Color(255, 255, 255);
        final Color hover = new Color(240, 240, 240);

        JPanel tarjeta = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                setBackground(base);
            }
        };

        tarjeta.setPreferredSize(new Dimension(500, 150));
        tarjeta.setMaximumSize(new Dimension(Integer.MAX_VALUE, 150));
        tarjeta.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        tarjeta.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        tarjeta.setLayout(new BorderLayout(10, 10));
        tarjeta.setBackground(base);

        // Add only the title label
        JLabel titleLabel = new JLabel(titulo);
        titleLabel.setFont(new Font("Poppins", Font.PLAIN, 18));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        tarjeta.add(titleLabel, BorderLayout.CENTER);

        // Add hover effect
        tarjeta.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                tarjeta.setBackground(hover);
                tarjeta.repaint();
            }

            public void mouseExited(MouseEvent e) {
                tarjeta.setBackground(base);
                tarjeta.repaint();
            }

            public void mouseClicked(MouseEvent e) {
                JOptionPane.showMessageDialog(null, "Has seleccionado: " + titulo);
            }
        });

        return tarjeta;
    }

}
