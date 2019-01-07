package msa.msaprojectfinal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MyPostDetailedActivity extends AppCompatActivity {
    Button btnDeletePost, btnMarkUnavailable;
    TextView datePosted, userName, description, payment, availability, title;
    private DatabaseReference mDatabase= FirebaseDatabase.getInstance().getReference().child("Posts");

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypostdetail);
        Bundle data = getIntent().getExtras();
        final Post currentPost = (Post) data.getParcelable("post");
        final User currentUser = (User) data.getParcelable("user");
        title = findViewById(R.id.textViewPostTitle);
        title.setText(currentPost.title);
        datePosted = findViewById(R.id.textViewDatePosted);
        datePosted.setText("Date posted: "+currentPost.date_posted);
        description = findViewById(R.id.textViewPostDescription);
        description.setText(currentPost.description);
        userName = findViewById(R.id.textViewPostedBy);
        userName.setText("Posted by: "+currentPost.user_name);
        payment = findViewById(R.id.textViewRewardPost);
        payment.setText("Payment offered: "+ currentPost.payment + " lei");
        availability = findViewById(R.id.textViewAvailability);
        if(currentPost.available){
            availability.setText("Post is Available");
        }else{
            availability.setText("Post is Unavailable");
        }

        btnDeletePost = (Button) findViewById(R.id.buttonDeletePost);

        btnDeletePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                Intent myIntent = new Intent(MyPostDetailedActivity.this, UserPostsActivity.class);
                myIntent.putExtra("user", currentUser);
                startActivity(myIntent);*/
                //delete this post from DB
                String post_key = currentPost.key_id;
                mDatabase.child(post_key).removeValue();
                //MyPostDetailedActivity.this.finish();
                Intent myIntent = new Intent(MyPostDetailedActivity.this, UserPostsActivity.class);
                myIntent.putExtra("user", currentUser);
                startActivity(myIntent);
            }
        });

    }

}
