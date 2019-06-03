package com.example.test.bitpanda.dagger;

import com.example.test.bitpanda.view.ui.Dashboard;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class MainActivityModule {

    @ContributesAndroidInjector(modules = FragmentBuildersModule.class)
    abstract Dashboard contributeDashboard();

}
