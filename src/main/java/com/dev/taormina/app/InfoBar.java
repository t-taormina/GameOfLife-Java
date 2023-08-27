package com.dev.taormina.app;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;

public class InfoBar extends HBox {
    private static final String DRAW = "Draw";
    private static final String ERASE = "Erase";
    private final Label cursor;
    private final Label editingTool;
    public InfoBar() {
        this.cursor = new Label();
        this.editingTool = new Label();

        Pane spacer = new Pane();
        spacer.setMinSize(0,0);
        spacer.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        HBox.setHgrow(spacer, Priority.ALWAYS);

        this.getChildren().addAll(this.editingTool, spacer, this.cursor);
    }

    public void setDrawModeFormat(boolean mode) {
        String drawModeFormat = "Draw Mode: %s";
        if (mode) {
            this.editingTool.setText(String.format(drawModeFormat, DRAW));
        } else {
            this.editingTool.setText(String.format(drawModeFormat, ERASE));
        }
    }

    public void setCursorPositionFormat(int x, int y) {
        String cursorPositionFormat = "Cursor: (%d, %d)";
        this.cursor.setText(String.format(cursorPositionFormat, x, y));
    }
}