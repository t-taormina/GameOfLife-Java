package com.dev.taormina.app;

import javafx.geometry.Point2D;
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
    private final InfoBar infobar;

    public MainView() {
        this.canvas = new Canvas(400, 400);

        this.simulation = new Simulation(10, 10);

        Toolbar toolbar = new Toolbar(this);

        this.infobar = new InfoBar();
        this.infobar.setDrawModeFormat(this.drawMode);
        this.infobar.setCursorPositionFormat(0, 0);

        this.affine = new Affine();
        this.affine.appendScale(400 / 10f, 400 / 10f);

        Pane spacer = new Pane();
        spacer.setMinSize(0,0);
        spacer.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        VBox.setVgrow(spacer, Priority.ALWAYS);

        // Handlers
        this.canvas.setOnMousePressed(this::handleDraw);
        this.canvas.setOnMouseDragged(this::handleDraw);
        this.canvas.setOnMouseMoved(this::handleHover);
        this.setOnKeyPressed(this::keyHandler);

        this.getChildren().addAll(toolbar, this.canvas, spacer, infobar);
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

    private void handleHover(MouseEvent event) {
        Point2D point = getMouseCoordinates(event);
        this.infobar.setCursorPositionFormat((int) point.getX(), (int) point.getY());
    }

    private void handleDraw(MouseEvent event) {
        Point2D point = getMouseCoordinates(event);
        if (this.drawMode) {
            this.simulation.setAlive((int) point.getX(), (int) point.getY());
        } else {
            this.simulation.setDead((int) point.getX(), (int) point.getY());
        }
        this.infobar.setCursorPositionFormat((int) point.getX(), (int) point.getY());
        draw();
    }

    private Point2D getMouseCoordinates(MouseEvent event) {
        return new Point2D(event.getX() / 40f, event.getY() / 40f);
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
