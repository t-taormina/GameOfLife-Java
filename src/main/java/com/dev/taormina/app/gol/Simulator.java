package com.dev.taormina.app.gol;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.util.Duration;

public class Simulator {
    private Timeline timeline;
    private MainView mainView;
    private Simulation simulation;

    public Simulator(MainView mainView) {
        this.mainView = mainView;
        this.simulation = mainView.getSimulation();
        this.timeline = new Timeline(new KeyFrame(Duration.millis(500), this::doStep));
        this.timeline.setCycleCount(Timeline.INDEFINITE);
    }

    private void doStep(ActionEvent event) {
        this.simulation.step();
        this.mainView.draw();
    }

    public void start() { this.timeline.play(); }

    public void stop()  { this.timeline.stop(); }
}
