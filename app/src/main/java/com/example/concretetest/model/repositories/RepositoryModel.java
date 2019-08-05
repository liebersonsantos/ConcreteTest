package com.example.concretetest.model.repositories;

import com.example.concretetest.contract.RepositoryContract;
import com.example.concretetest.model.service.ApiService;

import io.reactivex.Single;

public class RepositoryModel implements RepositoryContract.Model {

    private RepositoryContract.Presenter presenter;
    private RepositoryContract.View view;

    public RepositoryModel(RepositoryContract.Presenter presenter, RepositoryContract.View view){
        this.presenter = presenter;
        this.view = view;
    }

    @Override
    public Single<RepositoryResponse> getRepository(String language, String stars, int page) {
        return ApiService.getInstance(view).getRepositories(language, stars, page);
    }
}