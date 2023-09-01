package com.dev.taormina.gol;

import com.dev.taormina.gol.model.CellState;
import com.dev.taormina.gol.model.StandardRule;
import com.dev.taormina.gol.viewModel.ApplicationState;
import com.dev.taormina.gol.viewModel.ApplicationViewModel;
import com.dev.taormina.gol.viewModel.BoardViewModel;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;

public class Toolbar extends ToolBar {
    private Simulator simulator;
    private final MainView mainView;
    private final ApplicationViewModel applicationViewModel;
    private final BoardViewModel boardViewModel;

    public Toolbar(MainView mainView, ApplicationViewModel applicationViewModel, BoardViewModel boardViewModel) {
        this.simulator = null;
        this.mainView = mainView;
        this.applicationViewModel = applicationViewModel;
        this.boardViewModel = boardViewModel;

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
            this.simulator.doStep();
        });

        Button reset = new Button("Reset");
        reset.setOnAction(actionEvent -> {
            this.applicationViewModel.setApplicationState(ApplicationState.EDITING);
            this.simulator = null;
        });

        Button draw = new Button("Draw");
        draw.setOnAction(actionEvent -> this.mainView.setDrawMode(CellState.ALIVE));

        Button erase = new Button("Erase");
        erase.setOnAction(actionEvent -> this.mainView.setDrawMode(CellState.DEAD));

        this.getItems().addAll(start, stop, step, reset, draw, erase);
    }

    private void setSimulatingMode() {
        this.applicationViewModel.setApplicationState(ApplicationState.SIMULATING);
        Simulation simulation = new Simulation(boardViewModel.getBoard(), new StandardRule());
        this.simulator = new Simulator(this.boardViewModel, simulation);
    }
} // Toolbar
