package msa.msaprojectfinal;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AllPostsFragment extends Fragment {
    private DatabaseReference mDatabase= FirebaseDatabase.getInstance().getReference();
    ListView listview;
    ArrayList<Post> postsList;
    private PostAdapter mAdapter;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (container != null) {
            container.removeAllViews();
        }
        View myFragmentView = inflater.inflate(R.layout.activity_allposts, container, false);
        getActivity().setTitle("All posts");
        postsList = new ArrayList<Post>();
        mAdapter = new PostAdapter(this.getActivity(), postsList);
        listview = (ListView)  myFragmentView.findViewById(R.id.myalllistview);
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
                FragmentTransaction ft =  getActivity().getSupportFragmentManager().beginTransaction();
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                PostDetailedFragment fragment2 = new PostDetailedFragment();
                Bundle bundle = new Bundle();
                bundle.putParcelable("post", currentPost);
                fragment2.setArguments(bundle);
                ft.replace(R.id.fragment_container, fragment2);
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        return myFragmentView;
    }
}
