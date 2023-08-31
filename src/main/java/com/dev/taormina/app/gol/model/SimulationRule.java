package com.dev.taormina.app.gol.model;

public interface SimulationRule {
    CellState getNextState(int x, int y, Board board);
}
