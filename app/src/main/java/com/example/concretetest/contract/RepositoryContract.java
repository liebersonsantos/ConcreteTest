package com.example.concretetest.contract;

import android.content.Context;

import com.example.concretetest.model.repositories.Item;
import com.example.concretetest.model.repositories.RepositoryResponse;

import java.util.List;

import io.reactivex.Single;

public class RepositoryContract {

    public interface Model{
        Single<RepositoryResponse> getRepository(String language, String stars, int page);

    }

    public interface View{
        void showProgress(boolean b);
        void showRepositories(List<Item> response);
        void showRepositoriesError(Throwable t);
        Context getContext();

    }

    public interface Presenter{
        void attachView(RepositoryContract.View view);
        void detachView();
        void getRepository(int page);
        void onResume();
        void showRepositories(List<Item> response);

        void showRepositoryError(Throwable t);
    }
}
