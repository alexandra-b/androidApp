package msa.msaprojectfinal;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AllPostsActivity extends AppCompatActivity {
    private DatabaseReference mDatabase= FirebaseDatabase.getInstance().getReference();
    ListView listview;
    ArrayList<Post> postsList = new ArrayList<Post>();
    private PostAdapter mAdapter;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allposts);
        mAdapter = new PostAdapter(this, postsList);
        listview = (ListView) findViewById(R.id.myalllistview);
        mDatabase.child("Posts").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Post currentPost = dataSnapshot.getValue(Post.class);
                currentPost.key_id = dataSnapshot.getKey();
                postsList.add(currentPost);
                 mAdapter.notifyDataSetChanged();
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

                Intent myIntent = new Intent(AllPostsActivity.this, PostDetailedActivity.class);
                myIntent.putExtra("post", currentPost);
                startActivity(myIntent);
            }
        });
    }
}
