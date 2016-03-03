package starace.learn.com.myapplication;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.LinkedList;

/**
 * Created by mstarace on 3/2/16.
 */
public class Item implements Parcelable {

    private int backgroundColorResource;
    private String text;

    public Item() {

        backgroundColorResource = android.R.color.background_light;

    }

    public Item(String text) {

        this.text = text;

    }

    public int getBackgroundColorResource() {

        return backgroundColorResource;

    }

    public void setBackgroundColorResource(int backgroundColorResource) {

        this.backgroundColorResource = backgroundColorResource;

    }


    public void changeColor(){
        int curColor;
        curColor = getBackgroundColorResource();
        if (curColor == android.R.color.background_light) {
            setBackgroundColorResource(android.R.color.darker_gray);
        } else {
            setBackgroundColorResource(android.R.color.background_light);
        }

    }



    public LinkedList<String> getText() {
        LinkedList<String> curString = new LinkedList<>();
        curString.add(text);

        return curString;
    }

    public void setText(String text) {

        this.text = text;
    }

    @Override
    public String toString() {

        return text;

    }

    public static final Parcelable.Creator<Item> CREATOR = new Parcelable.Creator<Item>() {

        public Item createFromParcel(Parcel source) {

            Item mItem = new Item();

            mItem.backgroundColorResource = source.readInt();

            mItem.text = source.readString();

            return mItem;

        }

        public Item[] newArray(int size) {

            return new Item[size];

        }
    };



    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int flags) {

        parcel.writeInt(backgroundColorResource);

        parcel.writeString(text);

    }


}
