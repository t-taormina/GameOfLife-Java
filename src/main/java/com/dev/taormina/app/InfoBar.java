package com.dev.taormina.app;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;

public class InfoBar extends HBox {
    private static String drawModeFormat = "Draw Mode: %s";
    private static String cursorPosFormat = "Cursor: (%d, %d)";
    private static final String DRAW = "Draw";
    private static final String ERASE = "Erase";
    private Label cursor;
    private Label editingTool;
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
        if (mode) {
            this.editingTool.setText(String.format(drawModeFormat, DRAW));
        } else {
            this.editingTool.setText(String.format(drawModeFormat, ERASE));
        }
    }

    public void setCursorPosFormat(int x, int y) {
        this.cursor.setText(String.format(cursorPosFormat, x, y));
    }
}
