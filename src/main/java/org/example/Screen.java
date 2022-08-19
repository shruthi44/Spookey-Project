package org.example;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Screen {
    static final char BLOCK = '\u2588';
    public static final TextColor
            RED = new TextColor.RGB(255, 0, 0),
            YELLOW = new TextColor.RGB(255, 255, 0);
    private Random random = new Random();
    private DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory();
    private Terminal terminal;
    private char[][] array = new char[80][24]; // remembers the chars in screen

    public Screen() {
        try {
            terminal = terminalFactory.createTerminal();
            terminal.setCursorVisible(false);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public char getChar(int col, int row) {
        return array[col - 1][row - 1];
    }

    public void putChar(int column, int row, char c) throws IOException {
        terminal.setCursorPosition(column, row);
        terminal.setForegroundColor(YELLOW);
        terminal.putCharacter(c);
        array[column - 1][row - 1] = c;
        terminal.flush();
    }

    public void drawDots() throws IOException {
        char dot = '.';
        int columnIndex, rowIndex;
        columnIndex = terminal.getTerminalSize().getColumns();
        rowIndex = terminal.getTerminalSize().getRows();
        for (int col = 2; col < 79; col += 2) {
            for (int row = 1; row < 23; row++) {
                putChar(col, row, dot);
            }
        }
    }

    public List<Position> getObstacles() {
        List<Position> obstacles = getBorderObstacles();
        obstacles.addAll(getCenterObstacles());
        return obstacles;
    }

    public List<Position> getBorderObstacles() {
        List<Position> borderObstacles = new ArrayList<>();

        // Get left and right border positions
        for (int row = 1; row <= 22; row++) {
            borderObstacles.add(new Position(2, row));
            borderObstacles.add(new Position(78, row));
        }

        // Get top and bottom row border positions
        for (int col = 2; col <= 78; col++) {
            borderObstacles.add(new Position(col, 1));
            borderObstacles.add(new Position(col, 22));
        }

        return borderObstacles;
    }
    public List<Position> getCenterObstacles() {
        List<Position> borderObstacles = new ArrayList<>();
        int[] obstacleArrayX = new int[]{16, 32, 48, 64};
        int[] obstacleArrayY = new int[]{5, 9, 13, 17};
        for (int arrayX : obstacleArrayX) {
            for (int j = 0; j < obstacleArrayX.length; j++) {
                borderObstacles.add(new Position(arrayX, obstacleArrayY[j]));
            }
        }
        return borderObstacles;
    }

    public void drawObstacles(List<Position> obstacles) throws IOException {
        char block = '\u2588';
        for (Position p : obstacles) {
            putChar(p.getColumn(), p.getRow(), block);
        }
        terminal.flush();
    }
    public List<Position> getBombPositions() {
        List<Position> bombPositions = new ArrayList<Position>();
        int[] obstacleArrayX = new int[]{8, 24, 40, 56, 72};
        int[] obstacleArrayY = new int[]{4, 8, 12, 16, 20};
        System.out.println("BOMb");
        for (int arrayX : obstacleArrayX) {
            for (int j = 0; j < obstacleArrayX.length; j++) {
                bombPositions.add(new Position(arrayX, obstacleArrayY[j]));
            }
        }
        return bombPositions;
    }

    public void placeBombs(List<Position> bombPositions) throws IOException {
        char bomb = '\u2601';
        for (Position p : bombPositions) {
            putChar(p.getColumn(), p.getRow(), bomb);
        }
        terminal.flush();
    }

    public KeyStroke getInput() throws IOException {
        return terminal.pollInput();
    }

    public void close() throws IOException, InterruptedException {
        terminal.clearScreen();
        String lost = "You have lost the game! Try again!";
        for (int i = 0; i < lost.length(); i++) {
            putChar(i + 22, 11, lost.charAt(i));
        }
        terminal.flush();
        Thread.sleep(3000);
        terminal.close();
    }
}
