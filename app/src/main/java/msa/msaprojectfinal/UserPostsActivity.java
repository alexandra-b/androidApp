package msa.msaprojectfinal;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class UserPostsActivity extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    private DatabaseReference mDatabase= FirebaseDatabase.getInstance().getReference();
    ListView listview;
    ArrayList<String> posts = new ArrayList<String>();
    ArrayList<Post> postsList = new ArrayList<Post>();
    ArrayAdapter<String> itemsAdapter;
    private PostAdapter mAdapter;
    String userId;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userposts);
        mAdapter = new PostAdapter(this, postsList);
        Bundle data = getIntent().getExtras();
        final User currentUser = (User) data.getParcelable("user");
        listview = (ListView) findViewById(R.id.mylistview);
        userId = currentUser.userId;
        itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, posts);
        //listview.setAdapter(itemsAdapter);

        System.out.println("USER IS "+userId);
        mDatabase.child("Posts").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Post currentPost = dataSnapshot.getValue(Post.class);
                System.out.println("POST TITLE "+currentPost.title);
                if(currentPost.user_id.equals(userId)) {
                    String value = currentPost.description;
                    currentPost.key_id = dataSnapshot.getKey();
                    posts.add(value);
                    postsList.add(currentPost);
                   // itemsAdapter.notifyDataSetChanged();
                    mAdapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                Post currentPost = dataSnapshot.getValue(Post.class);
                postsList.remove(currentPost);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });
        listview.setAdapter(mAdapter);
        listview.setClickable(true);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Post currentPost = (Post)listview.getItemAtPosition(position);
                System.out.println("ITEM AT POSITION IS "+currentPost.key_id);
                //start new activity with post description
                Intent myIntent = new Intent(UserPostsActivity.this, MyPostDetailedActivity.class);
                myIntent.putExtra("post", currentPost);
                myIntent.putExtra("user", currentUser);
                startActivity(myIntent);
            }
        });
    }
}
