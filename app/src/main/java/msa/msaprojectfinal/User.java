package msa.msaprojectfinal;

public class User {
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

}
