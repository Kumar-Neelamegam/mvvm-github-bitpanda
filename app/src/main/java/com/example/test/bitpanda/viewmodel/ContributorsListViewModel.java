package com.example.test.bitpanda.viewmodel;

import android.app.Application;
import android.util.Log;

import com.example.test.bitpanda.service.model.Contributor;
import com.example.test.bitpanda.service.repository.ServiceRepository;

import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

public class ContributorsListViewModel extends AndroidViewModel {

    private final LiveData<List<Contributor>> contributorsListObservable;
    private final MutableLiveData<String> contributorID;
    private final MutableLiveData<String> contributorRepoName;
    public ObservableField<Contributor> contributorObservableField = new ObservableField<>();


    private static final MutableLiveData ABSENT = new MutableLiveData();
    {
        //noinspection unchecked
        ABSENT.setValue(null);
    }
    @Inject
    public ContributorsListViewModel(@NonNull ServiceRepository serviceRepository, @NonNull Application application) {
        super(application);
        contributorID=new MutableLiveData<>();
        contributorRepoName=new MutableLiveData<>();
        // If any transformation is needed, this can be simply done by Transformations class ...
        contributorsListObservable = Transformations.switchMap(contributorID, input -> {
            if (input.isEmpty()) {
                return ABSENT;
            }
            Log.e("contributorID", contributorID.getValue());
            return serviceRepository.getRepositortyContributors(contributorID.getValue(), contributorRepoName.getValue());
        });
    }

    /**
     * Expose the LiveData Projects query so the UI can observe it.
     */
    public LiveData<List<Contributor>> getContributorsListObservable() {
        return contributorsListObservable;
    }



    public void setContributor(Contributor contributor) {
        this.contributorObservableField.set(contributor);
    }

    public void setContributorLoginId(String contributorID) {
        this.contributorID.setValue(contributorID);
    }

    public void setContributorRepoName(String contributorID) {
        this.contributorRepoName.setValue(contributorID);
    }

}
