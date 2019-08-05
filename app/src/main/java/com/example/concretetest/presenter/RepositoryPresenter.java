package com.example.concretetest.presenter;

import android.util.Log;

import com.example.concretetest.contract.RepositoryContract;
import com.example.concretetest.model.repositories.Item;
import com.example.concretetest.model.repositories.RepositoryModel;
import com.example.concretetest.model.service.ApiService;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class RepositoryPresenter implements RepositoryContract.Presenter {

    private RepositoryContract.View view;
    private RepositoryContract.Model model;
    private CompositeDisposable disposable = new CompositeDisposable();
    private final String LANGUAGE = "Java";
    private final String ORDER = "stars";
    int page;

    @Override
    public void attachView(RepositoryContract.View view) {
        this.view = view;
        this.model = new RepositoryModel(this, view);
    }

    @Override
    public void detachView() {
        this.view = null;
        this.model = null;
    }

    @Override
    public void getRepository(int page) {
        disposable.add(ApiService.getInstance(view).getRepositories(LANGUAGE, ORDER, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable1 -> {
                    // colocar o loading para a tela
                })
                .doAfterTerminate(() -> {
                    // tirar o loading pra ma tela
                })
                .doOnError(throwable -> {
                    view.showRepositoriesError(throwable);
                    Log.i("TAG", "getRepositoryERROR: " + throwable.getMessage());
                })
                .subscribe(repositoryResponses -> {
                    view.showRepositories(repositoryResponses.getItems());
                    Log.i("TAG", "onCreate: " + repositoryResponses.getItems());

                }, throwable -> {
                    // mostrar a mensagem pro usuario, de error
                    view.showRepositoriesError(throwable);
                })

        );

    }

    @Override
    public void onResume() {
        page=1;
        getRepository(page);
    }

    @Override
    public void showRepositories(List<Item> response) {
        view.showProgress(false);
        view.showRepositories(response);
    }

    @Override
    public void showRepositoryError(Throwable t) {
        view.showProgress(false);
        view.showRepositoriesError(t);
    }

}
