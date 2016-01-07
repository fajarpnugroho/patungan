package com.fslab.android.patungan;

import com.fslab.android.patungan.services.ServiceModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class, ServiceModule.class})
public interface ApplicationComponent extends ApplicationGraph {
}
