package msa.msaprojectfinal;
import android.content.Intent;
import android.os.UserHandle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UserActivity extends AppCompatActivity {
    Button btnLogOut, btnCreatePost;
    FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private DatabaseReference mDatabase= FirebaseDatabase.getInstance().getReference();
    TextView userNameTV;
    User currentUser;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        Intent intent = getIntent();
        final String userId = intent.getStringExtra("userId");
        final String email = intent.getStringExtra("emailID");
        userNameTV = findViewById(R.id.textViewUserName);
        mDatabase.child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot currDataSnapshot : dataSnapshot.getChildren()) {
                    if(currDataSnapshot.getValue(User.class).email.equals(email)){
                        currentUser = currDataSnapshot.getValue(User.class);
                        userNameTV.setText(currentUser.username);
                        System.out.println("USER NAME IS "+currentUser.username);
                        break;
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btnLogOut = (Button) findViewById(R.id.buttonLogOut);

        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent I = new Intent(UserActivity.this, LoginActivity.class);
                startActivity(I);
            }
        });

        btnCreatePost = (Button) findViewById(R.id.buttonCreatePost);

        btnCreatePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent I = new Intent(UserActivity.this, CreatePostActivity.class);
                startActivity(I);
            }
        });


    }

}