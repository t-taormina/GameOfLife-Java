package com.dev.taormina.gol;

import com.dev.taormina.gol.model.CellState;
import com.dev.taormina.gol.viewModel.*;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;

public class Toolbar extends ToolBar {
    private final ApplicationViewModel applicationViewModel;
    private final EditorViewModel editorViewModel;
    private final SimulationViewModel simulationViewModel;

    public Toolbar(ApplicationViewModel applicationViewModel, EditorViewModel editorViewModel, SimulationViewModel simulationViewModel) {
        this.editorViewModel = editorViewModel;
        this.simulationViewModel = simulationViewModel;
        this.applicationViewModel = applicationViewModel;

        Button start = new Button("Start");
        start.setOnAction(actionEvent -> {
            setSimulatingMode();
            this.simulationViewModel.start();
        });

        Button stop = new Button("Stop");
        stop.setOnAction(actionEvent -> this.simulationViewModel.stop());

        Button step = new Button("Step");
        step.setOnAction(actionEvent -> {
            setSimulatingMode();
            this.simulationViewModel.doStep();
        });

        Button reset = new Button("Reset");
        reset.setOnAction(actionEvent -> this.applicationViewModel.setApplicationState(ApplicationState.EDITING));

        Button draw = new Button("Draw");
        draw.setOnAction(actionEvent -> this.editorViewModel.setDrawMode(CellState.ALIVE));

        Button erase = new Button("Erase");
        erase.setOnAction(actionEvent -> this.editorViewModel.setDrawMode(CellState.DEAD));

        this.getItems().addAll(start, stop, step, reset, draw, erase);
    }

    private void setSimulatingMode() {
        this.applicationViewModel.setApplicationState(ApplicationState.SIMULATING);
        this.simulationViewModel.onAppStateChanged(ApplicationState.SIMULATING);
    }
} // Toolbar
