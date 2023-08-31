package com.dev.taormina.gol;

import com.dev.taormina.gol.model.CellState;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;

public class Toolbar extends ToolBar {
    private Simulator simulator;
    private final MainView mainView;

    public Toolbar(MainView mainView) {
        this.simulator = new Simulator(mainView, mainView.getSimulation());
        this.mainView = mainView;

        Button start = new Button("Start");
        start.setOnAction(actionEvent -> {
            setSimulatingMode();
            this.simulator.start();
        });

        Button stop = new Button("Stop");
        stop.setOnAction(actionEvent -> {
            this.simulator.stop();
        });

        Button step = new Button("Step");
        step.setOnAction(actionEvent -> {
            setSimulatingMode();
            this.mainView.getSimulation().step();
            this.mainView.draw();
        });

        Button reset = new Button("Reset");
        reset.setOnAction(actionEvent -> {
            this.mainView.setApplicationState(MainView.EDITING);
            this.mainView.draw();
        });

        Button draw = new Button("Draw");
        draw.setOnAction(actionEvent -> this.mainView.setDrawMode(CellState.ALIVE));

        Button erase = new Button("Erase");
        erase.setOnAction(actionEvent -> this.mainView.setDrawMode(CellState.DEAD));

        this.getItems().addAll(start, stop, step, reset, draw, erase);
    }

    private void setSimulatingMode() {
        if (this.mainView.getApplicationState() == MainView.EDITING) {
            this.mainView.setApplicationState(MainView.SIMULATING);
            this.simulator = new Simulator(this.mainView, this.mainView.getSimulation());
        }
    }
} // Toolbar
