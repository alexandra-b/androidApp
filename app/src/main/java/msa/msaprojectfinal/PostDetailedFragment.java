package msa.msaprojectfinal;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PostDetailedFragment extends Fragment {
    Button interested;
    TextView datePosted, userName, description, payment, availability, title;
    String userID;
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (container != null) {
            container.removeAllViews();
        }
        View myFragmentView = inflater.inflate(R.layout.activity_postdetail, container, false);
        getActivity().setTitle("Post details");
        Bundle bundle = getArguments();
        final Post currentPost = (Post) bundle.getParcelable("post");
        title = myFragmentView.findViewById(R.id.textViewTitlePost2);
        title.setText(currentPost.title);
        datePosted = myFragmentView.findViewById(R.id.textViewDatePosted2);
        datePosted.setText("Date posted: "+currentPost.date_posted);
        description = myFragmentView.findViewById(R.id.textViewDescription2);
        description.setText(currentPost.description);
        userName = myFragmentView.findViewById(R.id.textViewPostedBy2);
        userName.setText("Posted by: "+currentPost.user_name);
        payment = myFragmentView.findViewById(R.id.textViewPayment);
        payment.setText("Payment offered: "+ currentPost.payment + " lei");
        availability = myFragmentView.findViewById(R.id.textViewAvailable);
        if(currentPost.available){
            availability.setText("Post is Available");
        }else{
            availability.setText("Post is Unavailable");
        }
        interested = myFragmentView.findViewById(R.id.buttonImInterested);
        interested.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //start new fragment with user info
                userID = currentPost.user_id;
                mDatabase.child("Users").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot currDataSnapshot : dataSnapshot.getChildren()) {
                            if(currDataSnapshot.getValue(User.class).userId.equals(userID)){
                                User currentUser = currDataSnapshot.getValue(User.class);
                                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                                ExtraInformationFragment fragment2 = new ExtraInformationFragment();
                                Bundle bundle = new Bundle();
                                bundle.putParcelable("user", currentUser);
                                fragment2.setArguments(bundle);
                                ft.replace(R.id.fragment_container, fragment2);
                                ft.addToBackStack(null);
                                ft.commit();
                                break;
                            }
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
        return myFragmentView;
    }
}
