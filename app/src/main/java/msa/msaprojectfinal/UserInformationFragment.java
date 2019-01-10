package msa.msaprojectfinal;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserInformationFragment extends Fragment {
    TextView user_name, first_name, last_name, email_profile;
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    private User currentUser;
    private String userId;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (container != null) {
            container.removeAllViews();
        }
        View myFragmentView = inflater.inflate(R.layout.activity_userinformation, container, false);
        getActivity().setTitle("User profile");
        Bundle bundle = getArguments();
        userId = bundle.getString("userId");
        System.out.println("USER ID IS "+userId);
        user_name = myFragmentView.findViewById(R.id.textViewUserNameProfile);
        first_name = myFragmentView.findViewById(R.id.textViewFNameProfile);
        last_name = myFragmentView.findViewById(R.id.textViewLNameProfile);
        email_profile = myFragmentView.findViewById(R.id.textViewEmailProfile);
        //findInDbUser
        mDatabase.child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                System.out.println("Searching for user info");
                for (DataSnapshot currDataSnapshot : dataSnapshot.getChildren()) {
                    if(currDataSnapshot.getValue(User.class).userId.equals(userId)){
                        System.out.println("FOUND HIM");
                        currentUser = currDataSnapshot.getValue(User.class);
                        user_name.setText(currentUser.username);
                        first_name.setText("First name : " + currentUser.firstName);
                        last_name.setText("Last name : " + currentUser.lastName);
                        email_profile.setText("Email : "+currentUser.email);
                        break;
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return myFragmentView;
    }
}
