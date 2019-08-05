package com.example.concretetest.contract;

import android.content.Context;

import com.example.concretetest.model.pullRequests.PullResquestResponse;

import java.util.List;

import io.reactivex.Single;


public class PullRequestContract {

    public interface Model{
        Single<List<PullResquestResponse>> getPullRequest(String owner, String repositoryName);

    }

    public interface View{
        Context getContext();
        void showProgress(boolean b);
        void showRepositories(List<PullResquestResponse> responseList);
        void showRepositoriesError(Throwable t);
        void updateOpenClosed(String open, String closed);

    }

    public interface Presenter{
        void getPullRequest(String owner, String repository_name);
        void attachView(PullRequestContract.View view);
        void detachView();

    }
}
