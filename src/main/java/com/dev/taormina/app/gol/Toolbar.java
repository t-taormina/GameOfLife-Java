package com.dev.taormina.app.gol;

import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import com.dev.taormina.app.gol.model.CellState;

public class Toolbar extends ToolBar {
    private static final boolean ALIVE = true;
    private static final boolean DEAD = false;
    private Simulator simulator;

    public Toolbar(MainView mv) {
        this.simulator = new Simulator(mv);
        Button start = new Button("Start");
        start.setOnAction(actionEvent -> {
            mv.setMode(MainView.SIMULATING);
            this.simulator.start();
        });

        Button stop = new Button("Stop");
        stop.setOnAction(actionEvent -> {
            this.simulator.stop();
        });

        Button step = new Button("Step");
        step.setOnAction(actionEvent -> {
            mv.setMode(MainView.SIMULATING);
            mv.getSimulation().step();
            mv.draw();
        });

        Button reset = new Button("Reset");
        reset.setOnAction(actionEvent -> {
            mv.setMode(MainView.EDITING);
            mv.draw();
        });

        Button draw = new Button("Draw");
        draw.setOnAction(actionEvent -> mv.setDrawMode(CellState.ALIVE));

        Button erase = new Button("Erase");
        erase.setOnAction(actionEvent -> mv.setDrawMode(CellState.DEAD));

        this.getItems().addAll(start, stop, step, reset, draw, erase);
    }
} // Toolbar
