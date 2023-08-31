package com.dev.taormina.gol;

import com.dev.taormina.gol.model.Board;
import com.dev.taormina.gol.model.SimulationRule;

public class Simulation {
    private Board board;
    private final SimulationRule simulationRule;

    public Simulation(Board simulationBoard, SimulationRule simulationRule) {
        this.board = simulationBoard;
        this.simulationRule = simulationRule;
    }

    public void step() {
        Board nextState = this.board.copy();
        for (int x = 0; x < this.board.getWidth(); x++) {
            for (int y = 0; y < this.board.getHeight(); y++) {
                nextState.setState(x, y, this.simulationRule.getNextState(x, y, this.board));
            }
        }
        this.board = nextState;
    }

    public Board getBoard() {
        return board;
    }
} // Simulation
