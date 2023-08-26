package com.dev.taormina.app;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;

public class MainView extends VBox {

    private Button stepButton;
    private Canvas canvas;
    private Simulation simulation;
    private Affine affine;
    public MainView() {
        this.stepButton = new Button("Step");
        this.canvas = new Canvas(400, 400);
        this.affine = new Affine();
        this.simulation = new Simulation(10, 10);

        this.getChildren().addAll(this.stepButton, this.canvas);
        this.affine.appendScale(400 / 10f, 400 / 10f);

        this.stepButton.setOnAction(actionEvent -> {
            this.simulation.step();
            draw();
        });

    }

    public void draw() {
        GraphicsContext g = this.canvas.getGraphicsContext2D();
        g.setTransform(this.affine);
        g.setFill(Color.LIGHTGRAY);

        // Fill background
        g.fillRect(0, 0, 400, 400);


        // Fill cell iff cell is alive
        g.setFill(Color.BLACK);
        for (int x = 0; x < this.simulation.height; x++) {
            for (int y = 0; y < this.simulation.height; y++) {
                if (this.simulation.isAlive(x, y)) {
                    g.fillRect(x, y, 1, 1);
                }
            }
        }

        // Draw Grid lines
        g.setStroke(Color.GRAY);
        g.setLineWidth(0.05f);
        for (int x = 0; x <= this.simulation.height; x++) {
            g.strokeLine(x, 0, x, 10);
            for (int y = 0; y <= this.simulation.height; y++) {
                g.strokeLine(0, y, 10, y);
            }
        }

    } // draw

} // MainView
