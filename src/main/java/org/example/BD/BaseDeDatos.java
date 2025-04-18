package org.example.BD;

import org.example.problemas.Ficha;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

public class BaseDeDatos {
    private static final String GAME_RECORDS_FILE = "game_records.csv";
    private static final String KNIGHT_MOVES_DIR = "knight_moves";
    private static final String QUEENS_SOLUTIONS_DIR = "queens_solutions";
    private static final String HANOI_MOVES_DIR = "hanoi_moves";

    public BaseDeDatos() {
        // Create directories if they don't exist
        createDirectoryIfNotExists(KNIGHT_MOVES_DIR);
        createDirectoryIfNotExists(QUEENS_SOLUTIONS_DIR);
        createDirectoryIfNotExists(HANOI_MOVES_DIR);

        // Ensure the game records file exists with headers
        initializeFileIfNotExists(GAME_RECORDS_FILE,
                "game_id,game_type,board_size,disk_count,moves_count,solutions_count,started_at,finished_at");
    }

    private void createDirectoryIfNotExists(String dirName) {
        File dir = new File(dirName);
        if (!dir.exists()) {
            dir.mkdir();
        }
    }

    private void initializeFileIfNotExists(String fileName, String header) {
        File file = new File(fileName);
        if (!file.exists()) {
            try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
                writer.println(header);
            } catch (IOException e) {
                System.err.println("Error creating file: " + e.getMessage());
            }
        }
    }

    public void recordKnightGame(int boardSize, List<Ficha> moveHistory) {
        // Generate a unique ID for this game
        String gameId = UUID.randomUUID().toString();
        LocalDateTime startTime = LocalDateTime.now();

        // Record in the main games file
        try (PrintWriter writer = new PrintWriter(new FileWriter(GAME_RECORDS_FILE, true))) {
            writer.println(String.format("%s,knight,%d,,%d,,%s,%s",
                    gameId, boardSize, moveHistory.size(), startTime, startTime));
        } catch (IOException e) {
            System.err.println("Error recording game: " + e.getMessage());
        }

        // Create a separate file for this game's moves
        String moveFileName = KNIGHT_MOVES_DIR + "/knight_" + gameId + ".csv";
        try (PrintWriter writer = new PrintWriter(new FileWriter(moveFileName))) {
            // Write header
            writer.println("step,row,column,algebraic_notation");

            // Write each move in an easy-to-read format
            int step = 1;
            for (Ficha move : moveHistory) {
                int row = move.getFila();
                int col = move.getColumna();
                char colLetter = (char)('a' + col);
                int rowNumber = boardSize - row;  // Convert to algebraic notation

                writer.println(String.format("%d,%d,%d,%c%d",
                        step, row, col, colLetter, rowNumber));
                step++;
            }
        } catch (IOException e) {
            System.err.println("Error recording knight moves: " + e.getMessage());
        }
    }

    public void recordQueensGame(int boardSize, List<List<Ficha>> solutions) {
        // Generate a unique ID for this game
        String gameId = UUID.randomUUID().toString();
        LocalDateTime startTime = LocalDateTime.now();

        // Record in the main games file
        try (PrintWriter writer = new PrintWriter(new FileWriter(GAME_RECORDS_FILE, true))) {
            writer.println(String.format("%s,queens,%d,,%d,%d,%s,%s",
                    gameId, boardSize, 0, solutions.size(), startTime, startTime));
        } catch (IOException e) {
            System.err.println("Error recording game: " + e.getMessage());
        }

        // Create a separate file for this game's solutions
        String solutionsFileName = QUEENS_SOLUTIONS_DIR + "/queens_" + gameId + ".csv";
        try (PrintWriter writer = new PrintWriter(new FileWriter(solutionsFileName))) {
            // Write header
            writer.println("solution_number,queen_number,row,column,algebraic_notation");

            // Write each solution in an easy-to-read format
            for (int solIndex = 0; solIndex < solutions.size(); solIndex++) {
                List<Ficha> solution = solutions.get(solIndex);
                for (int queenIndex = 0; queenIndex < solution.size(); queenIndex++) {
                    Ficha queen = solution.get(queenIndex);
                    int row = queen.getFila();
                    int col = queen.getColumna();
                    char colLetter = (char)('a' + col);
                    int rowNumber = boardSize - row;  // Convert to algebraic notation

                    writer.println(String.format("%d,%d,%d,%d,%c%d",
                            solIndex + 1, queenIndex + 1, row, col, colLetter, rowNumber));
                }
            }
        } catch (IOException e) {
            System.err.println("Error recording queens solutions: " + e.getMessage());
        }
    }

    public void recordHanoiGame(int numDisks, List<String> moves) {
        // Generate a unique ID for this game
        String gameId = UUID.randomUUID().toString();
        LocalDateTime startTime = LocalDateTime.now();

        // Record in the main games file
        try (PrintWriter writer = new PrintWriter(new FileWriter(GAME_RECORDS_FILE, true))) {
            writer.println(String.format("%s,hanoi,,%d,%d,,%s,%s",
                    gameId, numDisks, moves.size(), startTime, startTime));
        } catch (IOException e) {
            System.err.println("Error recording game: " + e.getMessage());
        }

        // Create a separate file for this game's moves
        String moveFileName = HANOI_MOVES_DIR + "/hanoi_" + gameId + ".csv";
        try (PrintWriter writer = new PrintWriter(new FileWriter(moveFileName))) {
            // Write header
            writer.println("step,disk_size,from_tower,to_tower,move_description");

            // Write each move in an easy-to-read format
            for (int i = 0; i < moves.size(); i++) {
                String move = moves.get(i);

                // Parse the move string "Mover disco X de Y a Z"
                String[] parts = move.split(" ");
                int diskSize = Integer.parseInt(parts[2]);
                char fromTower = parts[4].charAt(0);
                char toTower = parts[6].charAt(0);

                writer.println(String.format("%d,%d,%c,%c,\"%s\"",
                        i + 1, diskSize, fromTower, toTower, move));
            }
        } catch (IOException e) {
            System.err.println("Error recording Hanoi moves: " + e.getMessage());
        }
    }
}