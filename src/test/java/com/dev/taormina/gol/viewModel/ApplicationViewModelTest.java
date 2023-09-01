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
        assertEquals(ApplicationState.SIMULATING, listener.updatedAppState);
    }

    @Test
    void setApplicationState_setSameApplicationState() {
        TestListener listener = new TestListener();
        applicationViewModel.listenToAppState(listener);
        applicationViewModel.setApplicationState(ApplicationState.EDITING);
        assertFalse(listener.appStateUpdated);
        assertNull(listener.updatedAppState);
    }
    private static class TestListener implements SimpleChangeListener<ApplicationState> {
        private boolean appStateUpdated = false;
        private ApplicationState updatedAppState = null;

        @Override
        public void valueChanged(ApplicationState state) {
            appStateUpdated = true;
            updatedAppState = state;
        }
    }
}