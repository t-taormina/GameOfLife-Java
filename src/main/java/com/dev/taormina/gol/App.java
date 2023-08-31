package com.dev.taormina.gol;

import com.dev.taormina.gol.viewModel.ApplicationViewModel;
import com.dev.taormina.gol.viewModel.SimpleChangeListener;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        ApplicationViewModel applicationViewModel = new ApplicationViewModel();

        MainView mainView = new MainView();
        Scene scene = new Scene(mainView, 640, 480);
        stage.setScene(scene);
        stage.show();
        mainView.draw();
    }


    public static void main(String[] args) {
        launch();
    }
}