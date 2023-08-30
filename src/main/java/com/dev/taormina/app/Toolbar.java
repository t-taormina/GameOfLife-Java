package com.dev.taormina.app;

import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;

public class Toolbar extends ToolBar {
    private static final boolean ALIVE = true;
    private static final boolean DEAD = false;

    public Toolbar(MainView mv) {
        Button start = new Button("Start");
        start.setOnAction(actionEvent -> {
            mv.setState(MainView.SIMULATING);
            mv.getSimulator().start();
        });

        Button stop = new Button("Stop");
        stop.setOnAction(actionEvent -> {
            mv.getSimulator().stop();
        });

        Button step = new Button("Step");
        step.setOnAction(actionEvent -> {
            mv.setState(MainView.SIMULATING);
            mv.getSimulation().step();
            mv.draw();
        });

        Button reset = new Button("Reset");
        reset.setOnAction(actionEvent -> {
            mv.setState(MainView.EDITING);
            mv.draw();
        });

        Button draw = new Button("Draw");
        draw.setOnAction(actionEvent -> mv.setDrawMode(ALIVE));

        Button erase = new Button("Erase");
        erase.setOnAction(actionEvent -> mv.setDrawMode(DEAD));

        this.getItems().addAll(start, stop, step, reset, draw, erase);
    }
} // Toolbar
