package msa.msaprojectfinal;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    public EditText loginEmailId, logInpasswd;
    Button btnLogIn;
    TextView signup;
    FirebaseAuth firebaseAuth;
    private User currentUser;
    private String email;
    private FirebaseAuth.AuthStateListener authStateListener;
    private DatabaseReference mDatabase= FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        firebaseAuth = FirebaseAuth.getInstance();
        loginEmailId = findViewById(R.id.loginEmail);
        logInpasswd = findViewById(R.id.loginPassword);
        btnLogIn = findViewById(R.id.buttonLogIn);
        signup = findViewById(R.id.textViewSignUp);
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    System.out.println("------------------------------LOGGED IN -------------------------");
                    System.out.println(user.getEmail());
                    email = user.getEmail();
                    mDatabase.child("Users").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot currDataSnapshot : dataSnapshot.getChildren()) {
                                if(currDataSnapshot.getValue(User.class).email.equals(email)){
                                    currentUser = currDataSnapshot.getValue(User.class);
                                    //userNameTV.setText(currentUser.username);
                                    System.out.println("USER FROM DB IS "+currentUser.username);
                                    Intent myIntent = new Intent(LoginActivity.this, UserMenuActivity.class);
                                    myIntent.putExtra("user", currentUser);
                                    startActivity(myIntent);
                                    break;
                                }
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                    Toast.makeText(LoginActivity.this, "User logged in ", Toast.LENGTH_SHORT).show();
                    //Intent myIntent = new Intent(LoginActivity.this, UserActivity.class);
//                    Intent myIntent = new Intent(LoginActivity.this, UserMenuActivity.class);
//                    myIntent.putExtra("user", currentUser);
//                    startActivity(myIntent);
                } else {
                    Toast.makeText(LoginActivity.this, "Login to continue", Toast.LENGTH_SHORT).show();
                }
            }
        };

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent I = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(I);
            }
        });

        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userEmail = loginEmailId.getText().toString();
                String userPaswd = logInpasswd.getText().toString();
                if (userEmail.isEmpty()) {
                    loginEmailId.setError("Provide your Email first!");
                    loginEmailId.requestFocus();
                } else if (userPaswd.isEmpty()) {
                    logInpasswd.setError("Enter Password!");
                    logInpasswd.requestFocus();
                } else if (userEmail.isEmpty() && userPaswd.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Fields Empty!", Toast.LENGTH_SHORT).show();
                } else if (!(userEmail.isEmpty() && userPaswd.isEmpty())) {
                    firebaseAuth.signInWithEmailAndPassword(userEmail, userPaswd).addOnCompleteListener(LoginActivity.this, new OnCompleteListener() {
                        @Override
                        public void onComplete(@NonNull Task task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(LoginActivity.this, "Unsuccessful login!", Toast.LENGTH_SHORT).show();
                            } else {
//                                Intent myIntent = new Intent(LoginActivity.this, UserActivity.class);
//                                myIntent.putExtra("emailID", loginEmailId.getText().toString());
//                                startActivity(myIntent);
                                Intent myIntent = new Intent(LoginActivity.this, UserMenuActivity.class);
                                myIntent.putExtra("user", currentUser);
                                startActivity(myIntent);
                            }
                        }
                    });
                } else {
                    Toast.makeText(LoginActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(authStateListener);
    }
}
