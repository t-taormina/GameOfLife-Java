package com.dev.taormina.app;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;

public class MainView extends VBox {
    private final Canvas canvas;
    private final Simulation simulation;
    private final Affine affine;
    private int drawMode = 1;

    public MainView() {
        this.canvas = new Canvas(400, 400);
        this.canvas.setOnMousePressed(this::handleDraw);
        this.canvas.setOnMouseDragged(this::handleDraw);

        this.setOnKeyPressed(this::keyHandler);

        this.simulation = new Simulation(10, 10);

        Button stepButton = new Button("Step");
        stepButton.setOnAction(actionEvent -> {
            this.simulation.step();
            draw();
        });

        this.affine = new Affine();
        this.getChildren().addAll(stepButton, this.canvas);
        this.affine.appendScale(400 / 10f, 400 / 10f);
    }

    private void keyHandler(KeyEvent event) {
        if (event.getCode() == KeyCode.D ) {
            this.drawMode = 0;
        }
        if (event.getCode() == KeyCode.A) {
            this.drawMode = 1;
        }
    }

    private void handleDraw(MouseEvent event) {
        int mouseX =  (int) (event.getX() / 40f);
        int mouseY = (int) (event.getY() / 40f);
        if (this.drawMode == 0) {
            this.simulation.setDead(mouseX, mouseY);
        } else {
            this.simulation.setAlive(mouseX, mouseY);
        }
        draw();
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
