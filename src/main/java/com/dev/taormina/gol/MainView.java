package com.dev.taormina.gol;

import com.dev.taormina.gol.model.Board;
import com.dev.taormina.gol.model.CellState;
import com.dev.taormina.gol.viewModel.ApplicationViewModel;
import com.dev.taormina.gol.viewModel.BoardViewModel;
import com.dev.taormina.gol.viewModel.EditorViewModel;
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
    private final Affine affine;
    private final InfoBar infobar;
    private final EditorViewModel editorViewModel;


    public MainView(ApplicationViewModel applicationViewModel, BoardViewModel boardViewModel, EditorViewModel editorViewModel) {
        this.editorViewModel = editorViewModel;
        boardViewModel.listenToBoard(this::onBoardChanged);

        this.canvas = new Canvas(400, 400);

        Toolbar toolbar = new Toolbar(applicationViewModel, boardViewModel, editorViewModel);

        this.infobar = new InfoBar(editorViewModel);

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

    private void keyHandler(KeyEvent event) {
        CellState format = CellState.DEAD;
        if (event.getCode() == KeyCode.E ) {
            this.editorViewModel.setDrawMode(CellState.DEAD);
        }
        if (event.getCode() == KeyCode.D) {
            this.editorViewModel.setDrawMode(CellState.ALIVE);
            format = CellState.ALIVE;
        }
        this.infobar.setDrawModeLabel(format);
    }

    private void handleHover(MouseEvent event) {
        Point2D point = getMouseCoordinates(event);
        this.infobar.setCursorPositionLabel((int) point.getX(), (int) point.getY());
    }

    private void handleDraw(MouseEvent event) {
        Point2D point = getMouseCoordinates(event);
        int x = (int) point.getX();
        int y = (int) point.getY();

        this.infobar.setCursorPositionLabel((int) point.getX(), (int) point.getY());
        this.editorViewModel.coordinatesClicked(x, y);
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
} // MainView
