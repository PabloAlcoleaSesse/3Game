
package org.example.BD;

import org.example.problemas.Ficha;

import java.io.*;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class BaseDeDatos {
    private static final String GAME_RECORDS_PATH = "BaseDatos/game_records.csv";
    private static final String KNIGHT_MOVES_PATH = "BaseDatos/knight_moves/knightMoves.csv";
    private static final String QUEENS_MOVES_PATH = "BaseDatos/queens_solutions/QueenMoves.csv";
    private static final String HANOI_MOVES_PATH = "BaseDatos/hanoi_moves/HanoiMoves.csv";
    private static final String MOVE_HISTORY_PATH = "BaseDatos/move_history.csv";

    public BaseDeDatos() {
        // Ensure directories exist
        createDirectoriesIfNeeded();

        // Ensure CSV files exist with headers
        initializeFiles();
    }

    private void createDirectoriesIfNeeded() {
        try {
            Files.createDirectories(Paths.get("BaseDatos/knight_moves"));
            Files.createDirectories(Paths.get("BaseDatos/queens_solutions"));
            Files.createDirectories(Paths.get("BaseDatos/hanoi_moves"));
        } catch (IOException e) {
            System.err.println("Error creating directories: " + e.getMessage());
        }
    }

    private void initializeFiles() {
        // Initialize game records file if it doesn't exist
        File gameRecordsFile = new File(GAME_RECORDS_PATH);
        if (!gameRecordsFile.exists()) {
            try (FileWriter writer = new FileWriter(gameRecordsFile)) {
                writer.write("game_id,game_type,board_size,disk_count,moves_count,solutions_count,started_at,finished_at\n");
            } catch (IOException e) {
                System.err.println("Error initializing game records file: " + e.getMessage());
            }
        }

        // Initialize Knight moves file if it doesn't exist
        File knightMovesFile = new File(KNIGHT_MOVES_PATH);
        if (!knightMovesFile.exists()) {
            try (FileWriter writer = new FileWriter(knightMovesFile)) {
                writer.write("step,row,column,algebraic_notation\n");
            } catch (IOException e) {
                System.err.println("Error initializing knight moves file: " + e.getMessage());
            }
        }

        // Initialize Queens moves file if it doesn't exist
        File queensMovesFile = new File(QUEENS_MOVES_PATH);
        if (!queensMovesFile.exists()) {
            try (FileWriter writer = new FileWriter(queensMovesFile)) {
                writer.write("solution_number,queen_number,row,column\n");
            } catch (IOException e) {
                System.err.println("Error initializing queens moves file: " + e.getMessage());
            }
        }

        // Initialize Hanoi moves file if it doesn't exist
        File hanoiMovesFile = new File(HANOI_MOVES_PATH);
        if (!hanoiMovesFile.exists()) {
            try (FileWriter writer = new FileWriter(hanoiMovesFile)) {
                writer.write("step,disk_size,from_tower,to_tower,move_description\n");
            } catch (IOException e) {
                System.err.println("Error initializing hanoi moves file: " + e.getMessage());
            }
        }

        // Initialize move history file if it doesn't exist
        File moveHistoryFile = new File(MOVE_HISTORY_PATH);
        if (!moveHistoryFile.exists()) {
            try (FileWriter writer = new FileWriter(moveHistoryFile)) {
                writer.write("move_id,game_id,step_number,from_position,to_position,timestamp\n");
            } catch (IOException e) {
                System.err.println("Error initializing move history file: " + e.getMessage());
            }
        }
    }

    // Record Knight's Tour game
    public void recordKnightGame(int boardSize, List<Ficha> moveHistory) {
        // Generate unique game ID
        UUID gameId = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();

        // Record game metadata
        try (FileWriter writer = new FileWriter(GAME_RECORDS_PATH, true)) {
            writer.append(gameId.toString()).append(",");
            writer.append("knight").append(",");
            writer.append(String.valueOf(boardSize)).append(",");
            writer.append(","); // No disk_count for knight
            writer.append(String.valueOf(moveHistory.size())).append(",");
            writer.append(","); // No solutions_count for knight
            writer.append(now.toString()).append(",");
            writer.append(now.toString()).append("\n");
        } catch (IOException e) {
            System.err.println("Error recording knight game: " + e.getMessage());
        }

        // Record individual moves
        try (FileWriter knightWriter = new FileWriter(KNIGHT_MOVES_PATH, true);
             FileWriter historyWriter = new FileWriter(MOVE_HISTORY_PATH, true)) {

            for (int i = 0; i < moveHistory.size(); i++) {
                Ficha move = moveHistory.get(i);
                int step = i + 1;
                int row = move.getFila();
                int col = move.getColumna();
                String algebraicNotation = (char)('a' + col) + String.valueOf(row + 1);

                // Write to knight-specific file
                knightWriter.append(String.valueOf(step)).append(",");
                knightWriter.append(String.valueOf(row)).append(",");
                knightWriter.append(String.valueOf(col)).append(",");
                knightWriter.append(algebraicNotation).append("\n");

                // Write to general move history
                UUID moveId = UUID.randomUUID();
                historyWriter.append(moveId.toString()).append(",");
                historyWriter.append(gameId.toString()).append(",");
                historyWriter.append(String.valueOf(step)).append(",");
                historyWriter.append(i == 0 ? "START" : String.valueOf(moveHistory.get(i-1).getColumna()) + "," +
                        String.valueOf(moveHistory.get(i-1).getFila())).append(",");
                historyWriter.append(String.valueOf(col) + "," + String.valueOf(row)).append(",");
                historyWriter.append(now.toString()).append("\n");
            }
        } catch (IOException e) {
            System.err.println("Error recording knight moves: " + e.getMessage());
        }
    }

    // Record Queens game
    public void recordQueensGame(int boardSize, List<List<Ficha>> solutions) {
        // Generate unique game ID
        UUID gameId = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();

        // Record game metadata
        try (FileWriter writer = new FileWriter(GAME_RECORDS_PATH, true)) {
            writer.append(gameId.toString()).append(",");
            writer.append("queens").append(",");
            writer.append(String.valueOf(boardSize)).append(",");
            writer.append(","); // No disk_count for queens
            writer.append(","); // No moves_count for queens
            writer.append(String.valueOf(solutions.size())).append(",");
            writer.append(now.toString()).append(",");
            writer.append(now.toString()).append("\n");
        } catch (IOException e) {
            System.err.println("Error recording queens game: " + e.getMessage());
        }

        // Record solutions
        try (FileWriter queensWriter = new FileWriter(QUEENS_MOVES_PATH, true);
             FileWriter historyWriter = new FileWriter(MOVE_HISTORY_PATH, true)) {

            for (int solutionIndex = 0; solutionIndex < solutions.size(); solutionIndex++) {
                List<Ficha> solution = solutions.get(solutionIndex);
                int solutionNumber = solutionIndex + 1;

                for (int queenIndex = 0; queenIndex < solution.size(); queenIndex++) {
                    Ficha queen = solution.get(queenIndex);
                    int row = queen.getFila();
                    int col = queen.getColumna();

                    // Write to queens-specific file
                    queensWriter.append(String.valueOf(solutionNumber)).append(",");
                    queensWriter.append(String.valueOf(queenIndex + 1)).append(",");
                    queensWriter.append(String.valueOf(row)).append(",");
                    queensWriter.append(String.valueOf(col)).append("\n");

                    // Write to general move history
                    UUID moveId = UUID.randomUUID();
                    historyWriter.append(moveId.toString()).append(",");
                    historyWriter.append(gameId.toString()).append(",");
                    historyWriter.append(String.valueOf(queenIndex + 1)).append(",");
                    historyWriter.append("ROW" + row).append(",");
                    historyWriter.append("COL" + col).append(",");
                    historyWriter.append(now.toString()).append("\n");
                }
            }
        } catch (IOException e) {
            System.err.println("Error recording queens solutions: " + e.getMessage());
        }
    }

    // Record Towers of Hanoi game
    public void recordHanoiGame(int numDiscos, List<String> movimientos) {
        // Generate unique game ID
        UUID gameId = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();

        // Record game metadata
        try (FileWriter writer = new FileWriter(GAME_RECORDS_PATH, true)) {
            writer.append(gameId.toString()).append(",");
            writer.append("hanoi").append(",");
            writer.append(","); // No board_size for hanoi
            writer.append(String.valueOf(numDiscos)).append(",");
            writer.append(String.valueOf(movimientos.size())).append(",");
            writer.append(","); // No solutions_count for hanoi
            writer.append(now.toString()).append(",");
            writer.append(now.toString()).append("\n");
        } catch (IOException e) {
            System.err.println("Error recording hanoi game: " + e.getMessage());
        }

        // Record individual moves
        try (FileWriter hanoiWriter = new FileWriter(HANOI_MOVES_PATH, true);
             FileWriter historyWriter = new FileWriter(MOVE_HISTORY_PATH, true)) {

            for (int i = 0; i < movimientos.size(); i++) {
                String move = movimientos.get(i);
                String[] parts = move.split(" ");

                int step = i + 1;
                int diskSize = Integer.parseInt(parts[2]);
                char fromTower = parts[4].charAt(0);
                char toTower = parts[6].charAt(0);

                // Write to hanoi-specific file
                hanoiWriter.append(String.valueOf(step)).append(",");
                hanoiWriter.append(String.valueOf(diskSize)).append(",");
                hanoiWriter.append(String.valueOf(fromTower)).append(",");
                hanoiWriter.append(String.valueOf(toTower)).append(",");
                hanoiWriter.append("\"").append(move).append("\"").append("\n");

                // Write to general move history
                UUID moveId = UUID.randomUUID();
                historyWriter.append(moveId.toString()).append(",");
                historyWriter.append(gameId.toString()).append(",");
                historyWriter.append(String.valueOf(step)).append(",");
                historyWriter.append(String.valueOf(fromTower)).append(",");
                historyWriter.append(String.valueOf(toTower)).append(",");
                historyWriter.append(now.toString()).append("\n");
            }
        } catch (IOException e) {
            System.err.println("Error recording hanoi moves: " + e.getMessage());
        }
    }

    // Get game records
    public List<Map<String, String>> getGameRecords() {
        List<Map<String, String>> records = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(GAME_RECORDS_PATH))) {
            // Skip header
            String header = reader.readLine();
            String[] columns = header.split(",");

            String line;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                Map<String, String> record = new HashMap<>();

                for (int i = 0; i < columns.length && i < values.length; i++) {
                    record.put(columns[i], values[i]);
                }

                records.add(record);
            }
        } catch (IOException e) {
            System.err.println("Error reading game records: " + e.getMessage());
        }

        return records;
    }

    // Get moves for a specific game
    public List<Map<String, String>> getMovesForGame(UUID gameId) {
        List<Map<String, String>> moves = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(MOVE_HISTORY_PATH))) {
            // Skip header
            String header = reader.readLine();
            String[] columns = header.split(",");

            String line;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");

                // Check if this move belongs to the requested game
                if (values.length > 1 && values[1].equals(gameId.toString())) {
                    Map<String, String> move = new HashMap<>();

                    for (int i = 0; i < columns.length && i < values.length; i++) {
                        move.put(columns[i], values[i]);
                    }

                    moves.add(move);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading move history: " + e.getMessage());
        }

        // Sort moves by step number
        moves.sort((m1, m2) -> {
            int step1 = Integer.parseInt(m1.get("step_number"));
            int step2 = Integer.parseInt(m2.get("step_number"));
            return Integer.compare(step1, step2);
        });

        return moves;
    }

    // Get specific game type moves (Knight, Queens, or Hanoi)
    public List<Map<String, String>> getKnightMoves() {
        return readMovesFromFile(KNIGHT_MOVES_PATH);
    }

    public List<Map<String, String>> getQueensSolutions() {
        return readMovesFromFile(QUEENS_MOVES_PATH);
    }

    public List<Map<String, String>> getHanoiMoves() {
        return readMovesFromFile(HANOI_MOVES_PATH);
    }

    private List<Map<String, String>> readMovesFromFile(String filePath) {
        List<Map<String, String>> moves = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            // Skip header
            String header = reader.readLine();
            String[] columns = header.split(",");

            String line;
            while ((line = reader.readLine()) != null) {
                // Handle potential quoted fields (especially in Hanoi moves)
                List<String> values = parseCSVLine(line);
                Map<String, String> move = new HashMap<>();

                for (int i = 0; i < columns.length && i < values.size(); i++) {
                    move.put(columns[i], values.get(i));
                }

                moves.add(move);
            }
        } catch (IOException e) {
            System.err.println("Error reading moves from " + filePath + ": " + e.getMessage());
        }

        return moves;
    }

    private List<String> parseCSVLine(String line) {
        List<String> result = new ArrayList<>();
        boolean inQuotes = false;
        StringBuilder currentField = new StringBuilder();

        for (char c : line.toCharArray()) {
            if (c == '"') {
                inQuotes = !inQuotes;
            } else if (c == ',' && !inQuotes) {
                result.add(currentField.toString());
                currentField = new StringBuilder();
            } else {
                currentField.append(c);
            }
        }

        result.add(currentField.toString());
        return result;
    }

    // Archive old records to manage file size
    public void archiveOldRecords(int daysToKeep) {
        LocalDateTime cutoffDate = LocalDateTime.now().minusDays(daysToKeep);
        archiveFile(GAME_RECORDS_PATH, cutoffDate, "game_id", "started_at");
        archiveFile(MOVE_HISTORY_PATH, cutoffDate, "move_id", "timestamp");
    }

    private void archiveFile(String filePath, LocalDateTime cutoffDate, String idColumn, String dateColumn) {
        List<String> recentRecords = new ArrayList<>();
        List<String> oldRecords = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String header = reader.readLine();
            recentRecords.add(header);

            // Find indexes of relevant columns
            String[] columns = header.split(",");
            int dateColumnIndex = -1;

            for (int i = 0; i < columns.length; i++) {
                if (columns[i].equals(dateColumn)) {
                    dateColumnIndex = i;
                    break;
                }
            }

            if (dateColumnIndex == -1) {
                System.err.println("Could not find date column in " + filePath);
                return;
            }

            String line;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");

                if (values.length > dateColumnIndex) {
                    String dateStr = values[dateColumnIndex];
                    try {
                        LocalDateTime recordDate = LocalDateTime.parse(dateStr);

                        if (recordDate.isAfter(cutoffDate)) {
                            recentRecords.add(line);
                        } else {
                            oldRecords.add(line);
                        }
                    } catch (Exception e) {
                        // If date parsing fails, keep the record (just to be safe)
                        recentRecords.add(line);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file for archiving: " + e.getMessage());
            return;
        }

        // Write old records to archive file if there are any
        if (!oldRecords.isEmpty()) {
            String archiveFilePath = filePath.replace(".csv", "_archive_" +
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")) + ".csv");

            try (FileWriter writer = new FileWriter(archiveFilePath)) {
                writer.write(recentRecords.get(0) + "\n"); // Write header

                for (String record : oldRecords) {
                    writer.write(record + "\n");
                }

                System.out.println("Archived " + oldRecords.size() + " records to " + archiveFilePath);
            } catch (IOException e) {
                System.err.println("Error writing archive file: " + e.getMessage());
            }
        }

        // Write recent records back to the original file
        try (FileWriter writer = new FileWriter(filePath)) {
            for (String record : recentRecords) {
                writer.write(record + "\n");
            }
        } catch (IOException e) {
            System.err.println("Error writing recent records back to file: " + e.getMessage());
        }
    }
    // Add these methods to your BaseDeDatos class

    public List<Integer> getKnightGameIds() {
        List<Integer> gameIds = new ArrayList<>();
        // This would normally fetch from your database
        // For demonstration, I'll create some sample IDs
        for (int i = 1; i <= 5; i++) {
            gameIds.add(i);
        }
        return gameIds;
    }

    public List<Ficha> getKnightGameMoves(int gameId) {
        List<Ficha> moves = new ArrayList<>();
        // This would normally fetch from your database
        // For demonstration, I'll create some sample moves based on your data

        // Sample data from the provided long list
        String[] data = {"79,7,6,4", "80,1,0,5", "81,2,1,0", "82,3,2,3", "83,4,3,2"};

        for (String moveData : data) {
            String[] parts = moveData.split(",");
            if (parts.length >= 4) {
                int moveNumber = Integer.parseInt(parts[0]);
                int row = Integer.parseInt(parts[2]);
                int col = Integer.parseInt(parts[3]);
                moves.add(new Ficha(Ficha.Tipo.CABALLO, row, col));
            }
        }

        return moves;
    }


}