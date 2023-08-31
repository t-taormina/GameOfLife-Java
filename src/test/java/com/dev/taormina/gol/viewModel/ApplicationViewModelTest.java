package com.dev.taormina.gol.viewModel;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ApplicationViewModelTest {


    private ApplicationViewModel applicationViewModel;

    @BeforeEach
    void setUp() {
        applicationViewModel = new ApplicationViewModel();
    }

    @Test
    void setApplicationState_setNewApplicationState() {
        TestListener listener = new TestListener();
        applicationViewModel.listenToAppState(listener);
        applicationViewModel.setApplicationState(ApplicationState.SIMULATING);
        assertTrue(listener.appStateUpdated);
    }

    @Test
    void setApplicationState_setSameApplicationState() {
        TestListener listener = new TestListener();
        applicationViewModel.listenToAppState(listener);
        applicationViewModel.setApplicationState(ApplicationState.EDITING);
        assertFalse(listener.appStateUpdated);
    }
    private static class TestListener implements SimpleChangeListener {
        public boolean appStateUpdated = false;
        @Override
        public void valueChanged() {
            appStateUpdated = true;
        }
    }
}