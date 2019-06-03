package com.example.test.bitpanda.dagger;

import com.example.test.bitpanda.viewmodel.ContributorsListViewModel;
import com.example.test.bitpanda.viewmodel.RepositoryDetailsViewModel;
import com.example.test.bitpanda.viewmodel.RepositoryListViewModel;
import com.example.test.bitpanda.viewmodel.ViewModelFactory;

import dagger.Subcomponent;

/**
 * A sub component to create ViewModels. It is called by the
 * {@link ViewModelFactory}.
 */
@Subcomponent
public interface ViewModelSubComponent {
    @Subcomponent.Builder
    interface Builder {
        ViewModelSubComponent build();
    }

    RepositoryListViewModel repositoryListViewModel();

    RepositoryDetailsViewModel repositoryDetailsViewModel();

    ContributorsListViewModel contributorsListViewModel();
}
