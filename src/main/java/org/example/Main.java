package org.example;

import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        Screen screen = new Screen();
        Spooky spooky = new Spooky(screen);

        // Draw dots
        screen.drawDots();

        // Draw obstacles
        List<Position> obstacles = screen.getObstacles();
        screen.drawObstacles(obstacles);

        // Draw bombs
        List<Position> bombs = screen.getBombPositions();
        screen.placeBombs(bombs);

        spooky.spookyMovement(40, 11, obstacles, bombs);
        // screen.addTreasure();
    }
}