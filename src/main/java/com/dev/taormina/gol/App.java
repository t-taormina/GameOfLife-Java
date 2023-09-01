package com.dev.taormina.gol;

import com.dev.taormina.gol.model.Board;
import com.dev.taormina.gol.model.BoundedBoard;
import com.dev.taormina.gol.viewModel.ApplicationViewModel;
import com.dev.taormina.gol.viewModel.BoardViewModel;
import com.dev.taormina.gol.viewModel.EditorViewModel;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage stage) {
        ApplicationViewModel applicationViewModel = new ApplicationViewModel();
        BoardViewModel boardViewModel = new BoardViewModel();
        Board initialBoard = new BoundedBoard(10, 10);
        EditorViewModel editorViewModel = new EditorViewModel(boardViewModel, initialBoard);

        applicationViewModel.listenToAppState(editorViewModel::onAppStateChanged);

        MainView mainView = new MainView(applicationViewModel, boardViewModel, editorViewModel);
        Scene scene = new Scene(mainView, 640, 480);
        stage.setScene(scene);
        stage.show();
        boardViewModel.setBoard(initialBoard);
    }


    public static void main(String[] args) {
        launch();
    }
}