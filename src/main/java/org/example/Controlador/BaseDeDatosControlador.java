// File: src/main/java/org/example/Controlador/BaseDeDatosControlador.java
package org.example.Controlador;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.example.Modelo.BaseDeDatosModelo;
import org.example.Modelo.Ficha;

public class BaseDeDatosControlador {
    private final BaseDeDatosModelo modelo;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public BaseDeDatosControlador() {
        this.modelo = new BaseDeDatosModelo();
    }

    // Record a Knight's Tour game
    public void recordKnightGame(int boardSize, List<Ficha> moveHistory) {
        String gameId = modelo.generateGameId();
        LocalDateTime now = LocalDateTime.now();
        modelo.appendToGameRecords(gameId, "knight", boardSize, 0, moveHistory.size(), 0, now, now);

        // Append moves to knightMoves.csv
        for (int i = 0; i < moveHistory.size(); i++) {
            Ficha move = moveHistory.get(i);
            modelo.appendToKnightMoves(gameId, i + 1, move.getFila(), move.getColumna());
        }
    }

    // Record a Towers of Hanoi game
    public void recordTowerOfHanoiGame(int numDiscs, List<String> moves) {
        String gameId = modelo.generateGameId();
        LocalDateTime now = LocalDateTime.now();
        modelo.appendToGameRecords(gameId, "hanoi", 0, numDiscs, moves.size(), 0, now, now);

        // Append moves to HanoiMoves.csv
        for (int i = 0; i < moves.size(); i++) {
            String[] moveParts = moves.get(i).split(" ");
            int diskSize = Integer.parseInt(moveParts[1]);
            String fromTower = moveParts[3];
            String toTower = moveParts[5];
            modelo.appendToHanoiMoves(gameId, i + 1, diskSize, fromTower, toTower, moves.get(i));
        }
    }

    // Record an N-Queens game
    public void recordQueenGameSolutions(int boardSize, List<List<Ficha>> solutions) {
        String gameId = modelo.generateGameId();
        LocalDateTime now = LocalDateTime.now();
        modelo.appendToGameRecords(gameId, "queens", boardSize, 0, 0, solutions.size(), now, now);

        // Append solutions to QueenMoves.csv
        for (int i = 0; i < solutions.size(); i++) {
            List<Ficha> solution = solutions.get(i);
            for (Ficha queen : solution) {
                modelo.appendToQueenMoves(gameId, i + 1, queen.getFila(), queen.getColumna());
            }
        }
    }

    // Retrieve game history for display
    public List<Map<String, Object>> obtenerHistorialJuegos() {
        List<Map<String, String>> gameRecords = modelo.getGameRecords();
        return formatGameRecordsForDisplay(gameRecords);
    }

    public List<Map<String, Object>> obtenerHistorialJuegosPorTipo(String gameType) {
        List<Map<String, Object>> allRecords = obtenerHistorialJuegos();
        if (gameType == null) {
            return allRecords;
        }
        return allRecords.stream()
                .filter(record -> gameType.equals(record.get("problema_tipo")))
                .collect(Collectors.toList());
    }

    private List<Map<String, Object>> formatGameRecordsForDisplay(List<Map<String, String>> gameRecords) {
        List<Map<String, Object>> formattedRecords = new ArrayList<>();
        for (Map<String, String> record : gameRecords) {
            Map<String, Object> formattedRecord = new HashMap<>();
            formattedRecord.put("id", record.get("game_id").substring(0, 8));
            formattedRecord.put("problema", switch (record.get("game_type")) {
                case "knight" -> "Caballo";
                case "hanoi" -> "Torres de Hanoi";
                case "queens" -> "8 Reinas";
                default -> "Desconocido";
            });
            formattedRecord.put("tamano", "hanoi".equals(record.get("game_type"))
                    ? record.get("disk_count") + " discos"
                    : record.get("board_size") + "x" + record.get("board_size"));
            formattedRecord.put("solucion_resumen", "queens".equals(record.get("game_type"))
                    ? record.get("solutions_count") + " soluciones"
                    : record.get("moves_count") + " movimientos");
            formattedRecord.put("fecha", record.get("started_at"));
            formattedRecords.add(formattedRecord);
        }
        return formattedRecords;
    }
}