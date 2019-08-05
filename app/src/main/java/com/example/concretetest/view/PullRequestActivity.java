package com.example.concretetest.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.concretetest.R;
import com.example.concretetest.contract.PullRequestContract;
import com.example.concretetest.model.pullRequests.PullResquestResponse;
import com.example.concretetest.model.repositories.Item;
import com.example.concretetest.presenter.PullRequestPresenter;
import com.example.concretetest.view.adapter.PullResquestAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PullRequestActivity extends AppCompatActivity implements PullRequestContract.View{

    private PullRequestContract.Presenter presenter;
    private List<PullResquestResponse> responseList = new ArrayList<>();
    private PullResquestAdapter adapter;

    private String owner;
    private String repositoryName;
    private Item item;

    @BindView(R.id.text_opened)
    TextView openedText;
    @BindView(R.id.text_closed)
    TextView closedText;
    @BindView(R.id.recycler_view_pull)
    RecyclerView recyclerView;
    @BindView(R.id.progress_content_pull)
    ProgressBar progressBar;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pull_request);
        ButterKnife.bind(this);

        presenter = new PullRequestPresenter();
        presenter.attachView(this);

        unBundle();

        presenter.getPullRequest(owner, repositoryName);
        settingToolbar();

        setRecyclerView();

    }

    private void setRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        adapter = new PullResquestAdapter(responseList);
        recyclerView.setAdapter(adapter);
    }

    private void settingToolbar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(repositoryName);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void unBundle() {
        owner = getIntent().getStringExtra(Navigation.OWNER);
        repositoryName = getIntent().getStringExtra(Navigation.REPOSITORY_NAME);
        Log.i("TAG", "unBundle: -------------------------------> " + owner + "------" + repositoryName);
    }

    @Override
    public Context getContext() {
        return this;
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
    public void showRepositories(List<PullResquestResponse> responseList) {
        if (!responseList.isEmpty()){
            adapter.update(responseList);
        }
    }

    @Override
    public void showRepositoriesError(Throwable t) {
        Toast.makeText(this, "Erro ao carregar os dados", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void updateOpenClosed(String open, String closed) {
        openedText.setText(getString(R.string.open, open));
        closedText.setText(getString(R.string.closed, closed));
    }
}
