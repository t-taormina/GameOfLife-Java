package com.dev.taormina.app;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;

public class MainView extends VBox {
    private final Canvas canvas;
    private final Simulation simulation;
    private final Affine affine;
    private boolean drawMode = true;
    private InfoBar infobar;

    public MainView() {
        this.canvas = new Canvas(400, 400);
        this.canvas.setOnMousePressed(this::handleDraw);
        this.canvas.setOnMouseDragged(this::handleDraw);
        this.canvas.setOnMouseMoved(this::handleHover);

        this.setOnKeyPressed(this::keyHandler);

        this.simulation = new Simulation(10, 10);

        Toolbar toolbar = new Toolbar(this);
        this.infobar = new InfoBar();

        this.infobar.setDrawModeFormat(this.drawMode);
        this.infobar.setCursorPosFormat(0, 0);

        Pane spacer = new Pane();
        spacer.setMinSize(0,0);
        spacer.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        VBox.setVgrow(spacer, Priority.ALWAYS);

        this.getChildren().addAll(toolbar, this.canvas, spacer, infobar);

        this.affine = new Affine();
        this.affine.appendScale(400 / 10f, 400 / 10f);
    }

    private void handleHover(MouseEvent event) {
        int mouseX =  (int) (event.getX() / 40f);
        int mouseY = (int) (event.getY() / 40f);
        this.infobar.setCursorPosFormat(mouseX, mouseY);
    }

    private void keyHandler(KeyEvent event) {
        if (event.getCode() == KeyCode.E ) {
            this.drawMode = false;
        }
        if (event.getCode() == KeyCode.D) {
            this.drawMode = true;
        }
        this.infobar.setDrawModeFormat(this.drawMode);
    }

    private void handleDraw(MouseEvent event) {
        int mouseX =  (int) (event.getX() / 40f);
        int mouseY = (int) (event.getY() / 40f);
        if (this.drawMode) {
            this.simulation.setAlive(mouseX, mouseY);
        } else {
            this.simulation.setDead(mouseX, mouseY);
        }
        this.infobar.setCursorPosFormat(mouseX, mouseY);
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

    public void setDrawMode(boolean mode) {
        this.drawMode = mode;
        this.infobar.setDrawModeFormat(mode);
    }

    public Simulation getSimulation() {
        return this.simulation;
    }

} // MainView
