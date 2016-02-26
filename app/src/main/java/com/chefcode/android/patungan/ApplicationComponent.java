package com.chefcode.android.patungan;

import com.chefcode.android.patungan.services.PushnotifServiceModule;
import com.chefcode.android.patungan.services.ServiceModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class, ServiceModule.class, PushnotifServiceModule.class})
public interface ApplicationComponent extends ApplicationGraph {
}
