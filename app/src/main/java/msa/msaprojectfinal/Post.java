package msa.msaprojectfinal;

import com.google.firebase.database.DatabaseReference;

import java.util.HashMap;
import java.util.Map;

public class Post {
    public String uid;
    public String date;
    public String author; //username
    public String title;
    public String body;
    public String category;

    public Post() {
        // Default constructor required for calls to DataSnapshot.getValue(Post.class)
    }

    public Post(String uid, String author, String title, String body, String category, String date) {
        this.uid = uid;
        this.date = date;
        this.author = author;
        this.title = title;
        this.body = body;
        this.category = category;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("uid", uid);
        result.put("author", author);
        result.put("title", title);
        result.put("body", body);
        return result;
    }

}
