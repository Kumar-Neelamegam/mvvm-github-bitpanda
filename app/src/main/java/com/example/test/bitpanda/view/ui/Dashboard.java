package com.example.test.bitpanda.view.ui;

import android.os.Bundle;

import com.example.test.bitpanda.R;
import com.example.test.bitpanda.service.model.Repository;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class Dashboard extends AppCompatActivity implements HasSupportFragmentInjector {


    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Add project list fragment if this is first creation
        if (savedInstanceState == null) {
            RepositoryListFragment fragment = new RepositoryListFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, fragment, "RepositoryListFragment").commit();
        }
    }

    /**
     * Shows the project detail fragment
     */
    public void show(Repository repository) {
        RepositoryDetailsFragment repositoryDetailsFragment = RepositoryDetailsFragment.forProject(repository);

        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack("repositorylist")
                .replace(R.id.fragment_container,
                        repositoryDetailsFragment, null).commit();
    }


    @Override
    public DispatchingAndroidInjector<Fragment> supportFragmentInjector() {
        return dispatchingAndroidInjector;
    }

/*
    @Override
    public void onBackPressed() {
        //Constants.ExitDialog(this);
    }*/
}


