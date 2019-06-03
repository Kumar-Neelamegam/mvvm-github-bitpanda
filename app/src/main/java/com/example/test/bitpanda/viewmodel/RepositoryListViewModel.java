package com.example.test.bitpanda.viewmodel;

import android.app.Application;

import com.example.test.bitpanda.service.model.Repository;
import com.example.test.bitpanda.service.repository.ServiceRepository;

import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class RepositoryListViewModel extends AndroidViewModel {

    private final LiveData<List<Repository>> repositoryListObservable;

    @Inject
    public RepositoryListViewModel(@NonNull ServiceRepository serviceRepository, @NonNull Application application) {
        super(application);

        // If any transformation is needed, this can be simply done by Transformations class ...
        repositoryListObservable = serviceRepository.getPublicRepository();
    }

    /**
     * Expose the LiveData Projects query so the UI can observe it.
     */
    public LiveData<List<Repository>> getRepositoryListObservable() {
        return repositoryListObservable;
    }
}
