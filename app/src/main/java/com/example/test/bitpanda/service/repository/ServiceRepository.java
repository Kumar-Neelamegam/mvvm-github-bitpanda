package com.example.test.bitpanda.service.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.test.bitpanda.service.model.Contributor;
import com.example.test.bitpanda.service.model.Repository;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Singleton
public class ServiceRepository {
    private GitHubService gitHubService;

    @Inject
    public ServiceRepository(GitHubService gitHubService) {
        this.gitHubService = gitHubService;
    }


    public LiveData<List<Repository>> getPublicRepository() {
        final MutableLiveData<List<Repository>> data = new MutableLiveData<>();

        gitHubService.getPublicRepository().enqueue(new Callback<List<Repository>>() {
            @Override
            public void onResponse(Call<List<Repository>> call, Response<List<Repository>> response) {
                simulateDelay();
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<Repository>> call, Throwable t) {
                data.setValue(null);
            }
        });

        return data;
    }

    public LiveData<Repository> getPublicRepositoryWithName(String login, String name) {
        final MutableLiveData<Repository> data = new MutableLiveData<>();

        gitHubService.getPublicRepositoryWithName(login, name).enqueue(new Callback<Repository>() {
            @Override
            public void onResponse(Call<Repository> call, Response<Repository> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<Repository> call, Throwable t) {
                data.setValue(null);
            }
        });

        return data;
    }

    public LiveData<List<Contributor>> getRepositortyContributors(String cordinator_login, String repo_name) {
        final MutableLiveData<List<Contributor>> data = new MutableLiveData<>();

        gitHubService.getContributors(cordinator_login, repo_name).enqueue(new Callback<List<Contributor>>() {
            @Override
            public void onResponse(Call<List<Contributor>> call, Response<List<Contributor>> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<Contributor>> call, Throwable t) {
                data.setValue(null);
            }
        });

        return data;
    }


    private void simulateDelay() {
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
