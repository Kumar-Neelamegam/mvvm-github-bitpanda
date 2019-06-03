package com.example.test.bitpanda.viewmodel;

import android.app.Application;
import android.util.Log;

import com.example.test.bitpanda.service.model.Repository;
import com.example.test.bitpanda.service.repository.ServiceRepository;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

public class RepositoryDetailsViewModel extends AndroidViewModel {
    private static final String TAG = RepositoryDetailsViewModel.class.getName();
    private final LiveData<Repository> repositoryObservable;
    private final MutableLiveData<String> repositoryID;
    public ObservableField<Repository> repository = new ObservableField<>();

    private static final MutableLiveData ABSENT = new MutableLiveData();
    {
        //noinspection unchecked
        ABSENT.setValue(null);
    }

    @Inject
    public RepositoryDetailsViewModel(@NonNull ServiceRepository serviceRepository, @NonNull Application application) {
        super(application);
        this.repositoryID = new MutableLiveData<>();
        repositoryObservable = Transformations.switchMap(repositoryID, input -> {
            if (input.isEmpty()) {
                Log.e(TAG, "RepositoryViewModel repositoryId is absent!!!");
                return ABSENT;
            }

            Log.e(TAG,"RepositoryViewModel ID is " + repositoryID.getValue());

            return serviceRepository.getPublicRepositoryWithName(repository.get().owner.login, repository.get().name);
        });
    }


    public LiveData<Repository> getObservableRepository() {
        return repositoryObservable;
    }

    public void setRepository(Repository repository) {
        this.repository.set(repository);
    }

    public void setRepositoryID(String projectID) {
        this.repositoryID.setValue(projectID);
    }

}