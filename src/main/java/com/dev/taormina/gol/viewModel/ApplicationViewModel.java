package com.dev.taormina.gol.viewModel;

import java.util.LinkedList;
import java.util.List;

public class ApplicationViewModel {
    private ApplicationState applicationState;
    private final List<SimpleChangeListener> appStateListeners;

    public ApplicationViewModel() {
        this.applicationState = ApplicationState.EDITING;
        this.appStateListeners = new LinkedList<>();
    }

    public void setApplicationState(ApplicationState state) {
        if (this.applicationState != state) {
            this.applicationState = state;
            notifyAppStateListeners();
        }
    }

    private void notifyAppStateListeners() {
        for (SimpleChangeListener appStateListener : appStateListeners) {
            appStateListener.valueChanged();
        }
    }

    public ApplicationState getApplicationState() {
        return applicationState;
    }

    public void listenToAppState(SimpleChangeListener listener) {
        this.appStateListeners.add(listener);
    }
}
