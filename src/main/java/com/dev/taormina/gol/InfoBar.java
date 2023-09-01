package com.dev.taormina.gol;

import com.dev.taormina.gol.viewModel.EditorViewModel;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import com.dev.taormina.gol.model.CellState;

public class InfoBar extends HBox {
    private static final String DRAW = "Draw";
    private static final String ERASE = "Erase";
    private static final String drawModeFormat = "Draw Mode: %s";
    private static final String cursorPositionFormat = "Cursor: (%d, %d)";
    private final Label cursor;
    private final Label editingTool;

    public InfoBar(EditorViewModel editorViewModel) {
        editorViewModel.listenToDrawMode(this::setDrawModeLabel);
        this.cursor = new Label();
        this.editingTool = new Label();

        Pane spacer = new Pane();
        spacer.setMinSize(0,0);
        spacer.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        HBox.setHgrow(spacer, Priority.ALWAYS);
        this.editingTool.setText(String.format(drawModeFormat, DRAW));
        this.cursor.setText(String.format(cursorPositionFormat, 0, 0));

        this.getChildren().addAll(this.editingTool, spacer, this.cursor);
    }

    public void setDrawModeLabel(CellState mode) {
        if (mode == CellState.ALIVE) {
            this.editingTool.setText(String.format(drawModeFormat, DRAW));
        } else {
            this.editingTool.setText(String.format(drawModeFormat, ERASE));
        }
    }

    public void setCursorPositionLabel(int x, int y) {
        this.cursor.setText(String.format(cursorPositionFormat, x, y));
    }
}