package org.example.vistas.Historial;

import org.example.Modelo.Ficha;
import org.example.Controlador.BaseDeDatosControlador;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.Map;

public class PanelHistorial extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private JComboBox<String> filtroProblema;
    private JButton btnVolver;
    private JPanel panelFiltros;
    private final BaseDeDatosControlador controlador;

    public PanelHistorial() {
        this.controlador = new BaseDeDatosControlador();
        setLayout(new BorderLayout());
        inicializarComponentes();
        cargarDatos();
    }

    private void inicializarComponentes() {
        // Crear modelo de tabla
        tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Hacer la tabla no editable
            }
        };

        // Añadir columnas
        tableModel.addColumn("ID");
        tableModel.addColumn("Problema");
        tableModel.addColumn("Tamaño");
        tableModel.addColumn("Solución");
        tableModel.addColumn("Fecha");

        // Crear tabla
        table = new JTable(tableModel);
        table.getTableHeader().setReorderingAllowed(false);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setAutoCreateRowSorter(true);

        // Scroll para la tabla
        JScrollPane scrollPane = new JScrollPane(table);

        // Panel de filtros
        panelFiltros = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JLabel lblFiltro = new JLabel("Filtrar por problema:");
        filtroProblema = new JComboBox<>(new String[]{"Todos", "8 Reinas", "Caballo", "Torres de Hanoi"});
        filtroProblema.addActionListener(e -> filtrarDatos());

        panelFiltros.add(lblFiltro);
        panelFiltros.add(filtroProblema);

        // Panel inferior con botón volver
        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnVolver = new JButton("Volver");
        panelInferior.add(btnVolver);

        // Añadir componentes al panel principal
        add(panelFiltros, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(panelInferior, BorderLayout.SOUTH);
    }

    private void cargarDatos() {
        // Limpiar tabla
        tableModel.setRowCount(0);

        // Obtener datos históricos
        List<Map<String, Object>> registros = controlador.obtenerHistorialJuegos();

        // Añadir filas a la tabla
        for (Map<String, Object> registro : registros) {
            tableModel.addRow(new Object[]{
                    registro.get("id"),
                    registro.get("problema"),
                    registro.get("tamano"),
                    registro.get("solucion_resumen"),
                    registro.get("fecha")
            });
        }
    }

    private void filtrarDatos() {
        String filtro = (String) filtroProblema.getSelectedItem();
        if (filtro.equals("Todos")) {
            cargarDatos();
            return;
        }

        // Limpiar tabla
        tableModel.setRowCount(0);

        // Mapear nombres en combo a tipos de fichas
        Ficha.Tipo tipoFicha = null;
        if (filtro.equals("8 Reinas")) tipoFicha = Ficha.Tipo.REINA;
        else if (filtro.equals("Caballo")) tipoFicha = Ficha.Tipo.CABALLO;
        else if (filtro.equals("Torres de Hanoi")) tipoFicha = Ficha.Tipo.DISCO;

        // Filtrar registros
        List<Map<String, Object>> registros = controlador.obtenerHistorialJuegosPorTipo(tipoFicha.name().toLowerCase());

        // Añadir filas filtradas
        for (Map<String, Object> registro : registros) {
            tableModel.addRow(new Object[]{
                    registro.get("id"),
                    registro.get("problema"),
                    registro.get("tamano"),
                    registro.get("solucion_resumen"),
                    registro.get("fecha")
            });
        }
    }

    public JButton getBotonVolver() {
        return btnVolver;
    }

    public void refreshData() {
        cargarDatos();
    }
}