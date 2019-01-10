package msa.msaprojectfinal;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;

import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Calendar;

public class Post implements Parcelable {
    public String user_id;
    public String date_posted;
    public String user_name; //username
    public String title;
    public String description;
    public String category;
    public boolean available;
    public double payment;
    @Exclude public String key_id;

    public Post(){

    }

    public Post(String uid, String author, String title, String description, String category, double payment) {
        this.user_id = uid;
        this.user_name = author;
        this.title = title;
        this.description = description;
        this.category = category;
        this.date_posted = DateFormat.getDateTimeInstance().format(new Date());;
        this.available = true;
        this.payment = payment;
    }

    protected Post(Parcel in) {
        user_id = in.readString();
        date_posted = in.readString();
        user_name = in.readString();
        title = in.readString();
        description = in.readString();
        category = in.readString();
        available = in.readByte() != 0;
        payment = in.readDouble();
        key_id = in.readString();
    }

    public static final Creator<Post> CREATOR = new Creator<Post>() {
        @Override
        public Post createFromParcel(Parcel in) {
            return new Post(in);
        }

        @Override
        public Post[] newArray(int size) {
            return new Post[size];
        }
    };

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public void setDate_posted(String date_posted) {
        this.date_posted = date_posted;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public void setPayment(Double payment) {
        this.payment = payment;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getDate_posted() {
        return date_posted;
    }

    public String getUser_name() {
        return user_name;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public boolean isAvailable() {
        return available;
    }

    public Double getPayment() {
        return payment;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(user_id);
        dest.writeString(date_posted);
        dest.writeString(user_name);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(category);
        dest.writeByte((byte) (available ? 1 : 0));
        dest.writeDouble(payment);
        dest.writeString(key_id);
    }
}
