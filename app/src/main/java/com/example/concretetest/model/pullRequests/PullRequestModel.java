package com.example.concretetest.model.pullRequests;

import com.example.concretetest.contract.PullRequestContract;
import com.example.concretetest.model.service.ApiService;

import java.util.List;

import io.reactivex.Single;

public class PullRequestModel implements PullRequestContract.Model {

    private PullRequestContract.Presenter presenter;
    private PullRequestContract.View view;

    public PullRequestModel(PullRequestContract.Presenter presenter, PullRequestContract.View view){
        this.presenter = presenter;
        this.view = view;
    }

    @Override
    public Single<List<PullResquestResponse>> getPullRequest(String owner, String repositoryName) {
        return ApiService.getInstancePull(view).getPullRequestByRepositoryName(owner, repositoryName);
    }
}
