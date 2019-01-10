package msa.msaprojectfinal;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MyPostDetailedFragment extends Fragment {
    Button btnDeletePost, btnMarkUnavailable;
    TextView datePosted, userName, description, payment, availability, title;
    Post currentPost;
    private DatabaseReference mDatabase= FirebaseDatabase.getInstance().getReference().child("Posts");
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (container != null) {
            container.removeAllViews();
        }
        View myFragmentView = inflater.inflate(R.layout.activity_mypostdetail, container, false);
        getActivity().setTitle("Post details");
        Bundle bundle = getArguments();
        currentPost = (Post) bundle.getParcelable("post");
        title = myFragmentView.findViewById(R.id.textViewPostTitle);
        title.setText(currentPost.title);
        datePosted = myFragmentView.findViewById(R.id.textViewDatePosted);
        datePosted.setText("Date posted: "+currentPost.date_posted);
        description = myFragmentView.findViewById(R.id.textViewPostDescription);
        description.setText(currentPost.description);
        userName = myFragmentView.findViewById(R.id.textViewPostedBy);
        userName.setText("Posted by: "+currentPost.user_name);
        payment = myFragmentView.findViewById(R.id.textViewRewardPost);
        payment.setText("Payment offered: "+ currentPost.payment + " lei");
        availability = myFragmentView.findViewById(R.id.textViewAvailability);
        if(currentPost.available){
            availability.setText("Post is Available");
        }else{
            availability.setText("Post is Unavailable");
        }

        btnDeletePost = (Button) myFragmentView.findViewById(R.id.buttonDeletePost);

        btnDeletePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //delete this post from DB
                String post_key = currentPost.key_id;
                mDatabase.child(post_key).removeValue();
                //MyPostDetailedActivity.this.finish();
                getFragmentManager().popBackStackImmediate();
            }
        });


        return myFragmentView;
    }
}
