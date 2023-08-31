package com.dev.taormina.app.gol.model;

public class StandardRule implements SimulationRule {
    @Override
    public CellState getNextState(int x, int y, Board board) {
        CellState state = board.getState(x, y);
        int aliveNeighbors = countAliveNeighbors(x, y, board);

        if (state == CellState.ALIVE) {
            if (aliveNeighbors != 2 && aliveNeighbors != 3) {
                state = CellState.DEAD;
            }
        } else {
            if (aliveNeighbors == 3) {
                state = CellState.ALIVE;
            }
        }
        return state;
    }

    private int countAliveNeighbors(int x, int y, Board board) {
        int count = 0;
        if (board.getState(x - 1, y - 1) == CellState.ALIVE) { count += 1; }
        if (board.getState(x - 1, y) == CellState.ALIVE) { count += 1; }
        if (board.getState(x - 1, y + 1) == CellState.ALIVE) { count += 1; }
        if (board.getState(x , y - 1) == CellState.ALIVE) { count += 1; }
        if (board.getState(x, y + 1) == CellState.ALIVE) { count += 1; }
        if (board.getState(x + 1, y - 1) == CellState.ALIVE) { count += 1; }
        if (board.getState(x + 1, y) == CellState.ALIVE) { count += 1; }
        if (board.getState(x + 1, y + 1) == CellState.ALIVE) { count += 1; }
        return count;
    }
}
