package com.dev.taormina.gol;

import com.dev.taormina.gol.model.Board;
import com.dev.taormina.gol.model.BoundedBoard;
import com.dev.taormina.gol.model.CellState;
import com.dev.taormina.gol.model.StandardRule;
import com.dev.taormina.gol.viewModel.ApplicationState;
import com.dev.taormina.gol.viewModel.ApplicationViewModel;
import com.dev.taormina.gol.viewModel.BoardViewModel;
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

    private Canvas canvas;
    private Board initialBoard;
    private StandardRule rule;
    private Affine affine;
    private CellState drawMode = CellState.ALIVE;
    private InfoBar infobar;
    private ApplicationViewModel applicationViewModel;
    private BoardViewModel boardViewModel;
    private boolean isDrawingEnabled = true;


    public MainView(ApplicationViewModel applicationViewModel, BoardViewModel boardViewModel, Board initialBoard) {
        this.applicationViewModel = applicationViewModel;
        this.boardViewModel = boardViewModel;
        
        this.applicationViewModel.listenToAppState(this::onApplicationStateChanged);
        this.boardViewModel.listenToBoard(this::onBoardChanged);

        this.initialBoard = initialBoard;
        this.canvas = new Canvas(400, 400);
        this.rule = new StandardRule();

        Toolbar toolbar = new Toolbar(this, this.applicationViewModel, this.boardViewModel);

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

    private void onBoardChanged(Board board) {
        draw(board);
    }

    private void onApplicationStateChanged(ApplicationState applicationState) {
        if (applicationState == ApplicationState.EDITING) {
            this.isDrawingEnabled = true;
            this.boardViewModel.setBoard(this.initialBoard);
        } else if (applicationState == ApplicationState.SIMULATING) {
            this.isDrawingEnabled = false;
        } else {
            throw new IllegalArgumentException("Unsupported ApplicationState: " + applicationState.name());
        }
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

        if (isDrawingEnabled) {
            if (this.drawMode == CellState.ALIVE) {
                this.initialBoard.setState((int) point.getX(), (int) point.getY(), CellState.ALIVE);
            } else {
                this.initialBoard.setState((int) point.getX(), (int) point.getY(), CellState.DEAD);
            }
        }
       this.infobar.setCursorPositionFormat((int) point.getX(), (int) point.getY());
        this.boardViewModel.setBoard(this.initialBoard);

    }

    private Point2D getMouseCoordinates(MouseEvent event) {
        return new Point2D(event.getX() / 40f, event.getY() / 40f);
    }

    public void draw(Board board) {
        GraphicsContext g = this.canvas.getGraphicsContext2D();
        g.setTransform(this.affine);
        g.setFill(Color.LIGHTGRAY);

        // Fill background
        g.fillRect(0, 0, 400, 400);

        this.drawBoard(board);

        // Draw Grid lines
        g.setStroke(Color.GRAY);
        g.setLineWidth(0.05f);
        for (int x = 0; x <= board.getWidth(); x++) {
            g.strokeLine(x, 0, x, 10);
            for (int y = 0; y <= board.getHeight(); y++) {
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
} // MainView
