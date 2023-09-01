package com.dev.taormina.gol.viewModel;

import com.dev.taormina.gol.model.Board;
import com.dev.taormina.gol.model.CellState;

import java.util.LinkedList;
import java.util.List;

public class EditorViewModel {
    private CellState drawMode = CellState.ALIVE;
    private final List<SimpleChangeListener<CellState>> drawModeListeners;
    private final BoardViewModel boardViewModel;
    private final Board editorBoard;
    private boolean drawingEnabled = true;

    public EditorViewModel(BoardViewModel boardViewModel, Board initialBoard) {
        this.boardViewModel = boardViewModel;
        this.editorBoard = initialBoard;
        this.drawModeListeners = new LinkedList<>();
    }

    public void onAppStateChanged(ApplicationState state) {
        if (state == ApplicationState.EDITING) {
            drawingEnabled = true;
            boardViewModel.setBoard(this.editorBoard);
        } else {
            drawingEnabled = false;
        }
    }

    public void setDrawMode(CellState drawMode) {
        this.drawMode = drawMode;
        notifyDrawModeListeners();
    }

    public void listenToDrawMode(SimpleChangeListener<CellState> drawModeListener) {
        this.drawModeListeners.add(drawModeListener);
    }

    private void notifyDrawModeListeners() {
        for (SimpleChangeListener<CellState> drawModeListener: this.drawModeListeners) {
            drawModeListener.valueChanged(this.drawMode);
        }
    }

    public void coordinatesClicked(int x, int y) {
        if (drawingEnabled) {
            this.editorBoard.setState(x, y, this.drawMode);
            this.boardViewModel.setBoard(this.editorBoard);
        }
    }
}
