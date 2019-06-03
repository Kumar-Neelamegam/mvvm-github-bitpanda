package com.example.test.bitpanda.service.repository;

import com.example.test.bitpanda.service.model.Contributor;
import com.example.test.bitpanda.service.model.Repository;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GitHubService {

    @GET("repositories")
    Call<List<Repository>> getPublicRepository();

    @GET("repos/{login}/{name}")
    Call<Repository> getPublicRepositoryWithName(@Path("login") String contributor_login, @Path("name") String repo_name);

    @GET("repos/{login}/{name}/contributors")
    Call<List<Contributor>> getContributors(@Path("login") String contributor_login, @Path("name") String repo_name);

}
