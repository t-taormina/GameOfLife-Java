package com.dev.taormina.gol;

import com.dev.taormina.gol.model.CellState;
import com.dev.taormina.gol.viewModel.ApplicationState;
import com.dev.taormina.gol.viewModel.ApplicationViewModel;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;

public class Toolbar extends ToolBar {
    private Simulator simulator;
    private final MainView mainView;
    private final ApplicationViewModel applicationViewModel;

    public Toolbar(MainView mainView, ApplicationViewModel applicationViewModel) {
        this.simulator = new Simulator(mainView, mainView.getSimulation());
        this.mainView = mainView;
        this.applicationViewModel = applicationViewModel;

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
            this.applicationViewModel.setApplicationState(ApplicationState.EDITING);
            this.simulator = null;
            this.mainView.draw();
        });

        Button draw = new Button("Draw");
        draw.setOnAction(actionEvent -> this.mainView.setDrawMode(CellState.ALIVE));

        Button erase = new Button("Erase");
        erase.setOnAction(actionEvent -> this.mainView.setDrawMode(CellState.DEAD));

        this.getItems().addAll(start, stop, step, reset, draw, erase);
    }

    private void setSimulatingMode() {
        this.applicationViewModel.setApplicationState(ApplicationState.SIMULATING);
        this.simulator = new Simulator(this.mainView, this.mainView.getSimulation());
    }
} // Toolbar
