package com.example.concretetest.view;

import android.app.Activity;
import android.content.Intent;

import com.example.concretetest.model.repositories.Item;

public class Navigation {

    public static String OWNER = "OWNER";
    public static String REPOSITORY_NAME = "REPOSITORY_NAME";

    public Intent goToPullRequestActivity(Activity activity, Item item){
        Intent intent = new Intent(activity, PullRequestActivity.class);
        intent.putExtra(OWNER, item.getOwner().getLogin());
        intent.putExtra(REPOSITORY_NAME, item.getName());
        return intent;
    }
}