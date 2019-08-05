package com.example.concretetest.model.repositories;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RepositoryResponse implements Parcelable {

    @SerializedName("totalCount")
    private int totalCount;
    @SerializedName("incompleteResults")
    private boolean incompleteResults;
    @SerializedName("items")
    private List<Item> items;

    public RepositoryResponse() {
    }

    public RepositoryResponse(int totalCount, boolean incompleteResults, List<Item> items) {
        this.totalCount = totalCount;
        this.incompleteResults = incompleteResults;
        this.items = items;
    }

    protected RepositoryResponse(Parcel in) {
        totalCount = in.readInt();
        incompleteResults = in.readByte() != 0;
        items = in.createTypedArrayList(Item.CREATOR);
    }

    public static final Creator<RepositoryResponse> CREATOR = new Creator<RepositoryResponse>() {
        @Override
        public RepositoryResponse createFromParcel(Parcel in) {
            return new RepositoryResponse(in);
        }

        @Override
        public RepositoryResponse[] newArray(int size) {
            return new RepositoryResponse[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(totalCount);
        dest.writeByte((byte) (incompleteResults ? 1 : 0));
        dest.writeTypedList(items);
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public boolean isIncompleteResults() {
        return incompleteResults;
    }

    public void setIncompleteResults(boolean incompleteResults) {
        this.incompleteResults = incompleteResults;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}
