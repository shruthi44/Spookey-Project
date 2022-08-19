package org.example;

public class Positions {
    private int px;
    private int py;

    public Positions(int px, int py) {
        this.px = px;
        this.py = py;
    }
    public void setPx(int px) {
        this.px = px;
    }

    public void setPy(int py) {
        this.py = py;
    }
    public int getPx() {
        return px;
    }

    public int getPy() {
        return py;
    }
}
