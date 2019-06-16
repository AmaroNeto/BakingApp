package com.amaro.bakingapp.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Ingredient implements Parcelable {

    private int id;
    private float quantity;
    private String measure;
    private String ingredient;

    public Ingredient() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {

        @Override
        public Object createFromParcel(Parcel source) {
            return new Ingredient(source);
        }

        @Override
        public Ingredient[] newArray(int size) {
            return new Ingredient[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeFloat(quantity);
        dest.writeString(measure);
        dest.writeString(ingredient);
    }

    private Ingredient(Parcel in) {
        id = in.readInt();
        quantity = in.readFloat();
        measure = in.readString();
        ingredient = in.readString();
    }
}
