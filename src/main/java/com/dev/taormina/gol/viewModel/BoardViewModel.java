package com.dev.taormina.gol.viewModel;

import com.dev.taormina.gol.model.Board;

import java.util.LinkedList;
import java.util.List;

public class BoardViewModel {

    private Board board;
    private final List<SimpleChangeListener<Board>> boardListeners;

    public BoardViewModel() {
        this.boardListeners = new LinkedList<>();
    }

    private void notifyBoardListeners() {
        for (SimpleChangeListener<Board> boardListener : boardListeners) {
            boardListener.valueChanged(this.board);
        }
    }
    public void listenToBoard(SimpleChangeListener<Board> listener) {
        this.boardListeners.add(listener);
    }

    public void setBoard(Board board) {
        this.board = board;
        notifyBoardListeners();
    }

    public Board getBoard() {
        return board;
    }

}
