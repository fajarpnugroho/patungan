package com.fslab.android.patungan;

import android.app.Application;

public enum  Injector {
    INSTANCE;

    Injector() {}

    private ApplicationGraph applicationGraph;

    void initializeApplicationGraph(Application app) {
        applicationGraph = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(app)).build();
    }

    public ApplicationGraph getApplicationGraph() { return applicationGraph; }
}
