// File: BaseDeDatosModelo.java
package org.example.Modelo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class BaseDeDatosModelo {
    private static final String GAME_RECORDS_PATH = "BaseDatos/game_records.csv";
    private static final String KNIGHT_MOVES_PATH = "BaseDatos/knight_moves/knightMoves.csv";
    private static final String HANOI_MOVES_PATH = "BaseDatos/hanoi_moves/HanoiMoves.csv";
    private static final String QUEENS_MOVES_PATH = "BaseDatos/queens_solutions/QueenMoves.csv";

    public String generateGameId() {
        return UUID.randomUUID().toString();
    }

    public void appendToGameRecords(String gameId, String gameType, int boardSize, int diskCount, int movesCount, int solutionsCount, LocalDateTime startedAt, LocalDateTime finishedAt) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(GAME_RECORDS_PATH, true))) {
            writer.write(String.join(",", gameId, gameType, String.valueOf(boardSize), String.valueOf(diskCount),
                    String.valueOf(movesCount), String.valueOf(solutionsCount), startedAt.toString(), finishedAt.toString()));
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void appendToKnightMoves(String gameId, int step, int row, int column) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(KNIGHT_MOVES_PATH, true))) {
            writer.write(String.join(",", gameId, String.valueOf(step), String.valueOf(row), String.valueOf(column)));
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void appendToHanoiMoves(String gameId, int step, int diskSize, String fromTower, String toTower, String moveDescription) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(HANOI_MOVES_PATH, true))) {
            writer.write(String.join(",", gameId, String.valueOf(step), String.valueOf(diskSize), fromTower, toTower, moveDescription));
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void appendToQueenMoves(String gameId, int solutionNumber, int row, int column) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(QUEENS_MOVES_PATH, true))) {
            writer.write(String.join(",", gameId, String.valueOf(solutionNumber), String.valueOf(row), String.valueOf(column)));
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Map<String, String>> getGameRecords() {
        List<Map<String, String>> records = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(GAME_RECORDS_PATH))) {
            String header = reader.readLine(); // Skip header
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                Map<String, String> record = new HashMap<>();
                record.put("game_id", parts[0]);
                record.put("game_type", parts[1]);
                record.put("board_size", parts[2]);
                record.put("disk_count", parts[3]);
                record.put("moves_count", parts[4]);
                record.put("solutions_count", parts[5]);
                record.put("started_at", parts[6]);
                record.put("finished_at", parts[7]);
                records.add(record);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return records;
    }
}