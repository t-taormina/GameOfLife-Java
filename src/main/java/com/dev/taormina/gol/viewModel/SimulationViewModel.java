package com.dev.taormina.gol.viewModel;

import com.dev.taormina.gol.Simulation;
import com.dev.taormina.gol.model.StandardRule;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class SimulationViewModel {
    private final Timeline timeline;
    private final BoardViewModel boardViewModel;
    private Simulation simulation;

    public SimulationViewModel(BoardViewModel boardViewModel, Simulation simulation) {
        this.boardViewModel = boardViewModel;
        this.simulation = simulation;
        this.timeline = new Timeline(new KeyFrame(Duration.millis(500), event -> this.doStep()));
        this.timeline.setCycleCount(Timeline.INDEFINITE);
    }

    public void doStep() {
        this.simulation.step();
        this.boardViewModel.setBoard(this.simulation.getBoard());
    }

    public void onAppStateChanged(ApplicationState applicationState) {
        if (applicationState == ApplicationState.SIMULATING) {
            this.simulation = new Simulation(boardViewModel.getBoard(), new StandardRule());
        }
    }

    public void start() { this.timeline.play(); }

    public void stop()  { this.timeline.stop(); }
}
