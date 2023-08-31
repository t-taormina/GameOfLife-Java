package com.dev.taormina.gol.model;

public interface Board {
    Board copy();

    CellState getState(int x, int y);

    int setState(int x, int y, CellState cellState);

    int getWidth();

    int getHeight();

}
