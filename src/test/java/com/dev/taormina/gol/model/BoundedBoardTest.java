package com.dev.taormina.gol.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoundedBoardTest {
    private Board board;

    @BeforeEach
    void setUp() {
        this.board = new BoundedBoard(5, 3);
    }

    @Test
    void copy_sameSizeAsOriginal() {
        Board copy = this.board.copy();
        assertEquals(5, copy.getWidth());
        assertEquals(3, copy.getHeight());
    }

    @Test
    void copy_deepCopy() {
        Board copy = this.board.copy();
        copy.setState(3, 2, CellState.ALIVE);

        assertEquals(copy.getState(3, 2), CellState.ALIVE);
        assertEquals(this.board.getState(3, 2), CellState.DEAD);
    }

    @Test
    void copy_contentsAreSame() {
        board.setState(0, 0, CellState.ALIVE);
        board.setState(0, 1, CellState.ALIVE);
        board.setState(0, 2, CellState.ALIVE);

        Board copy = board.copy();

        for (int i = 0; i < copy.getWidth(); i++) {
            for (int j = 0; j < copy.getHeight(); j++) {
                assertEquals(this.board.getState(i, j) ,copy.getState(i, j));
            }
            
        }
    }

    @Test
    void setState_setOutOfBoundsX() {
        int outOfBoundsX = board.setState(-1, 0, CellState.ALIVE);
        assertEquals(-1, outOfBoundsX);
        outOfBoundsX = board.setState(6, 0, CellState.ALIVE);
        assertEquals(-1, outOfBoundsX);
    }

    @Test
    void setState_setOutOfBoundsY() {
        int outOfBoundsY = board.setState(0, -1, CellState.ALIVE);
        assertEquals(-1, outOfBoundsY);
        outOfBoundsY = board.setState(0, 5, CellState.ALIVE);
        assertEquals(-1, outOfBoundsY);
    }

    @Test
    void getState_getOutOfBoundsX() {
        CellState state = board.getState(-1, 0);
        assertEquals(CellState.DEAD, state);
        state = board.getState(6, 0);
        assertEquals(CellState.DEAD, state);
    }

    @Test
    void getState_getOutOfBoundsY() {
        CellState state = board.getState(0, -1);
        assertEquals(CellState.DEAD, state);
        state = board.getState(0, 4);
        assertEquals(CellState.DEAD, state);
    }

    @Test
    void getState_ensureStateChanges() {
        board.setState(2, 2, CellState.ALIVE);
        CellState cellState = board.getState(2, 2);
        assertEquals(CellState.ALIVE, cellState);
    }
}