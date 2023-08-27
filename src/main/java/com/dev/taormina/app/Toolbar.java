package com.dev.taormina.app;

import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;

public class Toolbar extends ToolBar {
    private static final boolean ALIVE = true;
    private static final boolean DEAD = false;

    public Toolbar(MainView mv) {
        Button step = new Button("Step");
        step.setOnAction(actionEvent -> {
            mv.getSimulation().step();
            mv.draw();
        });

        Button draw = new Button("Draw");
        draw.setOnAction(actionEvent -> mv.setDrawMode(ALIVE));

        Button erase = new Button("Erase");
        erase.setOnAction(actionEvent -> mv.setDrawMode(DEAD));

        this.getItems().addAll(step, draw, erase);
    }
} // Toolbar
