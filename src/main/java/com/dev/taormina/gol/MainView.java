package com.dev.taormina.gol;

import com.dev.taormina.gol.model.Board;
import com.dev.taormina.gol.model.BoundedBoard;
import com.dev.taormina.gol.model.CellState;
import com.dev.taormina.gol.model.StandardRule;
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
    public static final int EDITING = 0;
    public static final int SIMULATING = 1;

    private Canvas canvas;
    private Board initialBoard;
    private StandardRule rule;
    private Simulation simulation;
    private Affine affine;
    private CellState drawMode = CellState.ALIVE;
    private InfoBar infobar;
    private int applicationState = EDITING;

    public MainView() {
        this.canvas = new Canvas(400, 400);
        this.initialBoard = new BoundedBoard(10, 10);
        this.rule = new StandardRule();
        this.simulation = new Simulation(initialBoard, rule);

//        this.initSimulation = new Simulation(10, 10);
//        this.simulation = Simulation.copy(this.initSimulation);

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
            this.drawMode = CellState.DEAD;
        }
        if (event.getCode() == KeyCode.D) {
            this.drawMode = CellState.ALIVE;
        }
        this.infobar.setDrawModeFormat(this.drawMode);
    }

    private void handleHover(MouseEvent event) {
        Point2D point = getMouseCoordinates(event);
        this.infobar.setCursorPositionFormat((int) point.getX(), (int) point.getY());
    }

    private void handleDraw(MouseEvent event) {
        Point2D point = getMouseCoordinates(event);

        if (this.applicationState == EDITING) {
            if (this.drawMode == CellState.ALIVE) {
                this.initialBoard.setState((int) point.getX(), (int) point.getY(), CellState.ALIVE);
            } else {
                this.initialBoard.setState((int) point.getX(), (int) point.getY(), CellState.DEAD);
            }
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
        if (this.applicationState == EDITING) {
            drawBoard(this.initialBoard);
        } else {
            drawBoard(this.simulation.getBoard());
        }

        // Draw Grid lines
        g.setStroke(Color.GRAY);
        g.setLineWidth(0.05f);
        for (int x = 0; x <= this.initialBoard.getWidth(); x++) {
            g.strokeLine(x, 0, x, 10);
            for (int y = 0; y <= this.initialBoard.getHeight(); y++) {
                g.strokeLine(0, y, 10, y);
            }
        }
    } // draw

    private void drawBoard(Board board) {
        GraphicsContext g = this.canvas.getGraphicsContext2D();
        // Fill cell iff cell is alive
        g.setFill(Color.BLACK);
        for (int x = 0; x < board.getWidth(); x++) {
            for (int y = 0; y < board.getHeight(); y++) {
                if (board.getState(x, y) == CellState.ALIVE) {
                    g.fillRect(x, y, 1, 1);
                }
            }
        }
    }

    public void setDrawMode(CellState mode) {
        this.drawMode = mode;
        this.infobar.setDrawModeFormat(mode);
    }


    public void setApplicationState(int applicationState) {
        if (this.applicationState != applicationState) {
            if (applicationState == SIMULATING) {
                this.simulation = new Simulation(this.initialBoard, this.rule);
            }
            this.applicationState = applicationState;
        }
    }

    public int getApplicationState() {
        return applicationState;
    }

    public Simulation getSimulation() {
        return simulation;
    }
} // MainView
