package org.example;

import com.googlecode.lanterna.input.KeyStroke;

import java.io.IOException;
import java.util.List;

public class Spooky {
    private final char spooky;
    private final char space;
    private Screen screen;

    public Spooky(Screen screen) throws IOException {
        this.spooky = '\u2620';
        this.screen = screen;
        this.space = ' ';
    }
    public char getSpooky() {
        return spooky;
    }
    public void spookyMovement(int column, int row, List<Position> obstacles, List<Position> bombPositions) throws IOException, InterruptedException {
        screen.putChar(40, 11, spooky);
        boolean continueReadingInput = true;
        int oldColumn;
        int oldRow;
        while (continueReadingInput) {
            KeyStroke keyStroke = null;
            do {
                Thread.sleep(5); // might throw InterruptedException
                keyStroke = screen.getInput();
            } while (keyStroke == null);

            oldColumn = column; // save old position column
            oldRow = row; // save old position y(row)

            switch (keyStroke.getKeyType()) {
                case ArrowDown -> row++;
                case ArrowUp -> row--;
                case ArrowRight -> column += 2;
                case ArrowLeft -> column -= 2;
            }

            for (Position p : obstacles) {
                if ((p.getColumn() == column && p.getRow() == row) || (column == 0) || (column == 78) || (row == 0) || (row == 23)) {
                    char c1 = screen.getChar(p.getColumn(), p.getRow());
                    char c2 = screen.getChar(column, row);
                    if (c1 == c2) {
                        column = oldColumn;
                        row = oldRow;
                    }
                }
            }

            for (Position p : bombPositions) {
                if ((p.getColumn() == column && p.getRow() == row) || (column == 0) || (column == 78) || (row == 0) || (row == 23)) {
                    char c1 = screen.getChar(p.getColumn(), p.getRow());
                    char c2 = screen.getChar(column, row);
                    if (c1 == c2) {
                        screen.close();
                    }
                }
            }
            //---------------------------------------------
            screen.putChar(oldColumn, oldRow, space);
            screen.putChar(column, row, spooky);

            Character quit = keyStroke.getCharacter();
            if (quit == Character.valueOf('q')) {
                continueReadingInput = false;
                System.out.println("quit");
            }
        }
    }

}
