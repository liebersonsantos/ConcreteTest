package com.example.concretetest.model.pullRequests;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class User implements Parcelable {

    @SerializedName("avatar_url")
    private String avatar_url;
    @SerializedName("gravatar_id")
    private String gravatar_id;
    @SerializedName("id")
    private String id;
    @SerializedName("login")
    private String login;

    public User() {
    }

    public User(String avatar_url, String gravatar_id, String id, String login) {
        this.avatar_url = avatar_url;
        this.gravatar_id = gravatar_id;
        this.id = id;
        this.login = login;
    }

    protected User(Parcel in) {
        avatar_url = in.readString();
        gravatar_id = in.readString();
        id = in.readString();
        login = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(avatar_url);
        dest.writeString(gravatar_id);
        dest.writeString(id);
        dest.writeString(login);
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    public String getGravatar_id() {
        return gravatar_id;
    }

    public void setGravatar_id(String gravatar_id) {
        this.gravatar_id = gravatar_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
