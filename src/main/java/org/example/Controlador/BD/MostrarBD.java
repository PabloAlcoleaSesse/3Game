package org.example.Controlador.BD;

import org.example.Modelo.Ficha;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class MostrarBD {
    private static final BaseDeDatos baseDeDatos = new BaseDeDatos();
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    /**
     * Returns all game records for the history panel
     */
    public static List<Map<String, Object>> obtenerHistorialJuegos() {
        List<Map<String, String>> gameRecords = baseDeDatos.getGameRecords();
        return formatGameRecordsForDisplay(gameRecords);
    }



    /**
     * Returns filtered game records by game type
     */
    public static List<Map<String, Object>> obtenerHistorialJuegosPorTipo(Ficha.Tipo tipoFicha) {
        List<Map<String, Object>> allRecords = obtenerHistorialJuegos();

        if (tipoFicha == null) {
            return allRecords;
        }

        // Convert Ficha.Tipo to game_type string
        String gameType;
        switch (tipoFicha) {
            case REINA:
                gameType = "queens";
                break;
            case CABALLO:
                gameType = "knight";
                break;
            case DISCO:
                gameType = "hanoi";
                break;
            default:
                return new ArrayList<>();
        }

        // Filter records by game type
        return allRecords.stream()
                .filter(record -> gameType.equals(record.get("problema_tipo")))
                .collect(java.util.stream.Collectors.toList());
    }

    /**
     * Format game records from the database for display in the UI
     */
    private static List<Map<String, Object>> formatGameRecordsForDisplay(List<Map<String, String>> gameRecords) {
        List<Map<String, Object>> formattedRecords = new ArrayList<>();

        for (Map<String, String> record : gameRecords) {
            Map<String, Object> formattedRecord = new HashMap<>();

            // Extract UUID and use first 8 characters as ID for display
            String gameId = record.get("game_id");
            formattedRecord.put("id", gameId.substring(0, 8));
            formattedRecord.put("problema_tipo", record.get("game_type"));

            // Format problem name for display
            String problemName;
            switch (record.get("game_type")) {
                case "queens":
                    problemName = "8 Reinas";
                    break;
                case "knight":
                    problemName = "Caballo";
                    break;
                case "hanoi":
                    problemName = "Torres de Hanoi";
                    break;
                default:
                    problemName = "Desconocido";
            }
            formattedRecord.put("problema", problemName);

            // Format size based on game type
            String size;
            if ("hanoi".equals(record.get("game_type"))) {
                size = record.get("disk_count") + " discos";
            } else {
                size = record.get("board_size") + "x" + record.get("board_size");
            }
            formattedRecord.put("tamano", size);

            // Format solution summary
            String solutionSummary;
            if ("queens".equals(record.get("game_type"))) {
                solutionSummary = record.get("solutions_count") + " soluciones";
            } else {
                solutionSummary = record.get("moves_count") + " movimientos";
            }
            formattedRecord.put("solucion_resumen", solutionSummary);

            // Format date
            try {
                LocalDateTime dateTime = LocalDateTime.parse(record.get("started_at"));
                formattedRecord.put("fecha", dateTime.format(DATE_FORMATTER));
            } catch (Exception e) {
                formattedRecord.put("fecha", record.get("started_at"));
            }

            formattedRecords.add(formattedRecord);
        }

        return formattedRecords;
    }

    /**
     * Get specific game details by ID for detailed view
     */
    public static Map<String, Object> obtenerDetallesJuego(String gameId) {
        // Get all records and find the one with matching ID
        List<Map<String, String>> allRecords = baseDeDatos.getGameRecords();
        Map<String, String> gameRecord = null;

        for (Map<String, String> record : allRecords) {
            if (record.get("game_id").startsWith(gameId)) {
                gameRecord = record;
                break;
            }
        }

        if (gameRecord == null) {
            return null;
        }

        List<Map<String, Object>> formattedRecords = formatGameRecordsForDisplay(Collections.singletonList(gameRecord));
        return formattedRecords.isEmpty() ? null : formattedRecords.get(0);
    }
}