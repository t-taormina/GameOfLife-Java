package com.dev.taormina.gol.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StandardRuleTest {
    private Board board;
    private SimulationRule simulationRule;

    @BeforeEach
    void setUp() {
        board = new BoundedBoard(3, 3);
        simulationRule = new StandardRule();
    }

    @Test
    void getNextState_liveCellWithFewerThanTwoNeighbors() {
        board.setState(0, 0, CellState.ALIVE);
        board.setState(0, 1, CellState.ALIVE);
        CellState state = simulationRule.getNextState(0,0, board);
        assertEquals(CellState.DEAD, state);
    }

    @Test
    void getNextState_liveCellWithTwoNeighbors() {
        board.setState(0, 0, CellState.ALIVE);
        board.setState(0, 1, CellState.ALIVE);
        board.setState(1, 1, CellState.ALIVE);
        CellState state = simulationRule.getNextState(0,0, board);
        assertEquals(CellState.ALIVE, state);
    }

    @Test
    void getNextState_liveCellWithThreeNeighbors() {
        board.setState(0, 0, CellState.ALIVE);
        board.setState(0, 1, CellState.ALIVE);
        board.setState(1, 1, CellState.ALIVE);
        board.setState(1, 0, CellState.ALIVE);
        CellState state = simulationRule.getNextState(1,1, board);
        assertEquals(CellState.ALIVE, state);
    }

    @Test
    void getNextState_liveCellWithMoreThanThreeNeighbors() {
        board.setState(0, 0, CellState.ALIVE);
        board.setState(0, 1, CellState.ALIVE);
        board.setState(1, 1, CellState.ALIVE);
        board.setState(1, 0, CellState.ALIVE);
        board.setState(2, 1, CellState.ALIVE);
        CellState state = simulationRule.getNextState(1,1, board);
        assertEquals(CellState.DEAD, state);
    }

    @Test
    void getNextState_deadCellWithThreeNeighbors() {
        board.setState(0, 0, CellState.ALIVE);
        board.setState(0, 1, CellState.ALIVE);
        board.setState(1, 0, CellState.ALIVE);
        CellState state = simulationRule.getNextState(1,1, board);
        assertEquals(CellState.ALIVE, state);
    }
}