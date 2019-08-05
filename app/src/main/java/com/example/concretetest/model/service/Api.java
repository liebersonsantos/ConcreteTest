package com.example.concretetest.model.service;

import com.example.concretetest.BuildConfig;
import com.example.concretetest.model.pullRequests.PullResquestResponse;
import com.example.concretetest.model.repositories.RepositoryResponse;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Api {

    @GET("/search/repositories")
    @Headers({"Accept:application/vnd.github.v3+json",
            "Authorization:" + BuildConfig.TOKEN_1 + BuildConfig.TOKEN_2})
    Single<RepositoryResponse> getRepositories(@Query("q") String language,
                                               @Query("sort") String stars,
                                               @Query("page") int page);

    @GET("repos/{owner}/{repository_name}/pulls")
    Single<List<PullResquestResponse>> getPullRequestByRepositoryName(@Path("owner") String owner,
                                                                      @Path("repository_name") String repositoryName);
}
