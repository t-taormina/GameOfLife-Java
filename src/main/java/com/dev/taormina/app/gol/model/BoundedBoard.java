package com.dev.taormina.app.gol.model;

public class BoundedBoard implements Board {
    private int width;
    private int height;
    private CellState[][] board;

    public BoundedBoard(int width, int height) {
        this.width = width;
        this.height = height;
        this.board = new CellState[width][height];

        for (int i = 0; i < this.width; i++) {
            for (int j = 0; j < this.height; j++) {
                setState(i, j, CellState.DEAD);
            }
        }
    }

    @Override
    public BoundedBoard copy() {
        BoundedBoard boundedBoard = new BoundedBoard(this.width, this.height);
        for (int i = 0; i < this.width; i++) {
            for (int j = 0; j < this.height; j++) {
                boundedBoard.setState(i, j, this.getState(i, j));
            }
        }
        return boundedBoard;
    }

    @Override
    public CellState getState(int x, int y) {
        if (inBounds(x, y)) {
            return this.board[x][y];
        } else {
            return CellState.DEAD;
        }
    }

    @Override
    public void setState(int x, int y, CellState cellState) {
        if (inBounds(x, y)) {
            this.board[x][y] = cellState;
        }
    }

    @Override
    public int getWidth() {
        return this.width;
    }

    @Override
    public int getHeight() {
        return this.height;
    }

    private boolean inBounds(int x, int y) {
        return x >= 0 && x < this.width && y >= 0 && y < this.height;
    }
}
