package com.amaro.bakingapp.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Step implements Parcelable {
    private int id;
    private String shortDescription;
    private String videoURL;
    private String thumbnailUTL;
    private String description;

    public Step() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }

    public String getThumbnailUTL() {
        return thumbnailUTL;
    }

    public void setThumbnailUTL(String thumbnailUTL) {
        this.thumbnailUTL = thumbnailUTL;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {

        @Override
        public Object createFromParcel(Parcel source) {
            return new Step(source);
        }

        @Override
        public Step[] newArray(int size) {
            return new Step[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(shortDescription);
        dest.writeString(videoURL);
        dest.writeString(thumbnailUTL);
        dest.writeString(description);
    }

    private Step(Parcel in) {
        id = in.readInt();
        shortDescription = in.readString();
        videoURL = in.readString();
        thumbnailUTL = in.readString();
        description = in.readString();
    }
}
