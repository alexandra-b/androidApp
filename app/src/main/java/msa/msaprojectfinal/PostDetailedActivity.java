package msa.msaprojectfinal;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PostDetailedActivity extends AppCompatActivity {
    Button interested;
    TextView datePosted, userName, description, payment, availability, title;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postdetail);
        Bundle data = getIntent().getExtras();
        final Post currentPost = (Post) data.getParcelable("post");
        title = findViewById(R.id.textViewTitlePost2);
        title.setText(currentPost.title);
        datePosted = findViewById(R.id.textViewDatePosted2);
        datePosted.setText("Date posted: "+currentPost.date_posted);
        description = findViewById(R.id.textViewDescription2);
        description.setText(currentPost.description);
        userName = findViewById(R.id.textViewPostedBy2);
        userName.setText("Posted by: "+currentPost.user_name);
        payment = findViewById(R.id.textViewPayment);
        payment.setText("Payment offered: "+ currentPost.payment + " lei");
        availability = findViewById(R.id.textViewAvailable);
        if(currentPost.available){
            availability.setText("Post is Available");
        }else{
            availability.setText("Post is Unavailable");
        }
    }

}
