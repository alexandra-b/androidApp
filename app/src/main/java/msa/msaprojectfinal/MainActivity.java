package msa.msaprojectfinal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.support.annotation.NonNull;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    public EditText emailId, passwd, first_name, last_name, user_name, city, confirmPasswd;
    Button btnSignUp;
    TextView signIn;
    FirebaseAuth firebaseAuth;
    private DatabaseReference mDatabase= FirebaseDatabase.getInstance().getReference();
    private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseAuth = FirebaseAuth.getInstance();
        emailId = findViewById(R.id.editTextEmail);
        first_name = findViewById(R.id.editTextFName);
        last_name = findViewById(R.id.editTextLName);
        user_name = findViewById(R.id.editTextUserName);
        city = findViewById(R.id.editTextCity);
        passwd = findViewById(R.id.editTextPassword);
        confirmPasswd = findViewById(R.id.editTextConfirmPassword);
        btnSignUp = findViewById(R.id.buttonSignUp);
        signIn = findViewById(R.id.textViewAlreadyAccount);
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Toast.makeText(MainActivity.this, "User logged in ", Toast.LENGTH_SHORT).show();
                   // Intent myIntent = new Intent(MainActivity.this, UserActivity.class);
                    Intent myIntent = new Intent(MainActivity.this, UserMenuActivity.class);
                    myIntent.putExtra("emailID", user.getEmail());
                    startActivity(myIntent);
                } else {
                    Toast.makeText(MainActivity.this, "Login to continue", Toast.LENGTH_SHORT).show();
                }
            }
        };
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String emailID = emailId.getText().toString();
                String password = passwd.getText().toString();
                String confPassword = confirmPasswd.getText().toString();
                String nameText = first_name.getText().toString();
                if (emailID.isEmpty()) {
                    emailId.setError("Must input email address!");
                    emailId.requestFocus();
                } else if (password.isEmpty()) {
                    passwd.setError("Input your password");
                    passwd.requestFocus();
                }else if (confPassword.isEmpty()) {
                    confirmPasswd.setError("Confirm your password");
                    confirmPasswd.requestFocus();
                } else if (nameText.isEmpty()) {
                    first_name.setError("Input your name");
                    first_name.requestFocus();
                } else
                    {
                    //System.out.println(passwd.equals(confPassword));
                    if (password.equals(confPassword)){
                        firebaseAuth.createUserWithEmailAndPassword(emailID, password).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (!task.isSuccessful()) {
                                    Toast.makeText(MainActivity.this.getApplicationContext(),
                                            "Sign Up unsuccessful: " + task.getException().getMessage(),
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    //updateDataBaseWithNewUser
                                    String userId = addNewUser(first_name.getText().toString(), last_name.getText().toString(),emailId.getText().toString(),user_name.getText().toString(), city.getText().toString());
                                    //Intent myIntent = new Intent(MainActivity.this, UserActivity.class);
                                    Intent myIntent = new Intent(MainActivity.this, UserMenuActivity.class);
                                    //myIntent.putExtra("userId", userId);
                                    myIntent.putExtra("emailID", emailID);
                                    startActivity(myIntent);
                                }
                            }
                        });
                    }else{
                        Toast.makeText(MainActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent I = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(I);
            }
        });
    }

    private String addNewUser(String first_name, String last_name, String email, String user_name, String city) {
        String key = mDatabase.push().getKey();
        User user = new User(key, user_name, first_name, last_name, email, city);
        mDatabase.child("Users").child(key).setValue(user);
        return key;
    }

}
