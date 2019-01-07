package msa.msaprojectfinal;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {
    public String username;
    public String email;
    public String firstName;
    public String lastName;
    public String userId;
    public String city;

    public User(){

    }

    public User(String userId, String username, String firstName, String lastName, String email, String city){
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.city = city;
    }

    public User(String username, String email){
        this.username = username;
        this.email = email;
    }

    protected User(Parcel in) {
        username = in.readString();
        email = in.readString();
        firstName = in.readString();
        lastName = in.readString();
        userId = in.readString();
        city = in.readString();
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
        dest.writeString(username);
        dest.writeString(email);
        dest.writeString(firstName);
        dest.writeString(lastName);
        dest.writeString(userId);
        dest.writeString(city);
    }
}
