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

public class MainActivity extends AppCompatActivity {
    public EditText emailId, passwd, name, confirmPasswd;
    Button btnSignUp;
    TextView signIn;
    FirebaseAuth firebaseAuth;
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
                if (emailID.isEmpty()) {
                    emailId.setError("Must input email address!");
                    emailId.requestFocus();
                } else if (password.isEmpty()) {
                    passwd.setError("Input your password");
                    passwd.requestFocus();
                }else if (confPassword.isEmpty()){
                    confirmPasswd.setError("Confirm your password");
                    confirmPasswd.requestFocus();
                } else if (emailID.isEmpty() && password.isEmpty() && confPassword.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please complete empty fields!", Toast.LENGTH_SHORT).show();
                } else if (!(emailID.isEmpty() && password.isEmpty() && confPassword.isEmpty())) {
                    //System.out.println(passwd.equals(confPassword));
                    if (password.equals(confPassword)){
                        firebaseAuth.createUserWithEmailAndPassword(emailID, password).addOnCompleteListener(MainActivity.this, new OnCompleteListener() {
                            @Override
                            public void onComplete(@NonNull Task task) {

                                if (!task.isSuccessful()) {
                                    Toast.makeText(MainActivity.this.getApplicationContext(),
                                            "Sign Up unsuccessful: " + task.getException().getMessage(),
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    startActivity(new Intent(MainActivity.this, UserActivity.class));
                                }
                            }
                        });
                    }else{
                        Toast.makeText(MainActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
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

}
