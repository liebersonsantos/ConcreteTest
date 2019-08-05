package com.example.concretetest.presenter;

import android.util.Log;

import com.example.concretetest.contract.PullRequestContract;
import com.example.concretetest.model.pullRequests.PullRequestModel;
import com.example.concretetest.model.pullRequests.PullResquestResponse;
import com.example.concretetest.model.service.ApiService;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class PullRequestPresenter implements PullRequestContract.Presenter {

    private PullRequestContract.Model model;
    private PullRequestContract.View view;
    private CompositeDisposable disposable = new CompositeDisposable();

    @Override
    public void attachView(PullRequestContract.View view) {
        this.view = view;
        this.model = new PullRequestModel(this, view);
    }

    @Override
    public void detachView() {
        this.view = null;
        this.model = null;
    }

    @Override
    public void getPullRequest(String owner, String repositoryName) {
        disposable.add(ApiService.getInstancePull(view).getPullRequestByRepositoryName(owner, repositoryName)
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
                    Log.i("TAG", "getPullRequest: " + throwable.getMessage());
                })
                .subscribe(this::calculateOpenAndClosed, throwable -> {
                    view.showRepositoriesError(throwable);
                    Log.i("TAG", "getPullRequest: " + throwable.getMessage());
                })
        );
    }

    private void calculateOpenAndClosed(List<PullResquestResponse> responseList) {
        int openCount=calculateOpenPullRequest(responseList);
        String open= String.valueOf(openCount);
        String closed= String.valueOf((responseList.size()-openCount));
        view.updateOpenClosed(open,closed);
        view.showRepositories(responseList);

    }

    private int calculateOpenPullRequest(List<PullResquestResponse> responseList){
        int count=0;
        for(PullResquestResponse response : responseList){
            if(response.getState().equals("open")){
                count++;
            }
        }
        return count;
    }

}
