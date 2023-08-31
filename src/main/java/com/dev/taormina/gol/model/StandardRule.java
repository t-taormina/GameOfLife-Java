package com.dev.taormina.gol.model;

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
        if (board.getState(x, y) == CellState.ALIVE) { count -= 1; }

        for (int i = x - 1; i <= x + 1; i++) {
            for (int j = y - 1; j <= y + 1; j++) {
                if (board.getState(i, j) == CellState.ALIVE) { count += 1; }
            }
        }
        return count;
    }
}
