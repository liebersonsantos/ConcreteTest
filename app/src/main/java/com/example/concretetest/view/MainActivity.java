package com.example.concretetest.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.concretetest.R;
import com.example.concretetest.contract.RepositoryContract;
import com.example.concretetest.listeners.EndlessRecyclerViewListener;
import com.example.concretetest.model.repositories.Item;
import com.example.concretetest.presenter.RepositoryPresenter;
import com.example.concretetest.view.adapter.RepositoryAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements RepositoryContract.View {

    private RepositoryContract.Presenter presenter;
    private List<Item> items = new ArrayList<>();
    private RepositoryAdapter adapter;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setToolbar();

        presenter = new RepositoryPresenter();
        presenter.attachView(this);

        setRecyclerView();
    }

    private void setToolbar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(R.string.toolbar_text);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.onResume();
    }

    private void setRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        adapter = new RepositoryAdapter(items);

        recyclerView.addOnScrollListener(new EndlessRecyclerViewListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                presenter.getRepository(page);
            }
        });

        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @Override
    public void showProgress(boolean show) {
        int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

        recyclerView.setVisibility((show) ? View.GONE : View.VISIBLE);
        recyclerView.animate()
                .setDuration(shortAnimTime)
                .alpha((show) ? 0 : 1)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        recyclerView.setVisibility((show) ? View.GONE : View.VISIBLE);
                    }
                });

        progressBar.setVisibility((show) ? View.VISIBLE : View.GONE);
        progressBar.animate()
                .setDuration(shortAnimTime)
                .alpha((show) ? 1 : 0)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        progressBar.setVisibility((show) ? View.VISIBLE : View.GONE);
                    }
                });
    }

    @Override
    public void showRepositories(List<Item> response) {
        if (!response.isEmpty()){
            adapter.update(response);
        }
    }

    @Override
    public void showRepositoriesError(Throwable t) {
        Toast.makeText(this, "Erro ao carregar os dados", Toast.LENGTH_SHORT).show();
    }

    @Override
    public Context getContext() {
        return this;
    }

}