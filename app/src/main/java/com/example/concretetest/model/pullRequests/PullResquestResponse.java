package com.example.concretetest.model.pullRequests;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class PullResquestResponse implements Parcelable {

    @SerializedName("body")
    private String body;
    @SerializedName("state")
    private String state;
    @SerializedName("id")
    private String id;
    @SerializedName("title")
    private String title;
    @SerializedName("created_at")
    private String created_at;
    @SerializedName("html_url")
    private String html_url;
    @SerializedName("user")
    private User user;

    public PullResquestResponse() {
    }

    public PullResquestResponse(String body, String state, String id, String title, String created_at, String html_url, User user) {
        this.body = body;
        this.state = state;
        this.id = id;
        this.title = title;
        this.created_at = created_at;
        this.html_url = html_url;
        this.user = user;
    }

    protected PullResquestResponse(Parcel in) {
        body = in.readString();
        state = in.readString();
        id = in.readString();
        title = in.readString();
        created_at = in.readString();
        html_url = in.readString();
    }

    public static final Creator<PullResquestResponse> CREATOR = new Creator<PullResquestResponse>() {
        @Override
        public PullResquestResponse createFromParcel(Parcel in) {
            return new PullResquestResponse(in);
        }

        @Override
        public PullResquestResponse[] newArray(int size) {
            return new PullResquestResponse[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(body);
        dest.writeString(state);
        dest.writeString(id);
        dest.writeString(title);
        dest.writeString(created_at);
        dest.writeString(html_url);
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getHtml_url() {
        return html_url;
    }

    public void setHtml_url(String html_url) {
        this.html_url = html_url;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
