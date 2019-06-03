package com.example.test.bitpanda.dagger;

import com.example.test.bitpanda.view.ui.RepositoryDetailsFragment;
import com.example.test.bitpanda.view.ui.RepositoryListFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FragmentBuildersModule {

    @ContributesAndroidInjector
    abstract RepositoryListFragment contributeRepositoryFragment();

    @ContributesAndroidInjector
    abstract RepositoryDetailsFragment contributeRepositoryDetailsFragment();

}
