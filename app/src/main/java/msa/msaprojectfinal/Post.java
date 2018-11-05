package msa.msaprojectfinal;

import com.google.firebase.database.DatabaseReference;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Calendar;

public class Post {
    public String uid;
    public String date;
    public String author; //username
    public String title;
    public String description;
    public String category;
    DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm");

    public Post() {
        // Default constructor required for calls to DataSnapshot.getValue(Post.class)
    }

    public Post(String uid, String author, String title, String description, String category, String date) {
        this.uid = uid;
        this.date = date;
        this.author = author;
        this.title = title;
        this.description = description;
        this.category = category;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("uid", uid);
        result.put("author", author);
        result.put("title", title);
        result.put("body", description);
        result.put("date", df.format(Calendar.getInstance().getTime()));
        return result;
    }

}
