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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    public EditText emailId, passwd, name, confirmPasswd;
    Button btnSignUp;
    TextView signIn;
    FirebaseAuth firebaseAuth;
    private DatabaseReference mDatabase= FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseAuth = FirebaseAuth.getInstance();
        emailId = findViewById(R.id.editTextEmail);
        name = findViewById(R.id.editTextName);
        passwd = findViewById(R.id.editTextPassword);
        confirmPasswd = findViewById(R.id.editTextConfirmPassword);
        btnSignUp = findViewById(R.id.buttonSignUp);
        signIn = findViewById(R.id.textViewAlreadyAccount);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailID = emailId.getText().toString();
                String password = passwd.getText().toString();
                String confPassword = confirmPasswd.getText().toString();
                String nameText = name.getText().toString();
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
                    name.setError("Input your name");
                    name.requestFocus();
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
                                    addNewUser(name.getText().toString(), emailId.getText().toString());
                                    startActivity(new Intent(MainActivity.this, UserActivity.class));
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

    private void addNewUser(String name, String email) {
        User user = new User(name, email);
        mDatabase.child("Users").child(name).setValue(user);
    }

}
