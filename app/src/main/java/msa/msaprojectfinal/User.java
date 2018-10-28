package msa.msaprojectfinal;

import com.google.firebase.database.DatabaseReference;

public class User {
    public String username;
    public String email;
    public String firstName;
    public String lastName;
    public String userId;

    public User(){

    }

    public User(String userId, String username, String email, String firstName, String lastName){
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public User(String username, String email){
        this.username = username;
        this.email = email;
    }

}
